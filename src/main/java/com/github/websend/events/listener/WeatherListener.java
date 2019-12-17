package com.github.websend.events.listener;

import com.github.websend.Main;
import com.github.websend.events.configuration.WeatherEventsConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.*;

public class WeatherListener implements Listener{
    WeatherEventsConfiguration config = null;

    public WeatherListener(WeatherEventsConfiguration config) {
        this.config = config;
    }
    
    @EventHandler
    public void onEvent(LightningStrikeEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_WEATHER");
        }
    }

    @EventHandler
    public void onEvent(ThunderChangeEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_WEATHER");
        }
    }

    @EventHandler
    public void onEvent(WeatherChangeEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_WEATHER");
        }
    }
}
