package com.rettichlp.unicacityaddon.commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.services.utils.MathUtils;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "chatlog", usage = "(ab letzten X Minuten)")
public class ChatLogCommand extends UnicacityCommand {

    public static final Map<Long, String> chatLogMap = new HashMap<>();

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("[yyyy-MM-dd] [HH:mm:ss]");

    private final UnicacityAddon unicacityAddon;

    public ChatLogCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        String chatlog;
        if (arguments.length > 0 && MathUtils.isInteger(arguments[0])) {
            chatlog = createChatLog(Integer.parseInt(arguments[0]));
        } else {
            chatlog = createChatLog(-1);
        }

        new Thread(() -> {
            String response = upload(chatlog);
            if (response != null) {
                p.sendMessage(Message.getBuilder()
                        .info().space()
                        .of("Chatlog gespeichert unter:").color(ColorCode.WHITE).advance().space()
                        .of(response).color(ColorCode.BLUE)
                                .underline()
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Link öffnen").color(ColorCode.RED).advance().createComponent())
                                .clickEvent(ClickEvent.Action.OPEN_URL, response)
                                .advance()
                        .createComponent());
            } else {
                p.sendErrorMessage("Chatlog konnte nicht erstellt werden.");
            }
        }).start();

        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }

    private String createChatLog(int i) {
        StringBuilder chatLogStringBuilder = new StringBuilder();
        chatLogMap.keySet().stream()
                .filter(time -> i < 0 || time > (System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(i)))
                .sorted(Comparator.reverseOrder()) // revers for limit (last n items)
                .limit(1000) // get last n items
                .sorted() // revers for origin order
                .forEach(time -> {
                    String formattedTime = DATE_FORMAT.format(new Date(time));
                    String message = chatLogMap.get(time);
                    chatLogStringBuilder.append(formattedTime).append(" ").append(message).append("\n");
                });
        return chatLogStringBuilder.toString();
    }

    private String upload(String content) {
        try {
            byte[] postData = content.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;
            String request = "https://paste.labymod.net/documents";
            URL url = new URL(request);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(postData);
            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            JsonReader jsonReader = new JsonReader(in);
            jsonReader.setLenient(true);
            JsonElement jsonElement = new JsonParser().parse(jsonReader);
            if (!jsonElement.getAsJsonObject().has("key")) {
                this.unicacityAddon.logger().error("Error while uploading chatlog");
                return null;
            } else {
                String key = jsonElement.getAsJsonObject().get("key").getAsString();
                return "https://paste.labymod.net/" + key;
            }
        } catch (IOException e) {
            this.unicacityAddon.logger().warn(e.getMessage());
            return null;
        }
    }
}