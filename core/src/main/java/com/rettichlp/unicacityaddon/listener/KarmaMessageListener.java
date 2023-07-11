package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.api.StatisticType;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.listener.faction.rettungsdienst.ReviveListener;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class KarmaMessageListener {

    private boolean karmaCheck = false;
    private int karma;

    private final UnicacityAddon unicacityAddon;

    public KarmaMessageListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();

        if (AccountListener.isAfk)
            return;

        Matcher karmaChangedMatcher = PatternHandler.KARMA_CHANGED_PATTERN.matcher(msg);
        if (karmaChangedMatcher.find()) {
            // handle revive
            if (System.currentTimeMillis() - ReviveListener.medicReviveStartTime < TimeUnit.SECONDS.toMillis(10)) {
                this.unicacityAddon.api().sendStatisticAddRequest(StatisticType.REVIVE);
            }

            // show karma mesage
            karmaCheck = true;
            this.unicacityAddon.player().sendServerMessage("/karma");
            e.setCancelled(true);

            karma = Integer.parseInt(karmaChangedMatcher.group(1));
            if (karma < 0 && karma > -9) {
                this.unicacityAddon.api().sendStatisticAddRequest(StatisticType.KILL);
            }

            return;
        }

        Matcher karmaMatcher = PatternHandler.KARMA_PATTERN.matcher(msg);
        if (karmaMatcher.find() && karmaCheck) {
            if (karma < 0 && this.unicacityAddon.configuration().despawnTime().get()) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MINUTE, 5);
                Date date = cal.getTime();

                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

                e.setMessage(Message.getBuilder().of("[").color(ColorCode.DARK_GRAY).advance()
                        .of("Karma").color(ColorCode.BLUE).advance()
                        .of("]").color(ColorCode.DARK_GRAY).advance().space()
                        .of(String.valueOf(karma)).color(ColorCode.AQUA).advance().space()
                        .of("Karma").color(ColorCode.AQUA).advance().space()
                        .of("(").color(ColorCode.DARK_GRAY).advance()
                        .of(karmaMatcher.group(1)).color(ColorCode.AQUA).advance()
                        .of("/").color(ColorCode.DARK_GRAY).advance()
                        .of("100").color(ColorCode.AQUA).advance()
                        .of(")").color(ColorCode.DARK_GRAY).advance().space()
                        .of("(").color(ColorCode.DARK_GRAY).advance()
                        .of(timeFormat.format(date)).color(ColorCode.AQUA).advance()
                        .of(")").color(ColorCode.DARK_GRAY).advance().createComponent());
            } else {
                e.setMessage(Message.getBuilder().of("[").color(ColorCode.DARK_GRAY).advance()
                        .of("Karma").color(ColorCode.BLUE).advance()
                        .of("]").color(ColorCode.DARK_GRAY).advance().space()
                        .of("+" + karma).color(ColorCode.AQUA).advance().space()
                        .of("Karma").color(ColorCode.AQUA).advance().space()
                        .of("(").color(ColorCode.DARK_GRAY).advance()
                        .of(karmaMatcher.group(1)).color(ColorCode.AQUA).advance()
                        .of("/").color(ColorCode.DARK_GRAY).advance()
                        .of("100").color(ColorCode.AQUA).advance()
                        .of(")").color(ColorCode.DARK_GRAY).advance().createComponent());
            }
            karmaCheck = false;
        }
    }
}