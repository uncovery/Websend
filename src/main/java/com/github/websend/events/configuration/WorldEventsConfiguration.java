package com.github.websend.events.configuration;

import com.github.websend.events.listener.WorldListener;

public class WorldEventsConfiguration extends Configuration<WorldListener> {

    @Override
    public String getFilename() {
        return "world.txt";
    }

    @Override
    public String[] getEventNameList() {
        return new String[]{
                    "ChunkEvent",
                    "ChunkLoadEvent",
                    "ChunkPopulateEvent",
                    "ChunkUnloadEvent",
                    "PortalCreateEvent",
                    "SpawnChangeEvent",
                    "StructureGrowEvent",
                    "WorldInitEvent",
                    "WorldLoadEvent",
                    "WorldSaveEvent",
                    "WorldUnloadEvent"
                };
    }
}
