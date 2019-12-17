package com.github.websend.events.configuration;

import com.github.websend.events.Main;
import com.github.websend.events.listener.ServerListener;
import java.util.logging.Level;
import org.bukkit.Bukkit;

public class ServerEventsConfiguration extends Configuration<ServerListener> {
    private int customTickDuration = 100;

    @Override
    public void initialize() {
        if(this.isEventEnabled("TickEvent")){
            Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable(){
                @Override
                public void run() {
                    String[] array = {"event", "TickEvent"};
                    com.github.websend.Main.doCommand(array, "WEBSEND_EVENTS_SERVER");
                }
            }, 20, customTickDuration);
        }
    }
    
    @Override
    public String getFilename() {
        return "server.txt";
    }

    @Override
    public String[] getEventNameList() {
        return new String[]{
                    "MapInitializeEvent",
                    "PluginDisableEvent",
                    "PluginEnableEvent",
                    "RemoteServerCommandEvent",
                    "ServerCommandEvent",
                    "ServerListPingEvent",
                    "ServiceRegisterEvent",
                    "ServiceUnregisterEvent",
                    "TickEvent"
                };
    }

    @Override
    protected String getDefaultConfig() {
        String config = super.getDefaultConfig();
        config += "TickEventDuration=100\n";
        return config;
    }

    @Override
    protected void parseLine(String line) {
        String[] parts = line.split("=");
        if(parts.length != 2){
            Main.getInstance().getLogger().log(Level.WARNING, "Invalid config line: "+line);
        }else{
            String name = parts[0].trim();
            String value = parts[1].trim();
            
            if(name.equals("TickEvent")){
                if(Boolean.parseBoolean(value.trim())){
                    super.activeEventsList.add(name);
                }
            }else if(name.equals("TickEventDuration")){
                int ticks = Integer.parseInt(value);
                if(ticks <= 4){
                    Main.getInstance().getLogger().log(Level.WARNING, 
                            "CustomTickDuration is set to a very low value. This might affect server and network performance!");
                }
                customTickDuration = ticks;
            }else{
                super.parseLine(line);
            }
        }
    }
}
