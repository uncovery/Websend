package com.github.websend.server;

import com.github.websend.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

public class PacketParser {
    static boolean parseAuthenticationRequestPacket( DataInputStream in, DataOutputStream out ) throws IOException {
        String header = readString( in );
        if ( !header.equals( "websendmagic" ) ) {
            return false;
        }

        SecureRandom random = new SecureRandom();
        int seed = random.nextInt();
        out.writeInt( seed );
        String correctHash = Util.hash( seed + Main.getSettings().getPassword() );

        String authString = readString( in );
        boolean success = authString.equals( correctHash );
        out.writeInt( success ? 1 : 0 );
        return success;
    }

    public static void parseDoCommandAsPlayer( DataInputStream in, DataOutputStream out ) throws IOException, ExecutionException, InterruptedException {
        String command = readString( in );
        String playerStr = readString( in );

        Player player = Bukkit.getScheduler().callSyncMethod( Main.getInstance(), () -> Util.findPlayer( playerStr ) ).get();

        if ( player == null ) {
            Main.logDebugInfo( Level.WARNING, "Can't execute command (" + command + ") as player: Player cannot be found (" + playerStr + ")" );
            out.writeInt( 0 );
            out.flush();
            return;
        }

        boolean success;
        try {
            if ( Main.getSettings().areCommandExecutorsWrapped() ) {
                PluginCommand pluginCommand = Bukkit.getPluginCommand( command );
                if ( pluginCommand != null ) {
                    Plugin targetPlugin = pluginCommand.getPlugin();
                    success = Bukkit.getScheduler().callSyncMethod( Main.getInstance(), () -> Bukkit.dispatchCommand( new WebsendPlayerCommandSender( player, targetPlugin ), command ) ).get();
                } else {
                    Main.getMainLogger().log( Level.WARNING, "Cannot execute command '" + command + "': Command does not exist." );
                    success = false;
                }
            } else {
                success = Bukkit.getScheduler().callSyncMethod( Main.getInstance(), () -> Bukkit.dispatchCommand( player, command ) ).get();
            }
        } catch ( Exception ex ) {
            Main.logDebugInfo( Level.WARNING, "Websend caught an exception while running command '" + command + "'", ex );
            success = false;
        }

        if ( success ) {
            out.writeInt( 1 );
        } else {
            out.writeInt( 0 );
        }
        out.flush();
    }

    public static void parseDoCommandAsConsole( DataInputStream in, DataOutputStream out ) throws IOException {
        String command = readString( in );
        boolean success;
        try {
            //config check?
            if ( Main.getSettings().areCommandExecutorsWrapped() ) {
                PluginCommand pluginCommand = Bukkit.getPluginCommand( command );
                if ( pluginCommand != null ) {
                    Plugin targetPlugin = pluginCommand.getPlugin();
                    success = Bukkit.getScheduler().callSyncMethod( Main.getInstance(), () -> Bukkit.dispatchCommand(
                            new WebsendConsoleCommandSender(
                                    Bukkit.getConsoleSender(),
                                    targetPlugin ),
                            command ) ).get();
                } else {
                    Main.getMainLogger().log( Level.WARNING, "Cannot execute command '" + command + "': Command does not exist." );
                    success = false;
                }
            } else {
                success = Bukkit.getScheduler().callSyncMethod( Main.getInstance(), () -> Bukkit.dispatchCommand( Bukkit.getConsoleSender(), command ) ).get();
            }
        } catch ( Exception ex ) {
            Main.logDebugInfo( Level.WARNING, "Websend caught an exception while running command '" + command + "'", ex );
            success = false;
        }
        if ( success ) {
            out.writeInt( 1 );
        } else {
            out.writeInt( 0 );
        }
        out.flush();
    }

    public static void parseDoScript( DataInputStream in, DataOutputStream out ) throws IOException {
        String scriptName = readString( in );
        Main.getScriptManager().invokeScript( scriptName );
    }

    public static void parseWriteOutputToConsole( DataInputStream in, DataOutputStream out ) throws IOException {
        String message = readString( in );
        Main.getMainLogger().info( message );
    }

    public static void parseWriteOutputToPlayer( DataInputStream in, DataOutputStream out ) throws IOException, ExecutionException, InterruptedException {
        String message = readString( in );
        String playerStr = readString( in );
        Player player = Bukkit.getScheduler().callSyncMethod( Main.getInstance(), () -> Util.findPlayer( playerStr ) ).get();
        if ( player != null ) {
            out.writeInt( 1 );
            Bukkit.getScheduler().runTask( Main.getInstance(), () -> player.sendMessage( message ) );
        } else {
            out.writeInt( 0 );
        }
        out.flush();
    }

    public static void parseBroadcast( DataInputStream in, DataOutputStream out ) throws IOException {
        String message = readString( in );
        Bukkit.getScheduler().runTask( Main.getInstance(), () -> Bukkit.broadcastMessage( ChatColor.translateAlternateColorCodes( '&', message ) ) );
    }

    public static void parseStartPluginOutputRecording( DataInputStream in, DataOutputStream out ) throws IOException {
        String pluginName = readString( in );
        PluginOutputManager.startRecording( pluginName );
    }

    public static void parseEndPluginOutputRecording( DataInputStream in, DataOutputStream out ) throws IOException {
        String pluginName = readString( in );
        ArrayList<String> output = PluginOutputManager.stopRecording( pluginName );
        out.writeInt( output.size() );
        for ( String cur : output ) {
            writeString( out, cur );
        }
        out.flush();
    }

    private static String readString( DataInputStream in ) throws IOException {
        int stringSize = in.readInt();
        StringBuilder buffer = new StringBuilder();
        for ( int i = 0; i < stringSize; i++ ) {
            buffer.append( in.readChar() );
        }

        return buffer.toString();
    }

    private static void writeString( DataOutputStream out, String string ) throws IOException {
        out.writeInt( string.length() );
        out.writeChars( string );
    }
}
