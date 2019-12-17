package com.github.websend.events.listener;

import com.github.websend.Main;
import com.github.websend.events.configuration.PlayerEventsConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class PlayerListener implements Listener{
    PlayerEventsConfiguration config = null;

    public PlayerListener(PlayerEventsConfiguration config) {
        this.config = config;
    }
    
    @EventHandler
    public void onEvent(AsyncPlayerChatEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(AsyncPlayerPreLoginEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getName());
        }
    }

    @EventHandler
    public void onEvent(PlayerAnimationEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerBedEnterEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerBedLeaveEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerBucketEmptyEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerBucketFillEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerChangedWorldEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerChannelEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerChatEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerChatTabCompleteEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerCommandPreprocessEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerDropItemEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }
    
    @EventHandler
    public void onEvent(PlayerEditBookEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerEggThrowEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerExpChangeEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerFishEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerGameModeChangeEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerInteractEntityEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }
    
    @EventHandler
    public void onEvent(PlayerArmorStandManipulateEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }
    
    @EventHandler
    public void onEvent(PlayerInteractAtEntityEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerInteractEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerItemBreakEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerItemConsumeEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerItemHeldEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerJoinEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerKickEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerLevelChangeEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerLoginEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerMoveEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerPickupItemEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerPortalEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerPreLoginEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getName());
        }
    }

    @EventHandler
    public void onEvent(PlayerQuitEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }
    
    @EventHandler
    public void onEvent(PlayerResourcePackStatusEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerRegisterChannelEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerRespawnEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerShearEntityEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }
    
    @EventHandler
    public void onEvent(PlayerStatisticIncrementEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerTeleportEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerToggleFlightEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerToggleSneakEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerToggleSprintEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerUnleashEntityEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }
    
    @EventHandler
    public void onEvent(PlayerUnregisterChannelEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }

    @EventHandler
    public void onEvent(PlayerVelocityEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, e.getPlayer());
        }
    }
    
    public void onCustomEvent(String eventName, Player player){
        String[] array = {"event", eventName};
            Main.doCommand(array, player);
    }
}
