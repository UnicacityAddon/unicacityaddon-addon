package com.rettichlp.unicacityaddon.listener.faction.polizei;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
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
            String name = wantedListEntryMatcher.group(1);
            int wantedPoints = Integer.parseInt(wantedListEntryMatcher.group(2));
            String reason = wantedListEntryMatcher.group(3);

            WANTED_MAP.put(name, new Wanted(reason, wantedPoints));
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
}