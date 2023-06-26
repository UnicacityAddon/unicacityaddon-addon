package com.rettichlp.unicacityaddon.listener.faction.state;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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

            Map<String, Wanted> wantedPlayerMap = this.unicacityAddon.services().nametag().getWantedPlayerMap();
            wantedPlayerMap.put(name, new Wanted(reason, 0));
            this.unicacityAddon.services().nametag().setWantedPlayerMap(wantedPlayerMap);

            return;
        }

        Matcher wantedsGivenPointsMatcher = PatternHandler.WANTED_GIVEN_POINTS_PATTERN.matcher(msg);
        if (wantedsGivenPointsMatcher.find()) {
            String name = wantedsGivenPointsMatcher.group(1);
            int wantedPoints = Integer.parseInt(wantedsGivenPointsMatcher.group(2));

            Wanted wanted = this.unicacityAddon.services().nametag().getWantedPlayerMap().get(name);
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

            Map<String, Wanted> wantedPlayerMap = this.unicacityAddon.services().nametag().getWantedPlayerMap();
            wantedPlayerMap.remove(name);
            this.unicacityAddon.services().nametag().setWantedPlayerMap(wantedPlayerMap);

            return;
        }

        Matcher wantedListEntryMatcher = PatternHandler.WANTED_LIST_PATTERN.matcher(msg);
        if (wantedListEntryMatcher.find() && System.currentTimeMillis() - wantedsShown < 1000L) {
            String playerName = wantedListEntryMatcher.group(1);
            String wpReason = wantedListEntryMatcher.group(3);
            int wpAmount = Integer.parseInt(wantedListEntryMatcher.group(2));
            boolean isAfk = wantedListEntryMatcher.group(4).contains("AFK");

            ColorCode colorCode = this.unicacityAddon.services().nametag().getWpColor(wpAmount);

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

            Map<String, Wanted> wantedPlayerMap = this.unicacityAddon.services().nametag().getWantedPlayerMap();
            wantedPlayerMap.put(playerName, new Wanted(wpReason, wpAmount));
            this.unicacityAddon.services().nametag().setWantedPlayerMap(wantedPlayerMap);

            return;
        }

        if (msg.equals("Online Spieler mit WantedPunkten:")) {
            this.unicacityAddon.services().nametag().setWantedPlayerMap(new HashMap<>());
            wantedsShown = System.currentTimeMillis();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Wanted {

        private String reason;
        private int amount;
    }
}