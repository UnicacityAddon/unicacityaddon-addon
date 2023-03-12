package com.rettichlp.unicacityaddon.events.faction.state;

import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/events/NameFormatEventHandler.java">UCUtils by paulzhng</a>
 */
@UCEvent
public class WantedEventHandler {

    public static final Map<String, Wanted> WANTED_MAP = new HashMap<>();
    private static long wantedsShown;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onWantedsGiven(ClientChatReceivedEvent e) {
        ITextComponent message = e.getMessage();
        String unformattedMessage = message.getUnformattedText();

        Matcher wantedsGivenReasonMatcher = PatternHandler.WANTED_GIVEN_REASON_PATTERN.matcher(unformattedMessage);
        if (wantedsGivenReasonMatcher.find()) {
            String name = wantedsGivenReasonMatcher.group(1);
            String reason = wantedsGivenReasonMatcher.group(2);

            WANTED_MAP.put(name, new Wanted(reason, 0));
            return;
        }

        Matcher wantedsGivenPointsMatcher = PatternHandler.WANTED_GIVEN_POINTS_PATTERN.matcher(unformattedMessage);
        if (wantedsGivenPointsMatcher.find()) {
            String name = wantedsGivenPointsMatcher.group(1);
            int wantedPoints = Integer.parseInt(wantedsGivenPointsMatcher.group(2));

            Wanted wanted = WANTED_MAP.get(name);
            if (wanted == null)
                return;

            wanted.setAmount(wantedPoints);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onWantedsDeleted(ClientChatReceivedEvent e) {
        ITextComponent message = e.getMessage();
        String unformattedMessage = message.getUnformattedText();

        Matcher matcher = PatternHandler.WANTED_DELETED_PATTERN.matcher(unformattedMessage);
        if (!matcher.find())
            return;

        String name = null;
        for (int i = 1; i < matcher.groupCount() + 1; i++) {
            String tempName = matcher.group(i);
            if (tempName == null)
                continue;

            name = tempName;
            break;
        }

        WANTED_MAP.remove(name);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onWantedsShown(ClientChatReceivedEvent e) {
        ITextComponent message = e.getMessage();
        String unformattedMessage = message.getUnformattedText();

        long currentTime = System.currentTimeMillis();

        if (unformattedMessage.equals("Online Spieler mit WantedPunkten:")) {
            WANTED_MAP.clear();
            wantedsShown = currentTime;
            return;
        }

        Matcher matcher = PatternHandler.WANTED_LIST_PATTERN.matcher(unformattedMessage);
        if (matcher.find()) {
            String playerName = matcher.group(1);
            String wpReason = matcher.group(3);
            int wpAmount = Integer.parseInt(matcher.group(2));
            boolean isAfk = matcher.group(4).contains("AFK");

            ColorCode colorCode = getWpColorCode(wpAmount);

            e.setMessage(Message.getBuilder().space().space()
                    .of("»").color(ColorCode.DARK_GRAY).advance().space()
                    .of(playerName).color(colorCode).advance().space()
                    .of("|").color(ColorCode.DARK_GRAY).advance().space()
                    .of(wpReason).color(colorCode).advance().space()
                    .of("(").color(ColorCode.GRAY).advance()
                    .of(String.valueOf(wpAmount)).color(ColorCode.BLUE).advance()
                    .of(")").color(ColorCode.GRAY).advance().space()
                    .of(isAfk ? "|" : "").color(ColorCode.DARK_GRAY).advance().space()
                    .of(isAfk ? "AFK" : "").color(ColorCode.GRAY).advance()
                    .createComponent());

            if (currentTime - wantedsShown < 1000L) {
                String name = matcher.group(1);
                int wantedPoints = Integer.parseInt(matcher.group(2));
                String reason = matcher.group(3);
                WANTED_MAP.put(name, new Wanted(reason, wantedPoints));
            }
        }
    }

    public static class Wanted {

        private String reason;
        private int amount;

        public Wanted(String reason, int amount) {
            this.reason = reason;
            this.amount = amount;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }
    }

    private ColorCode getWpColorCode(int wpAmount) {
        ColorCode colorCode;
        if (wpAmount > 60) {
            colorCode = ColorCode.DARK_RED;
        } else if (wpAmount > 50) {
            colorCode = ColorCode.RED;
        } else if (wpAmount > 25) {
            colorCode = ColorCode.GOLD;
        } else if (wpAmount > 15) {
            colorCode = ColorCode.YELLOW;
        } else if (wpAmount > 1) {
            colorCode = ColorCode.GREEN;
        } else {
            colorCode = ColorCode.DARK_GREEN;
        }
        return colorCode;
    }
}