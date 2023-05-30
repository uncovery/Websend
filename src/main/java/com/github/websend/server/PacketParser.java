package com.github.websend.server;

import com.github.websend.Main;
import com.github.websend.PluginOutputManager;
import com.github.websend.Util;
import com.github.websend.WebsendConsoleCommandSender;
import com.github.websend.WebsendPlayerCommandSender;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.WorldBorder;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pose;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.profile.PlayerProfile;

public class PacketParser {
    static boolean parseAuthenticationRequestPacket(DataInputStream in, DataOutputStream out) throws IOException {
        String header = readString(in);
        if(!header.equals("websendmagic")){
            return false;
        }

        SecureRandom random = new SecureRandom();
        int seed = random.nextInt();
        out.writeInt(seed);
        String correctHash = Util.hash(seed+Main.getSettings().getPassword());

        String authString = readString(in);
        boolean success = authString.equals(correctHash);
        out.writeInt(success?1:0);
        return success;
    }

    public static void parseDoCommandAsPlayer(DataInputStream in, DataOutputStream out) throws IOException {
        final String command = readString(in);
        String playerStr = readString(in);

        final Player player = Util.findPlayer(playerStr);

        if (player == null) {
            Main.logDebugInfo(Level.WARNING, "Can't execute command (" + command + ") as player: Player cannot be found (" + playerStr + ")");
            out.writeInt(0);
            out.flush();
            return;
        }

        boolean success;
        try {
            if (Main.getSettings().areCommandExecutorsWrapped()) {
                PluginCommand pluginCommand = Main.getBukkitServer().getPluginCommand(command);
                if (pluginCommand != null){
                    final Plugin targetPlugin = pluginCommand.getPlugin();

                    success = Main.getBukkitServer().getScheduler().callSyncMethod(Main.getInstance(), new CallableImpl(player, targetPlugin, command)).get();
                } else {
                    Main.getMainLogger().log(Level.WARNING, "Cannot execute command '" + command + "': Command does not exist.");
                    success = false;
                }
            } else {
                success = Main.getBukkitServer().dispatchCommand(player, command);
            }
        } catch (Exception ex) {
            Main.logDebugInfo(Level.WARNING, "Websend caught an exception while running command '" + command + "'", ex);
            success = false;
        }

        if (success) {
            out.writeInt(1);
        } else {
            out.writeInt(0);
        }
        out.flush();
    }

    public static void parseDoCommandAsConsole(DataInputStream in, DataOutputStream out) throws IOException {
        final String command = readString(in);
        boolean success;
        try {
            //config check?
            if (Main.getSettings().areCommandExecutorsWrapped()) {
                PluginCommand pluginCommand = Main.getBukkitServer().getPluginCommand(command);
                if(pluginCommand != null){
                    final Plugin targetPlugin = pluginCommand.getPlugin();
                    success = Main.getBukkitServer().getScheduler().callSyncMethod(Main.getInstance(), new Callable<Boolean>() {
                        @Override
                        public Boolean call() {
                            return Bukkit.dispatchCommand(
                                new WebsendConsoleCommandSender(
                                    Bukkit.getConsoleSender(),
                                    targetPlugin
                                ),
                                command
                            );
                        }
                    }).get();
                }else{
                    Main.getMainLogger().log(Level.WARNING, "Cannot execute command '"+command+"': Command does not exist.");
                    success = false;
                }
            } else {
                success = Main.getBukkitServer().getScheduler().callSyncMethod(Main.getInstance(), new Callable<Boolean>() {
                    @Override
                    public Boolean call() {
                        return Bukkit.dispatchCommand(Main.getBukkitServer().getConsoleSender(), command);
                    }
                }).get();
            }
        } catch (Exception ex) {
            Main.logDebugInfo(Level.WARNING, "Websend caught an exception while running command '" + command + "'", ex);
            success = false;
        }
        if (success) {
            out.writeInt(1);
        } else {
            out.writeInt(0);
        }
        out.flush();
    }

    public static void parseDoScript(DataInputStream in, DataOutputStream out) throws IOException {
        String scriptName = readString(in);
        Main.getScriptManager().invokeScript(scriptName);
    }

    public static void parseWriteOutputToConsole(DataInputStream in, DataOutputStream out) throws IOException {
        String message = readString(in);
        Main.getMainLogger().info(message);
    }

