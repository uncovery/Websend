package com.github.websend;

import com.github.websend.spigot.SpigotJSONSerializer;
import com.google.gson.Gson;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.MojangsonParser;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        JSONArray inventory = new JSONArray(); {
            for (int i = 0; i < inv.getSize(); i++) {
                ItemStack itemStack = inv.getItem(i);
                if (itemStack != null) {
                    JSONObject item = new JSONObject();
                    item.put("Slot", i);

                    item.put("TypeName", itemStack.getType().name());
                    item.put("Amount", itemStack.getAmount());

                    if (itemStack.hasItemMeta()) {
                        // method to get nbt json from the legacy minecraft code
                        net.minecraft.world.item.ItemStack CBStack = CraftItemStack.asNMSCopy(itemStack);
                        NBTTagCompound itemTag = CBStack.getTag();
                        Gson gson = new Gson();
                        String json = gson.toJson(itemTag);
                        item.put("nbt_json", json);

                        // method to get nbt_raw without converting it to json
                        // this is the current default instead of using the JSON above
                        String tagString = itemTag.toString();

                        try {
                            String parsedString = MojangsonParser.parse(tagString).toString();
                            item.put("nbt_raw", parsedString);
                        } catch (CommandSyntaxException ex) {
                            Logger.getLogger(JSONSerializer.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        // method to get nbt from the spigot API instead of the legacy minecraft code
                        // the issue here is that enchantments have the wrong name, e.g. "SWEEPING_EDGE" instead of "minecraft:sweeping"

                        Main.logDebugInfo("embedding item nbt_json_api for inventory slot " + i);
                        Map<String, Object> serialMeta;

                        serialMeta = itemStack.getItemMeta().serialize();
                        String json2 = serialMeta.toString();
                        // converting to JSON does not work. this would cause a crash, so we rather let this happen on the PHP side.
                        // main issue here is how crossbows once they were charged behave.
                        //
                        // String json2 = gson2.toJson(testing);
                        item.put("nbt_json_api_string", json2);
                    }

                    inventory.put(item);
                }
            }
        }
        return inventory;
    }

}
