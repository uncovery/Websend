package com.github.websend;

import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.*;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.MainHand;
import org.bukkit.inventory.Merchant;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class WebsendPlayerCommandSender implements Player {
    /* This class allows tapping into command output from plugins
     * if the output is sent through the commandsender.
     * Note to anyone having compilation problems: Compile against Bukkit, not CraftBukkit.
     *
     * Tap this method(1.6.4): sendRawMessage, sendMessage(String), sendMessage(String[])
     */

    private final Player baseObject;
    private final Plugin commandTargetPlugin;

    public WebsendPlayerCommandSender( Player baseObject, Plugin commandTargetPlugin ) {
        this.baseObject = baseObject;
        this.commandTargetPlugin = commandTargetPlugin;
    }

    @Override
    public void sendMessage( String param0 ) {
        PluginOutputManager.handleLogRecord( commandTargetPlugin, new LogRecord( Level.INFO, param0 ) );
        baseObject.sendMessage( param0 );
    }

    @Override
    public void sendMessage( String[] param0 ) {
        for ( String str : param0 ) {
            PluginOutputManager.handleLogRecord( commandTargetPlugin, new LogRecord( Level.INFO, str ) );
        }
        baseObject.sendMessage( param0 );
    }

    @Override
    public void sendRawMessage( String param0 ) {
        PluginOutputManager.handleLogRecord( commandTargetPlugin, new LogRecord( Level.INFO, param0 ) );
        baseObject.sendRawMessage( param0 );
    }

    @Override
    public String getDisplayName() {
        return baseObject.getDisplayName();
    }

    @Override
    public void setDisplayName( String param0 ) {
        baseObject.setDisplayName( param0 );
    }

    @Override
    public String getPlayerListName() {
        return baseObject.getPlayerListName();
    }

    @Override
    public void setPlayerListName( String param0 ) {
        baseObject.setPlayerListName( param0 );
    }

    @Override
    public String getPlayerListHeader() {
        return null;
    }

    @Override
    public void setPlayerListHeader( String s ) {

    }

    @Override
    public String getPlayerListFooter() {
        return null;
    }

    @Override
    public void setPlayerListFooter( String s ) {

    }

    @Override
    public void setPlayerListHeaderFooter( String s, String s1 ) {

    }

    @Override
    public org.bukkit.Location getCompassTarget() {
        return baseObject.getCompassTarget();
    }

    @Override
    public void setCompassTarget( org.bukkit.Location param0 ) {
        baseObject.setCompassTarget( param0 );
    }

    @Override
    public java.net.InetSocketAddress getAddress() {
        return baseObject.getAddress();
    }

    @Override
    public void kickPlayer( String param0 ) {
        baseObject.kickPlayer( param0 );
    }

    @Override
    public void chat( String param0 ) {
        baseObject.chat( param0 );
    }

    @Override
    public boolean performCommand( String param0 ) {
        return baseObject.performCommand( param0 );
    }

    @Override
    public boolean isSneaking() {
        return baseObject.isSneaking();
    }

    @Override
    public void setSneaking( boolean param0 ) {
        baseObject.setSneaking( param0 );
    }

    @Override
    public boolean isSprinting() {
        return baseObject.isSprinting();
    }

    @Override
    public void setSprinting( boolean param0 ) {
        baseObject.setSprinting( param0 );
    }

    @Override
    public void saveData() {
        baseObject.saveData();
    }

    @Override
    public void loadData() {
        baseObject.loadData();
    }

    @Override
    public boolean isSleepingIgnored() {
        return baseObject.isSleepingIgnored();
    }

    @Override
    public void setSleepingIgnored( boolean param0 ) {
        baseObject.setSleepingIgnored( param0 );
    }

    @Override
    public void playNote( org.bukkit.Location param0, byte param1, byte param2 ) {
        baseObject.playNote( param0, param1, param2 );
    }

    @Override
    public void playNote( org.bukkit.Location param0, org.bukkit.Instrument param1, org.bukkit.Note param2 ) {
        baseObject.playNote( param0, param1, param2 );
    }

    @Override
    public void playSound( org.bukkit.Location param0, org.bukkit.Sound param1, float param2, float param3 ) {
        baseObject.playSound( param0, param1, param2, param3 );
    }

    @Override
    public void playSound( org.bukkit.Location param0, String param1, float param2, float param3 ) {
        baseObject.playSound( param0, param1, param2, param3 );
    }

    @Override
    public void playSound( Location location, Sound sound, SoundCategory soundCategory, float v, float v1 ) {

    }

    @Override
    public void playSound( Location location, String s, SoundCategory soundCategory, float v, float v1 ) {

    }

    @Override
    public void playEffect( org.bukkit.Location param0, org.bukkit.Effect param1, int param2 ) {
        baseObject.playEffect( param0, param1, param2 );
    }

    @Override
    public <T> void playEffect( org.bukkit.Location param0, org.bukkit.Effect param1, T param2 ) {
        baseObject.playEffect( param0, param1, param2 );
    }

    @Override
    public void sendBlockChange( org.bukkit.Location param0, org.bukkit.Material param1, byte param2 ) {
        baseObject.sendBlockChange( param0, param1, param2 );
    }

    @Override
    public void sendBlockChange( Location location, BlockData blockData ) {

    }

    @Override
    public boolean sendChunkChange( org.bukkit.Location param0, int param1, int param2, int param3, byte[] param4 ) {
        return baseObject.sendChunkChange( param0, param1, param2, param3, param4 );
    }

    @Override
    public void sendMap( org.bukkit.map.MapView param0 ) {
        baseObject.sendMap( param0 );
    }

    @Override
    public void updateInventory() {
        baseObject.updateInventory();
    }

    @Override
    public void awardAchievement( org.bukkit.Achievement param0 ) {
        baseObject.awardAchievement( param0 );
    }

    @Override
    public void incrementStatistic( org.bukkit.Statistic param0 ) {
        baseObject.incrementStatistic( param0 );
    }

    @Override
    public void incrementStatistic( org.bukkit.Statistic param0, int param1 ) {
        baseObject.incrementStatistic( param0, param1 );
    }

    @Override
    public void incrementStatistic( org.bukkit.Statistic param0, org.bukkit.Material param1 ) {
        baseObject.incrementStatistic( param0, param1 );
    }

    @Override
    public void incrementStatistic( org.bukkit.Statistic param0, org.bukkit.Material param1, int param2 ) {
        baseObject.incrementStatistic( param0, param1, param2 );
    }

    @Override
    public void setPlayerTime( long param0, boolean param1 ) {
        baseObject.setPlayerTime( param0, param1 );
    }

    @Override
    public long getPlayerTime() {
        return baseObject.getPlayerTime();
    }

    @Override
    public long getPlayerTimeOffset() {
        return baseObject.getPlayerTimeOffset();
    }

    @Override
    public boolean isPlayerTimeRelative() {
        return baseObject.isPlayerTimeRelative();
    }

    @Override
    public void resetPlayerTime() {
        baseObject.resetPlayerTime();
    }

    @Override
    public org.bukkit.WeatherType getPlayerWeather() {
        return baseObject.getPlayerWeather();
    }

    @Override
    public void setPlayerWeather( org.bukkit.WeatherType param0 ) {
        baseObject.setPlayerWeather( param0 );
    }

    @Override
    public void resetPlayerWeather() {
        baseObject.resetPlayerWeather();
    }

    @Override
    public void giveExp( int param0 ) {
        baseObject.giveExp( param0 );
    }

    @Override
    public void giveExpLevels( int param0 ) {
        baseObject.giveExpLevels( param0 );
    }

    @Override
    public float getExp() {
        return baseObject.getExp();
    }

    @Override
    public void setExp( float param0 ) {
        baseObject.setExp( param0 );
    }

    @Override
    public int getLevel() {
        return baseObject.getLevel();
    }

    @Override
    public void setLevel( int param0 ) {
        baseObject.setLevel( param0 );
    }

    @Override
    public int getTotalExperience() {
        return baseObject.getTotalExperience();
    }

    @Override
    public void setTotalExperience( int param0 ) {
        baseObject.setTotalExperience( param0 );
    }

    @Override
    public float getExhaustion() {
        return baseObject.getExhaustion();
    }

    @Override
    public void setExhaustion( float param0 ) {
        baseObject.setExhaustion( param0 );
    }

    @Override
    public float getSaturation() {
        return baseObject.getSaturation();
    }

    @Override
    public void setSaturation( float param0 ) {
        baseObject.setSaturation( param0 );
    }

    @Override
    public int getFoodLevel() {
        return baseObject.getFoodLevel();
    }

    @Override
    public void setFoodLevel( int param0 ) {
        baseObject.setFoodLevel( param0 );
    }

    @Override
    public org.bukkit.Location getBedSpawnLocation() {
        return baseObject.getBedSpawnLocation();
    }

    @Override
    public void setBedSpawnLocation( org.bukkit.Location param0 ) {
        baseObject.setBedSpawnLocation( param0 );
    }

    @Override
    public void setBedSpawnLocation( org.bukkit.Location param0, boolean param1 ) {
        baseObject.setBedSpawnLocation( param0, param1 );
    }

    @Override
    public boolean getAllowFlight() {
        return baseObject.getAllowFlight();
    }

    @Override
    public void setAllowFlight( boolean param0 ) {
        baseObject.setAllowFlight( param0 );
    }

    @Override
    public void hidePlayer( org.bukkit.entity.Player param0 ) {
        baseObject.hidePlayer( param0 );
    }

    @Override
    public void hidePlayer( Plugin plugin, Player player ) {

    }

    @Override
    public void showPlayer( org.bukkit.entity.Player param0 ) {
        baseObject.showPlayer( param0 );
    }

    @Override
    public void showPlayer( Plugin plugin, Player player ) {

    }

    @Override
    public boolean canSee( org.bukkit.entity.Player param0 ) {
        return baseObject.canSee( param0 );
    }

    @Override
    public boolean isOnGround() {
        return baseObject.isOnGround();
    }

    @Override
    public boolean isFlying() {
        return baseObject.isFlying();
    }

    @Override
    public void setFlying( boolean param0 ) {
        baseObject.setFlying( param0 );
    }

    @Override
    public float getFlySpeed() {
        return baseObject.getFlySpeed();
    }

    @Override
    public void setFlySpeed( float param0 ) {
        baseObject.setFlySpeed( param0 );
    }

    @Override
    public float getWalkSpeed() {
        return baseObject.getWalkSpeed();
    }

    @Override
    public void setWalkSpeed( float param0 ) {
        baseObject.setWalkSpeed( param0 );
    }

    @Override
    public void setTexturePack( String param0 ) {
        baseObject.setTexturePack( param0 );
    }

    @Override
    public org.bukkit.scoreboard.Scoreboard getScoreboard() {
        return baseObject.getScoreboard();
    }

    @Override
    public void setScoreboard( org.bukkit.scoreboard.Scoreboard param0 ) {
        baseObject.setScoreboard( param0 );
    }

    @Override
    public boolean isHealthScaled() {
        return baseObject.isHealthScaled();
    }

    @Override
    public void setHealthScaled( boolean param0 ) {
        baseObject.setHealthScaled( param0 );
    }

    @Override
    public double getHealthScale() {
        return baseObject.getHealthScale();
    }

    @Override
    public void setHealthScale( double param0 ) {
        baseObject.setHealthScale( param0 );
    }

    @Override
    public String getName() {
        return baseObject.getName();
    }

    @Override
    public org.bukkit.inventory.PlayerInventory getInventory() {
        return baseObject.getInventory();
    }

    @Override
    public org.bukkit.inventory.Inventory getEnderChest() {
        return baseObject.getEnderChest();
    }

    @Override
    public boolean setWindowProperty( org.bukkit.inventory.InventoryView.Property param0, int param1 ) {
        return baseObject.setWindowProperty( param0, param1 );
    }

    @Override
    public org.bukkit.inventory.InventoryView getOpenInventory() {
        return baseObject.getOpenInventory();
    }

    @Override
    public org.bukkit.inventory.InventoryView openInventory( org.bukkit.inventory.Inventory param0 ) {
        return baseObject.openInventory( param0 );
    }

    @Override
    public org.bukkit.inventory.InventoryView openWorkbench( org.bukkit.Location param0, boolean param1 ) {
        return baseObject.openWorkbench( param0, param1 );
    }

    @Override
    public org.bukkit.inventory.InventoryView openEnchanting( org.bukkit.Location param0, boolean param1 ) {
        return baseObject.openEnchanting( param0, param1 );
    }

    @Override
    public void openInventory( org.bukkit.inventory.InventoryView param0 ) {
        baseObject.openInventory( param0 );
    }

    @Override
    public void closeInventory() {
        baseObject.closeInventory();
    }

    @Override
    public org.bukkit.inventory.ItemStack getItemInHand() {
        return baseObject.getItemInHand();
    }

    @Override
    public void setItemInHand( org.bukkit.inventory.ItemStack param0 ) {
        baseObject.setItemInHand( param0 );
    }

    @Override
    public org.bukkit.inventory.ItemStack getItemOnCursor() {
        return baseObject.getItemOnCursor();
    }

    @Override
    public void setItemOnCursor( org.bukkit.inventory.ItemStack param0 ) {
        baseObject.setItemOnCursor( param0 );
    }

    @Override
    public boolean hasCooldown( Material material ) {
        return false;
    }

    @Override
    public int getCooldown( Material material ) {
        return 0;
    }

    @Override
    public void setCooldown( Material material, int i ) {

    }

    @Override
    public boolean isSleeping() {
        return baseObject.isSleeping();
    }

    @Override
    public int getSleepTicks() {
        return baseObject.getSleepTicks();
    }

    @Override
    public org.bukkit.GameMode getGameMode() {
        return baseObject.getGameMode();
    }

    @Override
    public void setGameMode( org.bukkit.GameMode param0 ) {
        baseObject.setGameMode( param0 );
    }

    @Override
    public boolean isBlocking() {
        return baseObject.isBlocking();
    }

    @Override
    public boolean isHandRaised() {
        return false;
    }

    @Override
    public int getExpToLevel() {
        return baseObject.getExpToLevel();
    }

    @Override
    public boolean discoverRecipe( NamespacedKey namespacedKey ) {
        return false;
    }

    @Override
    public int discoverRecipes( Collection<NamespacedKey> collection ) {
        return 0;
    }

    @Override
    public boolean undiscoverRecipe( NamespacedKey namespacedKey ) {
        return false;
    }

    @Override
    public int undiscoverRecipes( Collection<NamespacedKey> collection ) {
        return 0;
    }

    @Override
    public Entity getShoulderEntityLeft() {
        return null;
    }

    @Override
    public void setShoulderEntityLeft( Entity entity ) {

    }

    @Override
    public Entity getShoulderEntityRight() {
        return null;
    }

    @Override
    public void setShoulderEntityRight( Entity entity ) {

    }

    @Override
    public double getEyeHeight() {
        return baseObject.getEyeHeight();
    }

    @Override
    public double getEyeHeight( boolean param0 ) {
        return baseObject.getEyeHeight( param0 );
    }

    @Override
    public org.bukkit.Location getEyeLocation() {
        return baseObject.getEyeLocation();
    }

    @Override
    public <T extends Projectile> T launchProjectile( Class<? extends T> param0 ) {
        return baseObject.launchProjectile( param0 );
    }

    @Override
    public int getRemainingAir() {
        return baseObject.getRemainingAir();
    }

    @Override
    public void setRemainingAir( int param0 ) {
        baseObject.setRemainingAir( param0 );
    }

    @Override
    public int getMaximumAir() {
        return baseObject.getMaximumAir();
    }

    @Override
    public void setMaximumAir( int param0 ) {
        baseObject.setMaximumAir( param0 );
    }

    @Override
    public int getMaximumNoDamageTicks() {
        return baseObject.getMaximumNoDamageTicks();
    }

    @Override
    public void setMaximumNoDamageTicks( int param0 ) {
        baseObject.setMaximumNoDamageTicks( param0 );
    }

    @Override
    public double getLastDamage() {
        return baseObject.getLastDamage();
    }

    @Override
    public void setLastDamage( double param0 ) {
        baseObject.setLastDamage( param0 );
    }

    @Override
    public int getNoDamageTicks() {
        return baseObject.getNoDamageTicks();
    }

    @Override
    public void setNoDamageTicks( int param0 ) {
        baseObject.setNoDamageTicks( param0 );
    }

    @Override
    public org.bukkit.entity.Player getKiller() {
        return baseObject.getKiller();
    }

    @Override
    public boolean addPotionEffect( org.bukkit.potion.PotionEffect param0 ) {
        return baseObject.addPotionEffect( param0 );
    }

    @Override
    public boolean addPotionEffect( org.bukkit.potion.PotionEffect param0, boolean param1 ) {
        return baseObject.addPotionEffect( param0, param1 );
    }

    @Override
    public boolean addPotionEffects( java.util.Collection<org.bukkit.potion.PotionEffect> param0 ) {
        return baseObject.addPotionEffects( param0 );
    }

    @Override
    public boolean hasPotionEffect( org.bukkit.potion.PotionEffectType param0 ) {
        return baseObject.hasPotionEffect( param0 );
    }

    @Override
    public void removePotionEffect( org.bukkit.potion.PotionEffectType param0 ) {
        baseObject.removePotionEffect( param0 );
    }

    @Override
    public java.util.Collection<org.bukkit.potion.PotionEffect> getActivePotionEffects() {
        return baseObject.getActivePotionEffects();
    }

    @Override
    public boolean hasLineOfSight( org.bukkit.entity.Entity param0 ) {
        return baseObject.hasLineOfSight( param0 );
    }

    @Override
    public boolean getRemoveWhenFarAway() {
        return baseObject.getRemoveWhenFarAway();
    }

    @Override
    public void setRemoveWhenFarAway( boolean param0 ) {
        baseObject.setRemoveWhenFarAway( param0 );
    }

    @Override
    public org.bukkit.inventory.EntityEquipment getEquipment() {
        return baseObject.getEquipment();
    }

    @Override
    public boolean getCanPickupItems() {
        return baseObject.getCanPickupItems();
    }

    @Override
    public void setCanPickupItems( boolean param0 ) {
        baseObject.setCanPickupItems( param0 );
    }

    @Override
    public String getCustomName() {
        return baseObject.getCustomName();
    }

    @Override
    public void setCustomName( String param0 ) {
        baseObject.setCustomName( param0 );
    }

    @Override
    public boolean isCustomNameVisible() {
        return baseObject.isCustomNameVisible();
    }

    @Override
    public void setCustomNameVisible( boolean param0 ) {
        baseObject.setCustomNameVisible( param0 );
    }

    @Override
    public boolean isLeashed() {
        return baseObject.isLeashed();
    }

    @Override
    public org.bukkit.entity.Entity getLeashHolder() {
        return baseObject.getLeashHolder();
    }

    @Override
    public boolean setLeashHolder( org.bukkit.entity.Entity param0 ) {
        return baseObject.setLeashHolder( param0 );
    }

    @Override
    public org.bukkit.Location getLocation() {
        return baseObject.getLocation();
    }

    @Override
    public org.bukkit.Location getLocation( org.bukkit.Location param0 ) {
        return baseObject.getLocation( param0 );
    }

    @Override
    public org.bukkit.util.Vector getVelocity() {
        return baseObject.getVelocity();
    }

    @Override
    public void setVelocity( org.bukkit.util.Vector param0 ) {
        baseObject.setVelocity( param0 );
    }

    @Override
    public double getHeight() {
        return 0;
    }

    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return null;
    }

    @Override
    public org.bukkit.World getWorld() {
        return baseObject.getWorld();
    }

    @Override
    public boolean teleport( org.bukkit.Location param0 ) {
        return baseObject.teleport( param0 );
    }

    @Override
    public boolean teleport( org.bukkit.Location param0, org.bukkit.event.player.PlayerTeleportEvent.TeleportCause param1 ) {
        return baseObject.teleport( param0, param1 );
    }

    @Override
    public boolean teleport( org.bukkit.entity.Entity param0 ) {
        return baseObject.teleport( param0 );
    }

    @Override
    public boolean teleport( org.bukkit.entity.Entity param0, org.bukkit.event.player.PlayerTeleportEvent.TeleportCause param1 ) {
        return baseObject.teleport( param0, param1 );
    }

    @Override
    public List<org.bukkit.entity.Entity> getNearbyEntities( double param0, double param1, double param2 ) {
        return baseObject.getNearbyEntities( param0, param1, param2 );
    }

    @Override
    public int getEntityId() {
        return baseObject.getEntityId();
    }

    @Override
    public int getFireTicks() {
        return baseObject.getFireTicks();
    }

    @Override
    public void setFireTicks( int param0 ) {
        baseObject.setFireTicks( param0 );
    }

    @Override
    public int getMaxFireTicks() {
        return baseObject.getMaxFireTicks();
    }

    @Override
    public void remove() {
        baseObject.remove();
    }

    @Override
    public boolean isDead() {
        return baseObject.isDead();
    }

    @Override
    public boolean isValid() {
        return baseObject.isValid();
    }

    @Override
    public org.bukkit.Server getServer() {
        return baseObject.getServer();
    }

    @Override
    public boolean isPersistent() {
        return false;
    }

    @Override
    public void setPersistent( boolean b ) {

    }

    @Override
    public org.bukkit.entity.Entity getPassenger() {
        return baseObject.getPassenger();
    }

    @Override
    public boolean setPassenger( org.bukkit.entity.Entity param0 ) {
        return baseObject.setPassenger( param0 );
    }

    @Override
    public List<Entity> getPassengers() {
        return null;
    }

    @Override
    public boolean addPassenger( Entity entity ) {
        return false;
    }

    @Override
    public boolean removePassenger( Entity entity ) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return baseObject.isEmpty();
    }

    @Override
    public boolean eject() {
        return baseObject.eject();
    }

    @Override
    public float getFallDistance() {
        return baseObject.getFallDistance();
    }

    @Override
    public void setFallDistance( float param0 ) {
        baseObject.setFallDistance( param0 );
    }

    @Override
    public org.bukkit.event.entity.EntityDamageEvent getLastDamageCause() {
        return baseObject.getLastDamageCause();
    }

    @Override
    public void setLastDamageCause( org.bukkit.event.entity.EntityDamageEvent param0 ) {
        baseObject.setLastDamageCause( param0 );
    }

    @Override
    public java.util.UUID getUniqueId() {
        return baseObject.getUniqueId();
    }

    @Override
    public int getTicksLived() {
        return baseObject.getTicksLived();
    }

    @Override
    public void setTicksLived( int param0 ) {
        baseObject.setTicksLived( param0 );
    }

    @Override
    public void playEffect( org.bukkit.EntityEffect param0 ) {
        baseObject.playEffect( param0 );
    }

    @Override
    public org.bukkit.entity.EntityType getType() {
        return baseObject.getType();
    }

    @Override
    public boolean isInsideVehicle() {
        return baseObject.isInsideVehicle();
    }

    @Override
    public boolean leaveVehicle() {
        return baseObject.leaveVehicle();
    }

    @Override
    public org.bukkit.entity.Entity getVehicle() {
        return baseObject.getVehicle();
    }

    @Override
    public void setMetadata( String param0, org.bukkit.metadata.MetadataValue param1 ) {
        baseObject.setMetadata( param0, param1 );
    }

    @Override
    public List<org.bukkit.metadata.MetadataValue> getMetadata( String param0 ) {
        return baseObject.getMetadata( param0 );
    }

    @Override
    public boolean hasMetadata( String param0 ) {
        return baseObject.hasMetadata( param0 );
    }

    @Override
    public void removeMetadata( String param0, org.bukkit.plugin.Plugin param1 ) {
        baseObject.removeMetadata( param0, param1 );
    }

    @Override
    public void damage( double param0 ) {
        baseObject.damage( param0 );
    }

    @Override
    public void damage( double param0, org.bukkit.entity.Entity param1 ) {
        baseObject.damage( param0, param1 );
    }

    @Override
    public double getHealth() {
        return baseObject.getHealth();
    }

    @Override
    public void setHealth( double param0 ) {
        baseObject.setHealth( param0 );
    }

    @Override
    public double getMaxHealth() {
        return baseObject.getMaxHealth();
    }

    @Override
    public void setMaxHealth( double param0 ) {
        baseObject.setMaxHealth( param0 );
    }

    @Override
    public void resetMaxHealth() {
        baseObject.resetMaxHealth();
    }

    @Override
    public boolean isPermissionSet( String param0 ) {
        return baseObject.isPermissionSet( param0 );
    }

    @Override
    public boolean isPermissionSet( org.bukkit.permissions.Permission param0 ) {
        return baseObject.isPermissionSet( param0 );
    }

    @Override
    public boolean hasPermission( String param0 ) {
        return baseObject.hasPermission( param0 );
    }

    @Override
    public boolean hasPermission( org.bukkit.permissions.Permission param0 ) {
        return baseObject.hasPermission( param0 );
    }

    @Override
    public org.bukkit.permissions.PermissionAttachment addAttachment( org.bukkit.plugin.Plugin param0, String param1, boolean param2 ) {
        return baseObject.addAttachment( param0, param1, param2 );
    }

    @Override
    public org.bukkit.permissions.PermissionAttachment addAttachment( org.bukkit.plugin.Plugin param0 ) {
        return baseObject.addAttachment( param0 );
    }

    @Override
    public org.bukkit.permissions.PermissionAttachment addAttachment( org.bukkit.plugin.Plugin param0, String param1, boolean param2, int param3 ) {
        return baseObject.addAttachment( param0, param1, param2, param3 );
    }

    @Override
    public org.bukkit.permissions.PermissionAttachment addAttachment( org.bukkit.plugin.Plugin param0, int param1 ) {
        return baseObject.addAttachment( param0, param1 );
    }

    @Override
    public void removeAttachment( org.bukkit.permissions.PermissionAttachment param0 ) {
        baseObject.removeAttachment( param0 );
    }

    @Override
    public void recalculatePermissions() {
        baseObject.recalculatePermissions();
    }

    @Override
    public Set<org.bukkit.permissions.PermissionAttachmentInfo> getEffectivePermissions() {
        return baseObject.getEffectivePermissions();
    }

    @Override
    public boolean isOp() {
        return baseObject.isOp();
    }

    @Override
    public void setOp( boolean param0 ) {
        baseObject.setOp( param0 );
    }

    @Override
    public boolean isConversing() {
        return baseObject.isConversing();
    }

    @Override
    public void acceptConversationInput( String param0 ) {
        baseObject.acceptConversationInput( param0 );
    }

    @Override
    public boolean beginConversation( org.bukkit.conversations.Conversation param0 ) {
        return baseObject.beginConversation( param0 );
    }

    @Override
    public void abandonConversation( org.bukkit.conversations.Conversation param0 ) {
        baseObject.abandonConversation( param0 );
    }

    @Override
    public void abandonConversation( org.bukkit.conversations.Conversation param0, org.bukkit.conversations.ConversationAbandonedEvent param1 ) {
        baseObject.abandonConversation( param0, param1 );
    }

    @Override
    public boolean isOnline() {
        return baseObject.isOnline();
    }

    @Override
    public boolean isBanned() {
        return baseObject.isBanned();
    }

    @Override
    public boolean isWhitelisted() {
        return baseObject.isWhitelisted();
    }

    @Override
    public void setWhitelisted( boolean param0 ) {
        baseObject.setWhitelisted( param0 );
    }

    @Override
    public org.bukkit.entity.Player getPlayer() {
        return baseObject.getPlayer();
    }

    @Override
    public long getFirstPlayed() {
        return baseObject.getFirstPlayed();
    }

    @Override
    public long getLastPlayed() {
        return baseObject.getLastPlayed();
    }

    @Override
    public boolean hasPlayedBefore() {
        return baseObject.hasPlayedBefore();
    }

    @Override
    public java.util.Map<String, Object> serialize() {
        return baseObject.serialize();
    }

    @Override
    public void sendPluginMessage( org.bukkit.plugin.Plugin param0, String param1, byte[] param2 ) {
        baseObject.sendPluginMessage( param0, param1, param2 );
    }

    @Override
    public Set<String> getListeningPluginChannels() {
        return baseObject.getListeningPluginChannels();
    }

    public void setResourcePack( String arg0 ) {
        baseObject.setResourcePack( arg0 );
    }

    @Override
    public void setResourcePack( String s, byte[] bytes ) {

    }

    public void removeAchievement( Achievement achievement ) {
        baseObject.removeAchievement( achievement );
    }

    public boolean hasAchievement( Achievement achievement ) {
        return baseObject.hasAchievement( achievement );
    }

    public void decrementStatistic( Statistic statistic ) throws IllegalArgumentException {
        baseObject.decrementStatistic( statistic );
    }

    public void decrementStatistic( Statistic statistic, int amount ) throws IllegalArgumentException {
        baseObject.decrementStatistic( statistic, amount );
    }

    public void setStatistic( Statistic statistic, int newValue ) throws IllegalArgumentException {
        baseObject.setStatistic( statistic, newValue );
    }

    public int getStatistic( Statistic statistic ) throws IllegalArgumentException {
        return baseObject.getStatistic( statistic );
    }

    public void decrementStatistic( Statistic statistic, Material material ) throws IllegalArgumentException {
        baseObject.decrementStatistic( statistic, material );
    }

    public int getStatistic( Statistic statistic, Material material ) throws IllegalArgumentException {
        return baseObject.getStatistic( statistic, material );
    }

    public void decrementStatistic( Statistic statistic, Material material, int amount ) throws IllegalArgumentException {
        baseObject.decrementStatistic( statistic, material, amount );
    }

    public void setStatistic( Statistic statistic, Material material, int newValue ) throws IllegalArgumentException {
        baseObject.setStatistic( statistic, material, newValue );
    }

    public void incrementStatistic( Statistic statistic, EntityType entityType ) throws IllegalArgumentException {
        baseObject.incrementStatistic( statistic, entityType );
    }

    public void decrementStatistic( Statistic statistic, EntityType entityType ) throws IllegalArgumentException {
        baseObject.decrementStatistic( statistic, entityType );
    }

    public int getStatistic( Statistic statistic, EntityType entityType ) throws IllegalArgumentException {
        return baseObject.getStatistic( statistic, entityType );
    }

    public void incrementStatistic( Statistic statistic, EntityType entityType, int amount ) throws IllegalArgumentException {
        baseObject.incrementStatistic( statistic, entityType, amount );
    }

    public void decrementStatistic( Statistic statistic, EntityType entityType, int amount ) {
        baseObject.decrementStatistic( statistic, entityType, amount );
    }

    public void setStatistic( Statistic statistic, EntityType entityType, int newValue ) {
        baseObject.setStatistic( statistic, entityType, newValue );
    }

    public <T extends Projectile> T launchProjectile( Class<? extends T> projectile, Vector velocity ) {
        return baseObject.launchProjectile( projectile, velocity );
    }

    public void sendSignChange( Location lctn, String[] strings ) throws IllegalArgumentException {
        baseObject.sendSignChange( lctn, strings );
    }

    public Entity getSpectatorTarget() {
        return baseObject.getSpectatorTarget();
    }

    public void setSpectatorTarget( Entity entity ) {
        baseObject.setSpectatorTarget( entity );
    }

    public void sendTitle( String string, String string1 ) {
        baseObject.sendTitle( string, string1 );
    }

    @Override
    public void sendTitle( String s, String s1, int i, int i1, int i2 ) {

    }

    public void resetTitle() {
        baseObject.resetTitle();
    }

    public void spawnParticle( Particle prtcl, Location lctn, int i ) {
        baseObject.spawnParticle( prtcl, lctn, i );
    }

    public void spawnParticle( Particle prtcl, double d, double d1, double d2, int i ) {
        baseObject.spawnParticle( prtcl, d, d1, d2, i );
    }

    public <T> void spawnParticle( Particle prtcl, Location lctn, int i, T t ) {
        baseObject.spawnParticle( prtcl, lctn, i, t );
    }

    public <T> void spawnParticle( Particle prtcl, double d, double d1, double d2, int i, T t ) {
        baseObject.spawnParticle( prtcl, d, d1, d2, i, t );
    }

    public void spawnParticle( Particle prtcl, Location lctn, int i, double d, double d1, double d2 ) {
        baseObject.spawnParticle( prtcl, lctn, i, d, d1, d2 );
    }

    public void spawnParticle( Particle prtcl, double d, double d1, double d2, int i, double d3, double d4, double d5 ) {
        baseObject.spawnParticle( prtcl, d, d1, d2, i, d3, d4, d5 );
    }

    public <T> void spawnParticle( Particle prtcl, Location lctn, int i, double d, double d1, double d2, T t ) {
        baseObject.spawnParticle( prtcl, lctn, i, d, d1, d2, t );
    }

    public <T> void spawnParticle( Particle prtcl, double d, double d1, double d2, int i, double d3, double d4, double d5, T t ) {
        baseObject.spawnParticle( prtcl, d, d1, d2, i, d3, d4, d5, t );
    }

    public void spawnParticle( Particle prtcl, Location lctn, int i, double d, double d1, double d2, double d3 ) {
        baseObject.spawnParticle( prtcl, lctn, i, d, d1, d2, d3 );
    }

    public void spawnParticle( Particle prtcl, double d, double d1, double d2, int i, double d3, double d4, double d5, double d6 ) {
        baseObject.spawnParticle( prtcl, d, d1, d2, i, d3, d4, d5, d6 );
    }

    public <T> void spawnParticle( Particle prtcl, Location lctn, int i, double d, double d1, double d2, double d3, T t ) {
        baseObject.spawnParticle( prtcl, lctn, i, d, d1, d2, d3, t );
    }

    public <T> void spawnParticle( Particle prtcl, double d, double d1, double d2, int i, double d3, double d4, double d5, double d6, T t ) {
        baseObject.spawnParticle( prtcl, d, d1, d2, i, d3, d4, d5, d6, t );
    }

    @Override
    public AdvancementProgress getAdvancementProgress( Advancement advancement ) {
        return baseObject.getAdvancementProgress( advancement );
    }

    @Override
    public String getLocale() {
        return baseObject.getLocale();
    }

    @Override
    public void updateCommands() {
        baseObject.updateCommands();
    }

    @Override
    public Spigot spigot() {
        return baseObject.spigot();
    }

    public InventoryView openMerchant( Villager vlgr, boolean bln ) {
        return baseObject.openMerchant( vlgr, bln );
    }

    @Override
    public InventoryView openMerchant( Merchant merchant, boolean b ) {
        return baseObject.openMerchant( merchant, b );
    }

    public List<Block> getLineOfSight( Set<Material> set, int i ) {
        return baseObject.getLineOfSight( set, i );
    }

    public Block getTargetBlock( Set<Material> set, int i ) {
        return baseObject.getTargetBlock( set, i );
    }

    public List<Block> getLastTwoTargetBlocks( Set<Material> set, int i ) {
        return baseObject.getLastTwoTargetBlocks( set, i );
    }

    @Override
    public Block getTargetBlockExact( int i ) {
        return baseObject.getTargetBlockExact( i );
    }

    @Override
    public Block getTargetBlockExact( int i, FluidCollisionMode fluidCollisionMode ) {
        return baseObject.getTargetBlockExact( i, fluidCollisionMode );
    }

    @Override
    public RayTraceResult rayTraceBlocks( double v ) {
        return baseObject.rayTraceBlocks( v );
    }

    @Override
    public RayTraceResult rayTraceBlocks( double v, FluidCollisionMode fluidCollisionMode ) {
        return baseObject.rayTraceBlocks( v, fluidCollisionMode );
    }

    public AttributeInstance getAttribute( Attribute atrbt ) {
        return baseObject.getAttribute( atrbt );
    }

    public boolean isGlowing() {
        return baseObject.isGlowing();
    }

    public void setGlowing( boolean bln ) {
        baseObject.setGlowing( bln );
    }

    public MainHand getMainHand() {
        return baseObject.getMainHand();
    }

    public boolean isGliding() {
        return baseObject.isGliding();
    }

    public void setGliding( boolean bln ) {
        baseObject.setGliding( bln );
    }

    @Override
    public boolean isSwimming() {
        return baseObject.isSwimming();
    }

    @Override
    public void setSwimming( boolean b ) {
        baseObject.setSwimming( b );
    }

    @Override
    public boolean isRiptiding() {
        return baseObject.isRiptiding();
    }

    public void setAI( boolean bln ) {
        baseObject.setAI( bln );
    }

    public boolean hasAI() {
        return baseObject.hasAI();
    }

    public boolean isCollidable() {
        return baseObject.isCollidable();
    }

    public void setCollidable( boolean bln ) {
        baseObject.setCollidable( bln );
    }

    public boolean isInvulnerable() {
        return baseObject.isInvulnerable();
    }

    public void setInvulnerable( boolean bln ) {
        baseObject.setInvulnerable( bln );
    }

    public void stopSound( Sound sound ) {
        baseObject.stopSound( sound );
    }

    public void stopSound( String string ) {
        baseObject.stopSound( string );
    }

    @Override
    public void stopSound( Sound sound, SoundCategory soundCategory ) {
        baseObject.stopSound( sound, soundCategory );
    }

    @Override
    public void stopSound( String s, SoundCategory soundCategory ) {
        baseObject.stopSound( s, soundCategory );
    }

    public boolean isSilent() {
        return baseObject.isSilent();
    }

    public void setSilent( boolean bln ) {
        baseObject.setSilent( bln );
    }

    public boolean hasGravity() {
        return baseObject.hasGravity();
    }

    public void setGravity( boolean bln ) {
        baseObject.setGravity( bln );
    }

    @Override
    public int getPortalCooldown() {
        return baseObject.getPortalCooldown();
    }

    @Override
    public void setPortalCooldown( int i ) {
        baseObject.setPortalCooldown( i );
    }

    @Override
    public Set<String> getScoreboardTags() {
        return baseObject.getScoreboardTags();
    }

    @Override
    public boolean addScoreboardTag( String s ) {
        return baseObject.addScoreboardTag( s );
    }

    @Override
    public boolean removeScoreboardTag( String s ) {
        return baseObject.removeScoreboardTag( s );
    }

    @Override
    public PistonMoveReaction getPistonMoveReaction() {
        return baseObject.getPistonMoveReaction();
    }

    @Override
    public BlockFace getFacing() {
        return baseObject.getFacing();
    }

    public PotionEffect getPotionEffect( PotionEffectType pet ) {
        return baseObject.getPotionEffect( pet );
    }
}
