package com.github.websend.events.listener;

import com.github.websend.Main;
import com.github.websend.events.configuration.ServerEventsConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.*;

public class ServerListener implements Listener{
    ServerEventsConfiguration config = null;

    public ServerListener(ServerEventsConfiguration config) {
        this.config = config;
    }
    
    @EventHandler
    public void onEvent(MapInitializeEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_SERVER");
        }
    }

    @EventHandler
    public void onEvent(PluginDisableEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_SERVER");
        }
    }

    @EventHandler
    public void onEvent(PluginEnableEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_SERVER");
        }
    }

    //@EventHandler
    public void onEvent(PluginEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_SERVER");
        }
    }

    @EventHandler
    public void onEvent(RemoteServerCommandEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_SERVER");
        }
    }

    @EventHandler
    public void onEvent(ServerCommandEvent e){
        System.out.println(e.getEventName());
        if(config.isEventEnabled(e.getEventName())){
            System.out.println("ServerCommandEvent happened!");
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_SERVER");
        }
    }

    @EventHandler
    public void onEvent(ServerListPingEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_SERVER");
        }
    }

    @EventHandler
    public void onEvent(ServiceRegisterEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_SERVER");
        }
    }

    @EventHandler
    public void onEvent(ServiceUnregisterEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_SERVER");
        }
    }
}
