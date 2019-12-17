package com.github.websend.events.configuration;

import com.github.websend.events.listener.InventoryListener;

public class InventoryEventsConfiguration extends Configuration<InventoryListener> {

    @Override
    public String getFilename() {
        return "inventory.txt";
    }

    @Override
    public String[] getEventNameList() {
        return new String[]{
                    "BrewEvent",
                    "CraftItemEvent",
                    "FurnaceBurnEvent",
                    "FurnaceExtractEvent",
                    "FurnaceSmeltEvent",
                    "InventoryClickEvent",
                    "InventoryCloseEvent",
                    "InventoryCreativeEvent",
                    "InventoryDragEvent",
                    "InventoryInteractEvent",
                    "InventoryMoveItemEvent",
                    "InventoryOpenEvent",
                    "InventoryPickupItemEvent",
                    "PrepareAnvilEvent",
                    "PrepareItemCraftEvent"};
    }
}
