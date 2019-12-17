package com.github.websend.events;

import com.github.websend.events.configuration.*;
import com.github.websend.events.listener.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin{
    private static Main instance;

    @Override
    public void onEnable(){
        instance = this;
        int loaded = registerListeners();
        this.getLogger().info("Enabled "+loaded+" event listening classes.");
    }

    @Override
    public void onDisable(){

    }

    public static Main getInstance() {
        return instance;
    }

    private int registerListeners(){
        //Ugly, but the most clean and balanced approach I can come up with...
        int loadedAmount = 0;

        loadedAmount += loadEventHandler(BlockEventsConfiguration.class, BlockListener.class) ? 1 : 0;
        loadedAmount += loadEventHandler(EnchantmentEventsConfiguration.class, EnchantmentListener.class) ? 1 : 0;
        loadedAmount += loadEventHandler(EntityEventsConfiguration.class, EntityListener.class) ? 1 : 0;
        loadedAmount += loadEventHandler(HangingEventsConfiguration.class, HangingListener.class) ? 1 : 0;
        loadedAmount += loadEventHandler(InventoryEventsConfiguration.class, InventoryListener.class) ? 1 : 0;
        loadedAmount += loadEventHandler(PlayerEventsConfiguration.class, PlayerListener.class) ? 1 : 0;
        loadedAmount += loadEventHandler(ServerEventsConfiguration.class, ServerListener.class) ? 1 : 0;
        loadedAmount += loadEventHandler(VehicleEventsConfiguration.class, VehicleListener.class) ? 1 : 0;
        loadedAmount += loadEventHandler(WeatherEventsConfiguration.class, WeatherListener.class) ? 1 : 0;
        loadedAmount += loadEventHandler(WorldEventsConfiguration.class, WorldListener.class) ? 1 : 0;

        return loadedAmount;
    }

    public <T extends Listener> boolean loadEventHandler(Class<? extends Configuration<T>> configClass, Class<T> listenerClass){
        Configuration<T> config = null;
        try {
            config = configClass.newInstance();
            config.loadConfiguration();
            if(!config.hasActiveEvent()){
                return false;
            }
        } catch (Exception ex) {
            String type = config == null ? "null" : config.getFilename();
            this.getLogger().log(Level.SEVERE, "Failed to load the events config file "+type+".", ex);
        }

        T listener = null;
        try {
            Constructor<T> constructor = listenerClass.getConstructor(configClass);
            listener = constructor.newInstance(config);
        } catch (SecurityException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            this.getLogger().log(Level.SEVERE, "Failed to instanciate "+listenerClass.getName(), ex);
        }

        if(listener == null){
            this.getLogger().log(Level.SEVERE, "No valid constructor found for "+listenerClass.getName());
            return false;
        }

        try {
            config.initialize();
            this.getServer().getPluginManager().registerEvents(listener, this);
            return true;
        } catch (Exception ex) {
            this.getLogger().log(Level.SEVERE, "Failed to load the events config file "+config.getFilename()+".", ex);
        }
        return false;
    }
}