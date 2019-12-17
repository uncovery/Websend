package com.github.websend.events.listener;

import com.github.websend.Main;
import com.github.websend.events.configuration.WorldEventsConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.*;

public class WorldListener implements Listener{
    WorldEventsConfiguration config = null;

    public WorldListener(WorldEventsConfiguration config) {
        this.config = config;
    }

    @EventHandler
    public void onEvent(ChunkLoadEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_WORLD");
        }
    }

    @EventHandler
    public void onEvent(ChunkPopulateEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_WORLD");
        }
    }

    @EventHandler
    public void onEvent(ChunkUnloadEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_WORLD");
        }
    }

    @EventHandler
    public void onEvent(PortalCreateEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_WORLD");
        }
    }

    @EventHandler
    public void onEvent(SpawnChangeEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_WORLD");
        }
    }

    @EventHandler
    public void onEvent(StructureGrowEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_WORLD");
        }
    }

    @EventHandler
    public void onEvent(WorldInitEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_WORLD");
        }
    }

    @EventHandler
    public void onEvent(WorldLoadEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_WORLD");
        }
    }

    @EventHandler
    public void onEvent(WorldSaveEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_WORLD");
        }
    }

    @EventHandler
    public void onEvent(WorldUnloadEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_WORLD");
        }
    }
}
