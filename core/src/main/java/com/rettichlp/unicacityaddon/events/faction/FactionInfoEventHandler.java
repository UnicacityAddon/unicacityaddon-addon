package com.rettichlp.unicacityaddon.events.faction;

import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/faction/CheckActiveMembersCommand.java">UCUtils by paulzhng</a>
 */
@UCEvent
public class FactionInfoEventHandler {

    private static final Timer TIMER = new Timer();
    private static final Pattern FACTION_MEMBERS_PATTERN = Pattern.compile("^ {2}=== Fraktionsmitglieder .+ ===$");
    private static final Map<Boolean, Integer> MEMBER_MAP = new HashMap<>();
    private static long memberlistShown;
    public static CompletableFuture<Map<Boolean, Integer>> future;

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        if (future == null)
            return;

        String msg = e.chatMessage().getPlainText();
        long currentTime = System.currentTimeMillis();

        if (FACTION_MEMBERS_PATTERN.matcher(msg).find()) {
            e.setCancelled(true);
            memberlistShown = currentTime;
            MEMBER_MAP.put(false, 0);
            MEMBER_MAP.put(true, 0);

            TIMER.schedule(new TimerTask() {
                @Override
                public void run() {
                    future.complete(MEMBER_MAP);
                }
            }, 300L);
            return;
        }

        if (currentTime - memberlistShown > 200L || !msg.startsWith(" » "))
            return;

        boolean inactive = !msg.endsWith("AFK") && !msg.endsWith("Nicht im Dienst");
        MEMBER_MAP.merge(inactive, 1, Integer::sum);

        e.setCancelled(true);
    }
}