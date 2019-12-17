package com.github.websend.events.configuration;

import com.github.websend.events.Main;
import java.io.*;
import java.util.HashSet;
import java.util.logging.Level;
import org.bukkit.event.Listener;

public abstract class Configuration<T extends Listener> {
    protected HashSet<String> activeEventsList = new HashSet<String>();
    
    public void loadConfiguration() throws IOException{        
        File configFile = new File(Main.getInstance().getDataFolder(), this.getFilename());
        if(!configFile.exists()){
            Main.getInstance().getDataFolder().mkdirs();
            configFile.createNewFile();
            writeConfigurationFile(configFile);
        }
        BufferedReader reader = new BufferedReader(new FileReader(configFile));
        String line;
        while((line = reader.readLine()) != null){
            this.parseLine(line);
        }
        reader.close();
    }
    
    public boolean isEventEnabled(String eventName){
        return activeEventsList.contains(eventName);
    }
    
    public boolean hasActiveEvent(){
        return !activeEventsList.isEmpty();
    }
    
    protected void parseLine(String line){
        String[] parts = line.split("=");
        if(parts.length != 2){
            Main.getInstance().getLogger().log(Level.WARNING, "Invalid config line: "+line);
        }else{
            String name = parts[0].trim();
            String boolString = parts[1].trim();
            boolean active = Boolean.parseBoolean(boolString.trim());
            if(active){
                activeEventsList.add(name);
            }
        }
    }
    
    protected String getDefaultConfig(){
        StringBuilder builder = new StringBuilder();
        for(String str : this.getEventNameList()){
            builder.append(str+"=false\n");
        }
        return builder.toString();
    }
    
    public void writeConfigurationFile(File file) throws IOException{
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.print(getDefaultConfig());
            writer.flush();
        }
    };
    
    public abstract String getFilename();
    public abstract String[] getEventNameList();
    public void initialize(){}
}
