package com.rettichlp.unicacityaddon.events;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.config.ConfigElements;
import com.rettichlp.unicacityaddon.base.enums.api.StatisticType;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.events.faction.rettungsdienst.ReviveEventHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();
        UPlayer p = AbstractionLayer.getPlayer();

        if (AccountEventHandler.isAfk)
            return;

        Matcher karmaChangedMatcher = PatternHandler.KARMA_CHANGED_PATTERN.matcher(msg);
        if (karmaChangedMatcher.find()) {
            ReviveEventHandler.handleRevive();

            // WORKAROUND START (for medics because ✨UCMDMOD✨) TODO: Remove later
            if (!p.getFaction().equals(Faction.RETTUNGSDIENST) && !ReviveEventHandler.isDead) {
                p.sendChatMessage("/karma");
                karmaCheck = true;
                e.setCanceled(true);
            }
            // WORKAROUND END

            karma = Integer.parseInt(karmaChangedMatcher.group(1));
            if (karma < 0 && karma > -9) {
                APIRequest.sendStatisticAddRequest(StatisticType.KILL);
            }

            return;
        }

        Matcher karmaMatcher = PatternHandler.KARMA_PATTERN.matcher(msg);
        if (karmaMatcher.find() && karmaCheck) {
            if (karma < 0 && ConfigElements.getEstimatedDespawnTime()) {
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