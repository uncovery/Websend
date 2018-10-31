package com.github.websend;

import com.github.websend.spigot.SpigotJSONSerializer;
import me.dpohvar.powernbt.nbt.NBTBase;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.dpohvar.powernbt.nbt.NBTContainerItem;

/**
 * Bukkit is not updated to 1.8 and the server mods based of bukkit are implementing their own patches to support 1.8
 * This is a compatibility problem since the central Bukkit API is now splitting up in several small APIs that all require seperate code to support.
 * This class provides several abstract serialization methods for subclasses to implement according to each API.
 * 
 * OVERRIDE:
 * We only support Spigot.
 * 
 */
public abstract class JSONSerializer {
    private static JSONSerializer instance = null;
    
    public static JSONSerializer getInstance(){
        if(instance == null){
            instance = new SpigotJSONSerializer();
            if (!Bukkit.getServer().getVersion().contains("Spigot")){
                // output warning here.
            }
        }
        return instance;
    }
    
    public JSONObject serializePlayer(Player ply, boolean serializeAllData) throws JSONException {
        JSONObject player = new JSONObject();
        {
            player.put("Name", ply.getName());
            player.put("UUID", ply.getUniqueId());
            player.put("UUIDVersion", ply.getUniqueId().version());
            player.put("XP", ply.getExp());
            player.put("XPLevel", ply.getLevel());
            player.put("Exhaustion", ply.getExhaustion());
            player.put("FoodLevel", ply.getFoodLevel());
            player.put("GameMode", ply.getGameMode());
            player.put("Health", ply.getHealth());
            player.put("IP", ply.getAddress().toString());
            player.put("IsOP", ply.isOp());
            if(serializeAllData){
                player.put("CurrentItemIndex", ply.getInventory().getHeldItemSlot());
                player.put("MainHandItemID", ply.getInventory().getItemInMainHand().getType().name());
                player.put("OffHandItemID", ply.getInventory().getItemInOffHand().getType().name());
                JSONObject location = serializeLocation(ply.getLocation());
                player.put("Location", location);
                JSONArray inventory = serializeInventory(ply.getInventory());
                player.put("Inventory", inventory);
            }
        }
        return player;
    }

    public JSONObject serializeLocation(Location loc) throws JSONException {
        JSONObject location = new JSONObject();
        {
            location.put("X", loc.getX());
            location.put("Y", loc.getY());
            location.put("Z", loc.getZ());
            location.put("Yaw", loc.getYaw());
            location.put("Pitch", loc.getPitch());
            location.put("World", loc.getWorld().getName());
        }
        return location;
    }

    public JSONArray serializeInventory(Inventory inv) throws JSONException {
        JSONArray inventory = new JSONArray();
        {
            for (int i = 0; i < inv.getSize(); i++) {
                ItemStack itemStack = inv.getItem(i);
                if (itemStack != null) {
                    JSONObject item = new JSONObject();
                    item.put("Slot", i);
                    item.put("TypeName", itemStack.getType().name());
                    item.put("Amount", itemStack.getAmount());                    
                    
                    // retrieve nbt data
                    if (itemStack.hasItemMeta()){
                        NBTContainerItem itemS = new NBTContainerItem(itemStack);
                        NBTBase itemTag = itemS.getTag();
                        String nbtString = itemTag.toString();
                        item.put("nbt", nbtString);
                    }
                    
                    inventory.put(item);
                    
                }
            }
        }
        return inventory;
    }
}
