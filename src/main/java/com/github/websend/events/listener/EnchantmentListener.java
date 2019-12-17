package com.github.websend.events.listener;

import com.github.websend.Main;
import com.github.websend.events.configuration.EnchantmentEventsConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.*;

public class EnchantmentListener implements Listener{
    EnchantmentEventsConfiguration config = null;

    public EnchantmentListener(EnchantmentEventsConfiguration config) {
        this.config = config;
    }
    
    @EventHandler
    public void onEvent(PrepareItemEnchantEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENCHANTMENT");
        }
    }

    @EventHandler
    public void onEvent(EnchantItemEvent e){
        if(config.isEventEnabled(e.getEventName())){
            String[] array = {"event", e.getEventName()};
            Main.doCommand(array, "WEBSEND_EVENTS_ENCHANTMENT");
        }
    }
}
