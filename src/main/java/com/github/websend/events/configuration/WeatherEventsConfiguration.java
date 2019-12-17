package com.github.websend.events.configuration;

import com.github.websend.events.listener.WeatherListener;

public class WeatherEventsConfiguration extends Configuration<WeatherListener> {

    @Override
    public String getFilename() {
        return "weather.txt";
    }

    @Override
    public String[] getEventNameList() {
        return new String[]{
                    "LightningStrikeEvent",
                    "ThunderChangeEvent",
                    "WeatherChangeEvent"
                };
    }
}
