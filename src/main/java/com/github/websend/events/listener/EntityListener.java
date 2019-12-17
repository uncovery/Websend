package com.github.websend.events.listener;

import com.github.websend.Main;
import com.github.websend.events.configuration.EntityEventsConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;

public class EntityListener implements Listener {
    EntityEventsConfiguration config = null;
    
    public EntityListener(EntityEventsConfiguration config) {
        this.config = config;
    } 
    
    @EventHandler
    public void onEvent(CreatureSpawnEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(CreeperPowerEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(EntityBreakDoorEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(EntityChangeBlockEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(EntityCombustByBlockEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(EntityCombustByEntityEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(EntityCombustEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(EntityCreatePortalEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(EntityDamageByBlockEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(EntityDamageByEntityEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(EntityDamageEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(EntityDeathEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(EntityExplodeEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(EntityInteractEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(EntityPortalEnterEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(EntityPortalEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(EntityPortalExitEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(EntityRegainHealthEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(EntityShootBowEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(EntityTameEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(EntityTargetEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(EntityTargetLivingEntityEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(EntityTeleportEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(EntityUnleashEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(ExpBottleEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(ExplosionPrimeEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }
    
    @EventHandler
    public void onEvent(FireworkExplodeEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(FoodLevelChangeEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(HorseJumpEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(ItemDespawnEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }
    
    @EventHandler
    public void onEvent(ItemMergeEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(ItemSpawnEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(PigZapEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(PlayerDeathEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    
    }
    @EventHandler
    public void onEvent(PlayerLeashEntityEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(PotionSplashEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(ProjectileHitEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(ProjectileLaunchEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(SheepDyeWoolEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(SheepRegrowWoolEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }

    @EventHandler
    public void onEvent(SlimeSplitEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }
    
    @EventHandler
    public void onEvent(VillagerAcquireTradeEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }
    
    @EventHandler
    public void onEvent(VillagerReplenishTradeEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENTITY");
        }
    }
}
