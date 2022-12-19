package com.rettichlp.unicacityaddon.events;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.enums.api.StatisticType;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.events.faction.rettungsdienst.ReviveEventHandler;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class KarmaMessageEventHandler {

    private boolean karmaCheck = false;
    private int karma;

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();
        UPlayer p = AbstractionLayer.getPlayer();

        if (AccountEventHandler.isAfk)
            return;

        Matcher karmaChangedMatcher = PatternHandler.KARMA_CHANGED_PATTERN.matcher(msg);
        if (karmaChangedMatcher.find()) {
            ReviveEventHandler.handleRevive();

            // WORKAROUND START (for medics because ✨UCMDMOD✨) TODO: Remove later
            if (!p.getFaction()
                    .equals(Faction.RETTUNGSDIENST)) {
                p.sendChatMessage("/karma");
                karmaCheck = true;
                e.setCancelled(true);
            }
            // WORKAROUND END
            karma = Integer.parseInt(karmaChangedMatcher.group(1));

            if (karma > 0 || karma < -7)
                return; // Wenn das Karma unter 0 ist, und nicht tiefer als 7 geht dann gibt es einen Kill

            APIRequest.sendStatisticAddRequest(StatisticType.KILL);
            return;
        }

        Matcher karmaMatcher = PatternHandler.KARMA_PATTERN.matcher(msg);
        if (!karmaCheck || !karmaMatcher.find())
            return;

        if (karma < 0 && UnicacityAddon.configuration.despawnTime().get()) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, 5);
            Date date = cal.getTime();

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            e.setMessage(Message.getBuilder().of("[").color(ColorCode.DARK_GRAY).advance()
                    .of("Karma").color(ColorCode.BLUE).advance()
                    .of("] ").color(ColorCode.DARK_GRAY).advance()
                    .of("" + karma).color(ColorCode.AQUA).advance().space()
                    .of("Karma ").color(ColorCode.AQUA).advance()
                    .of("(").color(ColorCode.DARK_GRAY).advance()
                    .of(karmaMatcher.group(1)).color(ColorCode.AQUA).advance()
                    .of("/").color(ColorCode.DARK_GRAY).advance()
                    .of("100").color(ColorCode.AQUA).advance()
                    .of(")").color(ColorCode.DARK_GRAY).advance().space()
                    .of("(").color(ColorCode.DARK_GRAY).advance()
                    .of(timeFormat.format(date)).color(ColorCode.AQUA).advance()
                    .of(")").color(ColorCode.DARK_GRAY).advance().createComponent());
            karmaCheck = !karmaCheck;
        } else {
            e.setMessage(Message.getBuilder().of("[").color(ColorCode.DARK_GRAY).advance()
                    .of("Karma").color(ColorCode.BLUE).advance()
                    .of("] ").color(ColorCode.DARK_GRAY).advance()
                    .of("+" + karma).color(ColorCode.AQUA).advance().space()
                    .of("Karma ").color(ColorCode.AQUA).advance()
                    .of("(").color(ColorCode.DARK_GRAY).advance()
                    .of(karmaMatcher.group(1)).color(ColorCode.AQUA).advance()
                    .of("/").color(ColorCode.DARK_GRAY).advance()
                    .of("100").color(ColorCode.AQUA).advance()
                    .of(")").color(ColorCode.DARK_GRAY).advance().createComponent());
            karmaCheck = !karmaCheck;
        }
    }
}