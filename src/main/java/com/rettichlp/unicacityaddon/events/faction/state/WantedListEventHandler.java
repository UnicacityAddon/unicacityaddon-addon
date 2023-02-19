package com.rettichlp.unicacityaddon.events.faction.state;

import com.rettichlp.unicacityaddon.base.config.ConfigElements;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author Gelegenheitscode
 */
@UCEvent
public class WantedListEventHandler {
    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent e)   {
        String msg = e.getMessage().getUnformattedText();

        if (!ConfigElements.getBetterWantedList()) {
            return;
        }

        Matcher wantedListMatcher = PatternHandler.WANTED_LIST_PATTERN.matcher(msg);
        if (wantedListMatcher.find()) {

            String playerName = wantedListMatcher.group(1);
            String wpReason = wantedListMatcher.group(3);
            int wpAmount = Integer.parseInt(wantedListMatcher.group(2));
            boolean isAfk = wantedListMatcher.group(4).contains("AFK");

            ColorCode colorCode;

            if (wpAmount == 1) {
                colorCode = ColorCode.DARK_GREEN;
            } else if (wpAmount <= 15 && wpAmount >= 2) {
                colorCode = ColorCode.GREEN;
            } else if (wpAmount <= 25 && wpAmount >= 16) {
                colorCode = ColorCode.YELLOW;
            } else if (wpAmount <= 49 && wpAmount > 26) {
                colorCode = ColorCode.GOLD;
            } else if (wpAmount <= 59 && wpAmount >= 50) {
                colorCode = ColorCode.RED;
            } else if (wpAmount <= 69 && wpAmount >= 60) {
                colorCode = ColorCode.DARK_RED;
            } else {
                colorCode = ColorCode.RED;
            }

            e.setMessage(Message.getBuilder()
                    .of("▶").color(ColorCode.DARK_GRAY).advance().space()
                    .of(playerName).color(colorCode).advance().space()
                    .of("|").color(ColorCode.DARK_GRAY).advance().space()
                    .of(wpReason).color(colorCode).advance().space()
                    .of("(").color(ColorCode.GRAY).advance()
                    .of(String.valueOf(wpAmount)).color(ColorCode.BLUE).advance()
                    .of(")").color(ColorCode.GRAY).advance().space()
                    .of(isAfk ? "|" : "").color(ColorCode.DARK_GRAY).advance().space()
                    .of(isAfk ? "AFK" : "").color(ColorCode.GRAY).advance()
                    .createComponent());
        }
    }
}