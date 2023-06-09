package com.rettichlp.unicacityaddon.commands.api;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;

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
@UCCommand(prefix = "broadcast", onlyOnUnicacity = false, usage = "[queue|send] (dd.MM.yyyy) (HH:mm:ss) (Nachricht)")
public class BroadcastCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public BroadcastCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    /**
     * Quote: *Stille* "Hat Das Vorteile?" - RettichLP zu der WG von Fio, 29.09.2022
     */
    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        new Thread(() -> {
            if (arguments.length == 1 && arguments[0].equalsIgnoreCase("queue")) {
                p.sendEmptyMessage();
                p.sendMessage(Message.getBuilder()
                        .of("Aktive Broadcasts:").color(ColorCode.DARK_AQUA).bold().advance()
                        .createComponent());

                this.unicacityAddon.api().sendBroadcastQueueRequest().forEach(broadcast -> {
                    String message = broadcast.getBroadcast();
                    String issuerName = broadcast.getIssuerName();

                    p.sendMessage(Message.getBuilder()
                            .of("»").color(ColorCode.GRAY).advance().space()
                            .of(message).color(ColorCode.AQUA).advance().space()
                            .of("(").color(ColorCode.GRAY).advance()
                            .of(issuerName).color(ColorCode.GRAY).advance()
                            .of(")").color(ColorCode.GRAY).advance()
                            .createComponent());
                });

                p.sendEmptyMessage();
            } else if (arguments.length > 3 && arguments[0].equalsIgnoreCase("send")) {
                String date = arguments[1]; // TT.MM.JJJJ
                String time = arguments[2]; // HH:MM:SS
                String message = this.unicacityAddon.services().utilService().textUtils().makeStringByArgs(arguments, " ")
                        .replace("send ", "")
                        .replace(date + " ", "")
                        .replace(time + " ", "");

                LocalDateTime localDateTime = LocalDateTime.parse(date + " " + time, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
                long sendTime = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

                String info = this.unicacityAddon.api().sendBroadcastSendRequest(message, String.valueOf(sendTime)).getInfo();
                p.sendAPIMessage(info, true);
            } else {
                sendUsage();
            }
        }).start();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        Date now = new Date();
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "queue", "send")
                .addAtIndex(2, new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN).format(now))
                .addAtIndex(3, new SimpleDateFormat("HH:mm:ss", Locale.GERMAN).format(now))
                .build();
    }
}