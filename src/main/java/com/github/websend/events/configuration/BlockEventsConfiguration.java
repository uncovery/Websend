package com.github.websend.events.configuration;

import com.github.websend.events.listener.BlockListener;

public class BlockEventsConfiguration extends Configuration<BlockListener> {

    @Override
    public String getFilename() {
        return "block.txt";
    }

    @Override
    public String[] getEventNameList() {
        return new String[]{
                    "BlockBreakEvent",
                    "BlockBurnEvent",
                    "BlockCanBuildEvent",
                    "BlockDamageEvent",
                    "BlockDispenseEvent",
                    "BlockExpEvent",
                    "BlockExplodeEvent",
                    "BlockFadeEvent",
                    "BlockFormEvent",
                    "BlockFromToEvent",
                    "BlockGrowEvent",
                    "BlockIgniteEvent",
                    "BlockPhysicsEvent",
                    "BlockPistonExtendEvent",
                    "BlockPistonRetractEvent",
                    "BlockPlaceEvent",
                    "BlockMultiPlaceEvent",
                    "BlockRedstoneEvent",
                    "CauldronLevelChangeEvent",
                    "BlockSpreadEvent",
                    "EntityBlockFormEvent",
                    "LeavesDecayEvent",
                    "NotePlayEvent",
                    "SignChangeEvent"
                };
    }
}
