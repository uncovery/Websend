package com.github.websend.events.configuration;

import com.github.websend.events.listener.HangingListener;

public class HangingEventsConfiguration extends Configuration<HangingListener> {

    @Override
    public String getFilename() {
        return "hanging.txt";
    }

    @Override
    public String[] getEventNameList() {
        return new String[]{
                    "HangingBreakByEntityEvent",
                    "HangingBreakEvent",
                    "HangingPlaceEvent"
                };
    }
}
