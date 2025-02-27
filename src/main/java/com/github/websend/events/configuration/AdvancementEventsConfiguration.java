package com.github.websend.events.configuration;

import com.github.websend.events.listener.AdvancementsListener;

public class AdvancementEventsConfiguration  extends Configuration<AdvancementsListener> {

    @Override
    public String getFilename() {
        return "advancement.txt";
    }

    @Override
    public String[] getEventNameList() {
        return new String[]{
                    "PlayerAdvancementDoneEvent",
                };
    }

}
