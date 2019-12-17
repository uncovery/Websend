package com.github.websend.events.configuration;

import com.github.websend.events.listener.EntityListener;

public class EntityEventsConfiguration extends Configuration<EntityListener> {

    @Override
    public String getFilename() {
        return "entity.txt";
    }

    @Override
    public String[] getEventNameList() {
        return new String[]{
                    "CreatureSpawnEvent",
                    "CreeperPowerEvent",
                    "EntityBreakDoorEvent",
                    "EntityChangeBlockEvent",
                    "EntityCombustByBlockEvent",
                    "EntityCombustByEntityEvent",
                    "EntityCombustEvent",
                    "EntityCreatePortalEvent",
                    "EntityDamageByBlockEvent",
                    "EntityDamageByEntityEvent",
                    "EntityDamageEvent",
                    "EntityDeathEvent",
                    "EntityExplodeEvent",
                    "EntityInteractEvent",
                    "EntityPortalEnterEvent",
                    "EntityPortalEvent",
                    "EntityPortalExitEvent",
                    "EntityRegainHealthEvent",
                    "EntityShootBowEvent",
                    "EntityTameEvent",
                    "EntityTargetEvent",
                    "EntityTargetLivingEntityEvent",
                    "EntityTeleportEvent",
                    "EntityUnleashEvent",
                    "ExpBottleEvent",
                    "ExplosionPrimeEvent",
                    "FireworkExplodeEvent",
                    "FoodLevelChangeEvent",
                    "HorseJumpEvent",
                    "ItemDespawnEvent",
                    "ItemMergeEvent",
                    "ItemSpawnEvent",
                    "PigZapEvent",
                    "PlayerDeathEvent",
                    "PlayerLeashEntityEvent",
                    "PotionSplashEvent",
                    "ProjectileHitEvent",
                    "ProjectileLaunchEvent",
                    "SheepDyeWoolEvent",
                    "SheepRegrowWoolEvent",
                    "SlimeSplitEvent",
                    "VillagerAcquireTradeEvent",
                    "VillagerReplenishTradeEvent"
                };
    }
}
