package com.rettichlp.unicacityaddon.listener.faction.state;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/events/NameFormatEventHandler.java">UCUtils by paulzhng</a>
 */
@UCEvent
public class WantedListener {

    public static final Map<String, Wanted> WANTED_MAP = new HashMap<>();
    private static long wantedsShown;

    private final UnicacityAddon unicacityAddon;

    public WantedListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();

        Matcher wantedsGivenReasonMatcher = PatternHandler.WANTED_GIVEN_REASON_PATTERN.matcher(msg);
        if (wantedsGivenReasonMatcher.find()) {
            String name = wantedsGivenReasonMatcher.group(1);
            String reason = wantedsGivenReasonMatcher.group(2);

            WANTED_MAP.put(name, new Wanted(reason, 0));
            return;
        }

        Matcher wantedsGivenPointsMatcher = PatternHandler.WANTED_GIVEN_POINTS_PATTERN.matcher(msg);
        if (wantedsGivenPointsMatcher.find()) {
            String name = wantedsGivenPointsMatcher.group(1);
            int wantedPoints = Integer.parseInt(wantedsGivenPointsMatcher.group(2));

            Wanted wanted = WANTED_MAP.get(name);
            if (wanted == null)
                return;

            wanted.setAmount(wantedPoints);
            return;
        }

        Matcher wantedDeletedMatcher = PatternHandler.WANTED_DELETED_PATTERN.matcher(msg);
        if (wantedDeletedMatcher.find()) {
            String name = null;
            for (int i = 1; i < wantedDeletedMatcher.groupCount() + 1; i++) {
                String tempName = wantedDeletedMatcher.group(i);
                if (tempName == null) continue;

                name = tempName;
                break;
            }

            WANTED_MAP.remove(name);
            return;
        }

        Matcher wantedListEntryMatcher = PatternHandler.WANTED_LIST_PATTERN.matcher(msg);
        if (wantedListEntryMatcher.find() && System.currentTimeMillis() - wantedsShown < 1000L) {
            String playerName = wantedListEntryMatcher.group(1);
            String wpReason = wantedListEntryMatcher.group(3);
            int wpAmount = Integer.parseInt(wantedListEntryMatcher.group(2));
            boolean isAfk = wantedListEntryMatcher.group(4).contains("AFK");

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

            WANTED_MAP.put(playerName, new Wanted(wpReason, wpAmount));
            return;
        }

        if (msg.equals("Online Spieler mit WantedPunkten:")) {
            WANTED_MAP.clear();
            wantedsShown = System.currentTimeMillis();
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