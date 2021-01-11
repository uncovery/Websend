package com.github.websend;

import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.json.*;

public class CommandParser {

    Settings settings;
    Server server;

    public CommandParser() {
        settings = Main.getSettings();
        server = Main.getBukkitServer();
    }

    /**
     * @param line
     * @param player
     */
    public void parse(String line, Player player) {
        // new version, do we have JSON?
        if (isJSONValid(line)) {
            JSONObject JSONobj = new JSONObject(line);

            String targetPlayer;
            if (!JSONobj.isNull("targetPlayer")) {
                targetPlayer = JSONobj.getString("targetPlayer").trim();
            } else {
                targetPlayer = null;
            }

            if (JSONobj.isNull("command")) {
                 Main.getMainLogger().info("Websend ERROR: The 'command' component of the JSON is missing: " + line);
                 return;
            }

            if (JSONobj.isNull("action")) {
                 Main.getMainLogger().info("Websend ERROR: The 'action' component of the JSON is missing!" + line);
                 return;
            }

            Main.logDebugInfo("Command parsing: Execute JSON commnad " + line);

            String action = JSONobj.getString("action").trim().toLowerCase();
            String command = JSONobj.getString("command").trim();

            switch (action) {
                case "executeplayercommand":
                    onExecutePlayerCommand(player, command, targetPlayer);
                    break;
                case "executeconsolecommand":
                    onExecuteConsoleCommand(player, command);
                    break;
                case "executescript":
                    onExecuteScript(command);
                    break;
                case "setresponseurl":
                    onSetResponseURL(command);
                    break;
                case "printtoconsole":
                    onPrintToConsole(command);
                    break;
                case "printtoplayer":
                    onPrintToPlayer(command, player, targetPlayer);
                    break;
                case "broadcast":
                    onBroadcast(command);
                    break;
                case "toggledebug":
                    onToggleDebug(command);
                    break;
                default:
                    Main.getMainLogger().info("Websend ERROR: invalid action '" + action + "'. Valid Actions are (case-insensitive):");
                    Main.getMainLogger().info("'ExecutePlayerCommand','ExecuteConsoleCommand','ExecuteScript','SetResponseURL','PrintToConsole','PrintToPlayer','Broadcast','toggleDebug'");
                    break;
            }
        } else {
            Main.getMainLogger().info("Websend ERROR: The input format from PHP is not valid JSON: " + line);
        }
    }

    private void onSetResponseURL(String newURL) {
        Main.logDebugInfo("Command parsing: Changed ResponseURL to " + newURL);
        settings.setResponseURL(newURL);
    }

    private void onToggleDebug(String arg) {
        if (arg == null) {
            if (settings.isDebugMode()) {
                settings.setDebugMode(false);
                Main.getMainLogger().info("Debug mode is now OFF");
            } else {
                settings.setDebugMode(true);
                Main.logDebugInfo("Debug mode is now ON");
            }
        } else {
            if ("on".equals(arg)) {
                settings.setDebugMode(true);
                Main.logDebugInfo("Debug mode is now ON");
            } else if ("off".equals(arg)) {
                settings.setDebugMode(false);
                Main.getMainLogger().info("Debug mode is now OFF");
            }
        }
    }

