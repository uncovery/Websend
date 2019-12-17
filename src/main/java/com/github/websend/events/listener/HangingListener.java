package com.github.websend.events.listener;

import com.github.websend.Main;
import com.github.websend.events.configuration.HangingEventsConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.*;

public class HangingListener implements Listener{
    HangingEventsConfiguration config = null;

    public HangingListener(HangingEventsConfiguration config) {
        this.config = config;
    }
    
    @EventHandler
    public void onEvent(HangingBreakByEntityEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_HANGING");
        }
    }

    @EventHandler
    public void onEvent(HangingBreakEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_HANGING");
        }
    }

    @EventHandler
    public void onEvent(HangingPlaceEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_HANGING");
        }
    }
}
