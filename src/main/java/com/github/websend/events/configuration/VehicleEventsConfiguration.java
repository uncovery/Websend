package com.github.websend.events.configuration;

import com.github.websend.events.listener.VehicleListener;

public class VehicleEventsConfiguration extends Configuration<VehicleListener> {

    @Override
    public String getFilename() {
        return "vehicle.txt";
    }

    @Override
    public String[] getEventNameList() {
        return new String[]{
                    "VehicleBlockCollisionEvent",
                    "VehicleCollisionEvent",
                    "VehicleCreateEvent",
                    "VehicleDamageEvent",
                    "VehicleDestroyEvent",
                    "VehicleEnterEvent",
                    "VehicleEntityCollisionEvent",
                    "VehicleExitEvent",
                    "VehicleMoveEvent",
                    "VehicleUpdateEvent"
                };
    }
}