    public static void parseWriteOutputToPlayer(DataInputStream in, DataOutputStream out) throws IOException, ExecutionException, InterruptedException {
        final String message = readString(in);
        final String playerStr = readString(in);
        final Player player = Main.getBukkitServer().getScheduler().callSyncMethod(Main.getInstance(), new Callable<Player>() {
            @Override
            public Player call() {
                return Util.findPlayer(playerStr);
            }
        }).get();

        if (player != null) {
            out.writeInt(1);
            Main.getBukkitServer().getScheduler().callSyncMethod(Main.getInstance(), new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    player.sendMessage(message);
                    return true;
                }
            }).get();
        } else {
            out.writeInt(0);
        }
        out.flush();
    }

    public static void parseBroadcast(DataInputStream in, DataOutputStream out) throws IOException, InterruptedException, ExecutionException {
        final String message = readString(in);
        Main.getBukkitServer().getScheduler().callSyncMethod(Main.getInstance(), new Callable<Boolean>() {
            @Override
            public Boolean call() {
                Main.getBukkitServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
                return true;
            }
        }).get();
}

    public static void parseStartPluginOutputRecording(DataInputStream in, DataOutputStream out) throws IOException {
        String pluginName = readString(in);
        PluginOutputManager.startRecording(pluginName);
    }

    public static void parseEndPluginOutputRecording(DataInputStream in, DataOutputStream out) throws IOException {
        String pluginName = readString(in);
        ArrayList<String> output = PluginOutputManager.stopRecording(pluginName);
        out.writeInt(output.size());
        for (String cur : output) {
            writeString(out, cur);
        }
        out.flush();
    }

    private static String readString(DataInputStream in) throws IOException {
        int stringSize = in.readInt();
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < stringSize; i++) {
            buffer.append(in.readChar());
        }

        return buffer.toString();
    }

    private static void writeString(DataOutputStream out, String string) throws IOException {
        out.writeInt(string.length());
        out.writeChars(string);
    }

    private static class CallableImpl implements Callable<Boolean> {

        private final Player player;
        private final Plugin targetPlugin;
        private final String command;

        public CallableImpl(Player player, Plugin targetPlugin, String command) {
            this.player = player;
            this.targetPlugin = targetPlugin;
            this.command = command;
        }

        @Override
        public Boolean call() {
            return Bukkit.dispatchCommand(new WebsendPlayerCommandSenderImpl(player, targetPlugin), command);
        }

        private static class WebsendPlayerCommandSenderImpl extends WebsendPlayerCommandSender {

            public WebsendPlayerCommandSenderImpl(Player player, Plugin plugin) {
                super(player, plugin);
            }

            @Override
            public void sendSignChange(Location lctn, String[] strings, DyeColor dc) throws IllegalArgumentException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void openBook(ItemStack is) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public <T> T getMemory(MemoryKey<T> mk) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public <T> void setMemory(MemoryKey<T> mk, T t) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setRotation(float f, float f1) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Pose getPose() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public PersistentDataContainer getPersistentDataContainer() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public double getAbsorptionAmount() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setAbsorptionAmount(double d) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void sendExperienceChange(float f) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void sendExperienceChange(float f, int i) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Player.Spigot spigot() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public float getAttackCooldown() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void attack(Entity entity) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void swingMainHand() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void swingOffHand() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean hasDiscoveredRecipe(NamespacedKey nk) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Set<NamespacedKey> getDiscoveredRecipes() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean dropItem(boolean bln) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Set<UUID> getCollidableExemptions() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int getArrowCooldown() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setArrowCooldown(int i) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int getArrowsInBody() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setArrowsInBody(int i) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public EntityCategory getCategory() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setInvisible(boolean bln) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isInvisible() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void sendMessage(UUID uuid, String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void sendMessage(UUID uuid, String[] strings) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void sendRawMessage(UUID uuid, String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isInWater() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void sendBlockDamage(Location arg0, float arg1) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int getPing() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int getSaturatedRegenRate() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setSaturatedRegenRate(int arg0) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int getUnsaturatedRegenRate() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setUnsaturatedRegenRate(int arg0) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int getStarvationRate() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setStarvationRate(int arg0) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean breakBlock(Block block) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public ItemStack getItemInUse() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isClimbing() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setVisualFire(boolean bln) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isVisualFire() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int getFreezeTicks() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int getMaxFreezeTicks() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setFreezeTicks(int i) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isFrozen() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void playSound(Entity entity, Sound sound, float f, float f1) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void playSound(Entity entity, String string, float f, float f1) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void playSound(Entity entity, Sound sound, SoundCategory sc, float f, float f1) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void playSound(Entity entity, String string, SoundCategory sc, float f, float f1) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void stopSound(SoundCategory sc) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void stopAllSounds() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void sendBlockChanges(Collection<BlockState> clctn, boolean bln) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void sendBlockDamage(Location lctn, float f, Entity entity) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void sendBlockDamage(Location lctn, float f, int i) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void sendEquipmentChange(LivingEntity le, EquipmentSlot es, ItemStack is) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void sendEquipmentChange(LivingEntity le, Map<EquipmentSlot, ItemStack> map) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void sendSignChange(Location lctn, String[] strings, DyeColor dc, boolean bln) throws IllegalArgumentException {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void sendHurtAnimation(float f) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void addCustomChatCompletions(Collection<String> clctn) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void removeCustomChatCompletions(Collection<String> clctn) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void setCustomChatCompletions(Collection<String> clctn) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public GameMode getPreviousGameMode() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public int getExpCooldown() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void setExpCooldown(int i) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void hideEntity(Plugin plugin, Entity entity) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void showEntity(Plugin plugin, Entity entity) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public boolean canSee(Entity entity) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void setResourcePack(String string, byte[] bytes, String string1) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void setResourcePack(String string, byte[] bytes, boolean bln) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void setResourcePack(String string, byte[] bytes, String string1, boolean bln) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public WorldBorder getWorldBorder() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void setWorldBorder(WorldBorder wb) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void openSign(Sign sign) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void openSign(Sign sign, Side side) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void showDemoScreen() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public boolean isAllowingServerListings() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public int getEnchantmentSeed() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void setEnchantmentSeed(int i) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public Location getLastDeathLocation() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void setLastDeathLocation(Location lctn) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public Firework fireworkBoost(ItemStack is) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public Sound getHurtSound() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public Sound getDeathSound() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public Sound getFallDamageSound(int i) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public Sound getFallDamageSoundSmall() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public Sound getFallDamageSoundBig() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public Sound getDrinkingSound(ItemStack is) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public Sound getEatingSound(ItemStack is) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public boolean canBreatheUnderwater() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public Sound getSwimSound() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public Sound getSwimSplashSound() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public Sound getSwimHighSpeedSplashSound() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void setVisibleByDefault(boolean bln) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public boolean isVisibleByDefault() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public SpawnCategory getSpawnCategory() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public PlayerProfile getPlayerProfile() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        }
    }
}
