package com.github.websend;

import com.github.websend.spigot.SpigotJSONSerializer;
import com.google.gson.Gson;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.server.v1_15_R1.MojangsonParser;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public abstract class JSONSerializer {
    private static JSONSerializer instance = null;

    public static JSONSerializer getInstance(){
        if(instance == null){
            instance = new SpigotJSONSerializer();
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

                    if (itemStack.hasItemMeta()) {
                        Map newStack = itemStack.getItemMeta().serialize();

                        net.minecraft.server.v1_15_R1.ItemStack CBStack = CraftItemStack.asNMSCopy(itemStack);
                        NBTTagCompound itemTag = CBStack.getTag();

                        Gson gson = new Gson();
                        String json = gson.toJson(itemTag);
                        item.put("nbt_json", json);

                        String tagString = itemTag.toString();

                        try {
                            String parsedString = MojangsonParser.parse(tagString).toString();
                            item.put("nbt_raw", parsedString);
                        } catch (CommandSyntaxException ex) {
                            Logger.getLogger(JSONSerializer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    inventory.put(item);
                }
            }
        }
        return inventory;
    }

}
