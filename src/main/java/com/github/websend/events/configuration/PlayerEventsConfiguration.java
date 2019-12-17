package com.github.websend.events.configuration;

import com.github.websend.events.listener.PlayerListener;

public class PlayerEventsConfiguration extends Configuration<PlayerListener> {

    @Override
    public String getFilename() {
        return "player.txt";
    }

    @Override
    public String[] getEventNameList() {
        return new String[]{
                    "AsyncPlayerChatEvent",
                    "AsyncPlayerPreLoginEvent",
                    "PlayerAchievementAwardedEvent",
                    "PlayerAnimationEvent",
                    "PlayerArmorStandManipulateEvent",
                    "PlayerBedEnterEvent",
                    "PlayerBedLeaveEvent",
                    "PlayerBucketEmptyEvent",
                    "PlayerBucketFillEvent",
                    "PlayerChangedWorldEvent",
                    "PlayerChannelEvent",
                    "PlayerChatEvent",
                    "PlayerChatTabCompleteEvent",
                    "PlayerCommandPreprocessEvent",
                    "PlayerDropItemEvent",
                    "PlayerEditBookEvent",
                    "PlayerEggThrowEvent",
                    "PlayerExpChangeEvent",
                    "PlayerFishEvent",
                    "PlayerGameModeChangeEvent",
                    "PlayerInteractEntityEvent",
                    "PlayerInteractAtEntityEvent",
                    "PlayerInteractEvent",
                    "PlayerInventoryEvent",
                    "PlayerItemBreakEvent",
                    "PlayerItemConsumeEvent",
                    "PlayerItemHeldEvent",
                    "PlayerJoinEvent",
                    "PlayerKickEvent",
                    "PlayerLevelChangeEvent",
                    "PlayerLoginEvent",
                    "PlayerMoveEvent",
                    "PlayerPickupItemEvent",
                    "PlayerPortalEvent",
                    "PlayerPreLoginEvent",
                    "PlayerQuitEvent",
                    "PlayerResourcePackStatusEvent",
                    "PlayerRegisterChannelEvent",
                    "PlayerRespawnEvent",
                    "PlayerShearEntityEvent",
                    "PlayerStatisticIncrementEvent",
                    "PlayerTeleportEvent",
                    "PlayerToggleFlightEvent",
                    "PlayerToggleSneakEvent",
                    "PlayerToggleSprintEvent",
                    "PlayerUnleashEntityEvent",
                    "PlayerUnregisterChannelEvent",
                    "PlayerVelocityEvent"};
    }
}