    private void onExecutePlayerCommand(Player player, String command, String targetPlayer) {
        if (player == null) {
            Main.getMainLogger().info("Websend: ExecutePlayerCommand is used in a wrong context.");
        }
        Main.logDebugInfo("Command parsing: An ExecutePlayerCommand was found: '" + command + "'");
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Task(command, player, targetPlayer) {

            @Override
            public void run() {
                String command = (String) this.getArgs().get(0);
                Player player = (Player) this.getArgs().get(1);
                String targetPlayer = (String) this.getArgs().get(2);

                if (targetPlayer != null) {
                    Player fakePlayer = Util.findPlayer(targetPlayer);
                    if (!server.dispatchCommand(fakePlayer, command)) { // execute command and check for succes.
                        Main.getMainLogger().info("Error executing onExecutePlayerCommand: Command dispatching failed: '" + command + "'"); // error
                    }
                } else {
                    try {
                        if (player == null) {
                            Main.getMainLogger().info("Error executing onExecutePlayerCommand: Command dispatching from terminal is not allowed. Try again in-game or set a targetplayer.");
                        } else if (!player.getServer().dispatchCommand(player, command)) { // execute command and check for succes.
                            player.sendMessage("Error executing onExecutePlayerCommand: Command dispatching failed: '" + command + "'"); // error
                        }
                    } catch (Exception ex) {
                        Main.getMainLogger().info("Error executing onExecutePlayerCommand: Are you trying to execute a player command from console?");
                    }
                }
            }
        });
    }

    private void onExecuteScript(String command) {
        Main.logDebugInfo("Command parsing: executing script: " + command);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Task(command) {
            @Override
            public void run() {
                Main.getScriptManager().invokeScript((String) this.getArgs().get(0));
            }
        });
    }

    private void onExecuteConsoleCommand(Player player, String command) {
        // split line into command and variables
        Main.logDebugInfo("Command parsing: An ExecuteConsoleCommand was found: '" + command + "'");

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Task(command, player) {
            @Override
            public void run() {
                String command = (String) this.getArgs().get(0);
                Player player = (Player) this.getArgs().get(1);
                ConsoleCommandSender ccs = server.getConsoleSender();
                if (!server.dispatchCommand(ccs, command)) { // execute command and check for succes.
                    if (player != null) {
                        player.sendMessage("Command dispatching failed: '" + command + "'"); // error
                    } else {
                        Main.getMainLogger().info("Command dispatching failed: '" + command + "'"); // error
                    }
                }
            }
        });
    }

    private void onPrintToConsole(String line) {
        Main.logDebugInfo("Command parsing: printing text to console " + line);
        String text = line.replaceFirst("PrintToConsole:", "");
        Main.getMainLogger().info(text);
    }

    private void onPrintToPlayer(String line, Player player, String targetPlayer) {
        Main.logDebugInfo("Command parsing: printing text to player  " + line);
        if (player == null && targetPlayer == null) {
            Main.getMainLogger().log(Level.WARNING, "Websend: No player to print text to. Use the targetPlayer argument in JSON to sent text in this context.");
            Main.getMainLogger().log(Level.WARNING, line.replaceFirst("PrintToConsole:", ""));
        } else if (player != null) {
            String playerName = targetPlayer;
            String message = line;

            if ("console".equals(playerName)) {
                Main.logDebugInfo("Websend: Player 'console'? Using PrintToConsole instead.");
            } else if (targetPlayer == null) {
                Main.getMainLogger().log(Level.WARNING, "Websend: No player '" + playerName + "' found on PrintToPlayer.");
            } else if (!player.isOnline()) {
                Main.logDebugInfo("Websend: Player '" + playerName + "' is offline. Ignoring PrintToPlayer");
            } else {
                player.sendMessage(parseColor(message));
            }
            String text = line.replaceFirst("PrintToPlayer:", "");
            player.sendMessage(parseColor(text));
        } else if (targetPlayer != null) {


        }
    }

    private void onBroadcast(String line) {
        Main.logDebugInfo("Command parsing: Broadcasting text " + line);
        String text = line.replaceFirst("Broadcast:", "");
        server.broadcastMessage(parseColor(text));
    }

    public String parseColor(String line) {
        return ChatColor.translateAlternateColorCodes('&', line);
    }

    // check if JSon is valid
    // from https://stackoverflow.com/questions/10174898/how-to-check-whether-a-given-string-is-valid-json-in-java
    public boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            return false;
        }
        return true;
    }


    @SuppressWarnings("unused")
    private void onExecuteConsoleCommandAndReturn(String line) {
        // split line into command and variables
        String[] commandArray = line.split("ExecuteConsoleCommandAndReturn-");
        Main.logDebugInfo("Command parsing: An ExecuteConsoleCommandAndReturn was found: '" + Util.stringArrayToString(commandArray) + "'");
        Plugin plugin = null;
        if (commandArray[1].split(":")[0].toLowerCase().startsWith("bukkit")) {
            // TODO: implement bukkit listening.
        } else {
            plugin = server.getPluginManager().getPlugin(commandArray[1].split(":")[0]);
            if (plugin == null) {
                Main.getMainLogger().info("ERROR: An invalid plugin name was provided.");
                return;
            }
            // TODO: Implement or remove.
        }
    }

    @SuppressWarnings("unused")
    private void onExecutePlayerCommandAndReturn(String line) {
        String commandArray[];
        // split line into command and variables
        commandArray = line.split("ExecutePlayerCommandAndReturn-");
        Main.logDebugInfo("Command parsing: An ExecutePlayerCommandAndReturn was found: '" + Util.stringArrayToString(commandArray) + "'");
        String argArray[] = commandArray[1].split("-");
        Player fakePlayer = Util.findPlayer(argArray[0].trim());
        String command = argArray[1].split(":")[1];
        Plugin plugin = null;
        if (commandArray[1].split(":")[0].toLowerCase().startsWith("bukkit")) {
            // TODO: implement bukkit listening.
        } else {
            plugin = server.getPluginManager().getPlugin(commandArray[1].split(":")[0]);
            if (plugin == null) {
                Main.getMainLogger().info("ERROR: An invalid plugin name was provided.");
                return;
            }
            // TODO: Implement or remove.
        }
    }
}
