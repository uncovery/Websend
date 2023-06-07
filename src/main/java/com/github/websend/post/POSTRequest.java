package com.github.websend.post;

import com.github.websend.CommandParser;
import com.github.websend.CompressionToolkit;
import com.github.websend.JSONSerializer;
import com.github.websend.Main;
import com.github.websend.Util;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.ContentType;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class POSTRequest {
            
    private final ArrayList<BasicNameValuePair> content;
    private final URL url;
    private final String jsonData;
    private Player player;

    public POSTRequest(URL url, String args[], Player player, boolean isResponse) {
        this.content = new ArrayList<>();
        this.player = player;

        if (args.length == 0) {
            Main.getMainLogger().info("POSTRequest ERROR: You need to pass arguments with /ws and /websend!");
        } else {
            Main.logDebugInfo("POSTRequest: Executing commands by player " + player.getDisplayName() + " with arguments '" + String.join("', '", args) + "'");
        }

        content.add(new BasicNameValuePair("isResponse", Boolean.toString(isResponse)));
        content.add(new BasicNameValuePair("authKey", Util.hash(Main.getSettings().getPassword())));
        content.add(new BasicNameValuePair("isCompressed", Boolean.toString(Main.getSettings().areRequestsGZipped())));
        jsonData = getJSONDataString(player, null);

        for (int i = 0; i < args.length; i++) {
            content.add(new BasicNameValuePair("args[" + i + "]", args[i]));
        }
        this.url = url;
    }

    public POSTRequest(URL url, String args[], String playerNameArg, boolean isResponse) {
        this.content = new ArrayList<>();

        if (args.length == 0) {
            Main.getMainLogger().info("POSTRequest ERROR: You need to pass arguments with /ws and /websend!");
        } else {
            Main.logDebugInfo("POSTRequest: Executing commands by player " + playerNameArg + " with arguments '" + String.join("', '", args) + "'");
        }

        content.add(new BasicNameValuePair("isResponse", Boolean.toString(isResponse)));
        content.add(new BasicNameValuePair("authKey", Util.hash(Main.getSettings().getPassword())));
        content.add(new BasicNameValuePair("isCompressed", Boolean.toString(Main.getSettings().areRequestsGZipped())));

        jsonData = getJSONDataString(null, playerNameArg);
        for (int i = 0; i < args.length; i++) {
            content.add(new BasicNameValuePair("args[" + i + "]", args[i]));
        }
        this.url = url;
    }

    public void run(HttpClient httpClient) throws IOException {
        HttpResponse response = doRequest(httpClient);

        int responseCode = response.getStatusLine().getStatusCode();
        String reason = response.getStatusLine().getReasonPhrase();

        String message = "";
        Level logLevel = Level.WARNING;

        if (responseCode >= 200 && responseCode < 300) {
            if (Main.getSettings().isDebugMode()) {
                message = "The server responded to the request with a 2xx code. Assuming request OK. (" + reason + ")";
                logLevel = Level.INFO;
            }
        } else if (responseCode >= 400) {
            message = "HTTP request failed. (" + reason + ")";
            Main.getMainLogger().log(Level.SEVERE, message);
        } else if (responseCode >= 300) {
            message = "The server responded to the request with a redirection message. Assuming request OK. (" + reason + ")";
        } else if (responseCode < 200) {
            message = "The server responded to the request with a continue or protocol switching message. Assuming request OK. (" + reason + ")";
        } else {
            message = "The server responded to the request with an unknown response code (" + responseCode + "). Assuming request OK. (" + reason + ")";
        }

        Main.logDebugInfo(logLevel, message);

        CommandParser parser = new CommandParser();
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String cur;
        while ((cur = reader.readLine()) != null) {
            parser.parse(cur, player);
        }
        reader.close();
    }

    private HttpResponse doRequest(HttpClient httpClient) throws IOException {
        HttpPost httpPost = new HttpPost(url.toString());
        httpPost.setHeader("enctype", "multipart/form-data");

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(Charset.forName("UTF-8"));
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        for (BasicNameValuePair cur : content) {
            builder.addTextBody(cur.getName(), cur.getValue());
        }
        if (Main.getSettings().areRequestsGZipped()) {
            builder.addPart("jsonData", new ByteArrayBody(CompressionToolkit.gzipString(jsonData), "jsonData"));
        } else {
            builder.addTextBody("jsonData", jsonData, ContentType.APPLICATION_JSON);
        }
        httpPost.setEntity(builder.build());
        return httpClient.execute(httpPost);
    }

    private String getJSONDataString(Player ply, String playerNameArg) throws JSONException {
        Server server = Main.getBukkitServer();
        JSONObject data = new JSONObject();
        {
            if (ply != null) {
                org.json.JSONObject jsonPlayer = null;
                try {
                    jsonPlayer = JSONSerializer.getInstance().serializePlayer(ply, true);
                } catch (CommandSyntaxException ex) {
                    Logger.getLogger(POSTRequest.class.getName()).log(Level.SEVERE, null, ex);
                }
                data.put("Invoker", jsonPlayer);
            } else if (playerNameArg != null) {
                JSONObject jsonPlayer = new JSONObject();
                {
                    jsonPlayer.put("Name", playerNameArg);
                }
                data.put("Invoker", jsonPlayer);
            } else {
                JSONObject jsonPlayer = new JSONObject();
                {
                    jsonPlayer.put("Name", "@Console");
                }
                data.put("Invoker", jsonPlayer);
            }

            JSONArray plugins = new JSONArray();
            for (Plugin plugin : server.getPluginManager().getPlugins()) {
                JSONObject plug = new JSONObject();
                plug.put("Name", plugin.getDescription().getFullName());
                plugins.put(plug);
            }
            data.put("Plugins", plugins);

            JSONObject serverSettings = new JSONObject();
            {
                serverSettings.put("Name", server.getName());
                serverSettings.put("Build", server.getVersion());
                serverSettings.put("Port", server.getPort());
                serverSettings.put("NetherEnabled", server.getAllowNether());
                serverSettings.put("FlyingEnabled", server.getAllowFlight());
                serverSettings.put("DefaultGameMode", server.getDefaultGameMode());
                serverSettings.put("OnlineMode", server.getOnlineMode());
                serverSettings.put("MaxPlayers", server.getMaxPlayers());
            }
            data.put("ServerSettings", serverSettings);

            JSONObject serverStatus = new JSONObject();
            {
                JSONArray onlinePlayers = new JSONArray();
                {
                    for (Player cur : server.getOnlinePlayers()) {
                        boolean extendedData = Main.getSettings().isExtendedPlayerDataEnabled();
                        JSONObject curPlayer = null;
                        try {
                            curPlayer = JSONSerializer.getInstance().serializePlayer(cur, extendedData);
                        } catch (CommandSyntaxException ex) {
                            Logger.getLogger(POSTRequest.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        onlinePlayers.put(curPlayer);
                    }
                }
                serverStatus.put("OnlinePlayers", onlinePlayers);
                serverStatus.put("AvailableMemory", Runtime.getRuntime().freeMemory());
                serverStatus.put("MaxMemory", Runtime.getRuntime().maxMemory());
            }
            data.put("ServerStatus", serverStatus);
        }
        return data.toString();
    }
}
