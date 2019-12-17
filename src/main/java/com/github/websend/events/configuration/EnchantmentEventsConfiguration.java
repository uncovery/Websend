package com.github.websend.events.configuration;

import com.github.websend.events.listener.EnchantmentListener;

public class EnchantmentEventsConfiguration extends Configuration<EnchantmentListener> {

    @Override
    public String getFilename() {
        return "enchantment.txt";
    }

    @Override
    public String[] getEventNameList() {
        return new String[]{
                    "EnchantItemEvent",
                    "PrepareItemEnchantEvent"
                };
    }
}
