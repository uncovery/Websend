package com.github.websend.events.listener;

import com.github.websend.Main;
import com.github.websend.events.configuration.AdvancementEventsConfiguration;
import org.bukkit.advancement.Advancement;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class AdvancementsListener implements Listener{
    AdvancementEventsConfiguration config = null;

    public AdvancementsListener(AdvancementEventsConfiguration config) {
        this.config = config;
    }

    @EventHandler
    public void onEvent(PlayerAdvancementDoneEvent e){
        if(config.isEventEnabled(e.getEventName())){
            Advancement advancement = e.getAdvancement();
            String[] array = {"event", e.getEventName(), advancement.getKey().toString()};
            Main.doCommand(array, e.getPlayer());
        }
    }
}