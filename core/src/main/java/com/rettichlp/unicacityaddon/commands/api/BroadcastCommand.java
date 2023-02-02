package com.rettichlp.unicacityaddon.commands.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import net.labymod.api.client.chat.command.Command;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author RettichLP
 */
@UCCommand
public class BroadcastCommand extends Command {

    private static final String usage = "/broadcast [queue|send] (dd.MM.yyyy) (HH:mm:ss) (Nachricht)";

    public BroadcastCommand() {
        super("broadcast");
    }

    /**
     * Quote: *Stille* "Hat das Vorteile?" - RettichLP zu der WG von Fio, 29.09.2022
     */
    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (arguments.length == 1 && arguments[0].equalsIgnoreCase("queue")) {
            JsonArray response = APIRequest.sendBroadcastQueueRequest();
            if (response == null)
                return true;

            p.sendEmptyMessage();
            p.sendMessage(Message.getBuilder()
                    .of("Aktive Broadcasts:").color(ColorCode.DARK_AQUA).bold().advance()
                    .createComponent());

            response.forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();
                String broadcast = o.get("broadcast").getAsString();
                String issuerName = o.get("issuerName").getAsString();

                p.sendMessage(Message.getBuilder()
                        .of("»").color(ColorCode.GRAY).advance().space()
                        .of(broadcast).color(ColorCode.AQUA).advance().space()
                        .of("(").color(ColorCode.GRAY).advance()
                        .of(issuerName).color(ColorCode.GRAY).advance()
                        .of(")").color(ColorCode.GRAY).advance()
                        .createComponent());
            });

            p.sendEmptyMessage();

        } else if (arguments.length > 3 && arguments[0].equalsIgnoreCase("send")) {
            String date = arguments[1]; // TT.MM.JJJJ
            String time = arguments[2]; // HH:MM:SS
            String message = TextUtils.makeStringByArgs(arguments, " ")
                    .replace("send ", "")
                    .replace(date + " ", "")
                    .replace(time + " ", "");

            LocalDateTime localDateTime = LocalDateTime.parse(date + " " + time, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
            long sendTime = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

            JsonObject response = APIRequest.sendBroadcastSendRequest(message, String.valueOf(sendTime));
            if (response == null)
                return true;
            p.sendAPIMessage(response.get("info").getAsString(), true);
        } else {
            p.sendSyntaxMessage(usage);
        }
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        Date now = new Date();
        return TabCompletionBuilder.getBuilder(arguments)
                .addAtIndex(1, "queue", "send")
                .addAtIndex(2, new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN).format(now))
                .addAtIndex(3, new SimpleDateFormat("HH:mm:ss", Locale.GERMAN).format(now))
                .build();
    }
}