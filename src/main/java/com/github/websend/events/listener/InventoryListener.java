package com.github.websend.events.listener;

import com.github.websend.Main;
import com.github.websend.events.configuration.InventoryEventsConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;

public class InventoryListener implements Listener{
    InventoryEventsConfiguration config = null;

    public InventoryListener(InventoryEventsConfiguration config) {
        this.config = config;
    }
    
    @EventHandler
    public void onEvent(BrewEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_INVENTORY");
        }
    }

    @EventHandler
    public void onEvent(CraftItemEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_INVENTORY");
        }
    }

    @EventHandler
    public void onEvent(FurnaceBurnEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_INVENTORY");
        }
    }

    @EventHandler
    public void onEvent(FurnaceExtractEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_INVENTORY");
        }
    }

    @EventHandler
    public void onEvent(FurnaceSmeltEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_INVENTORY");
        }
    }

    @EventHandler
    public void onEvent(InventoryClickEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_INVENTORY");
        }
    }

    @EventHandler
    public void onEvent(InventoryCloseEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_INVENTORY");
        }
    }

    @EventHandler
    public void onEvent(InventoryCreativeEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_INVENTORY");
        }
    }

    @EventHandler
    public void onEvent(InventoryDragEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_INVENTORY");
        }
    }

    @EventHandler
    public void onEvent(InventoryInteractEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_INVENTORY");
        }
    }

    @EventHandler
    public void onEvent(InventoryMoveItemEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_INVENTORY");
        }
    }

    @EventHandler
    public void onEvent(InventoryOpenEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_INVENTORY");
        }
    }
    
    @EventHandler
    public void onEvent(InventoryPickupItemEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_INVENTORY");
        }
    }
    
    @EventHandler
    public void onEvent(PrepareAnvilEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_INVENTORY");
        }
    }

    @EventHandler
    public void onEvent(PrepareItemCraftEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_INVENTORY");
        }
    }
}
