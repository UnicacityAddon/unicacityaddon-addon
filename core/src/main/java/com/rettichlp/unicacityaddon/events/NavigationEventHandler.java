package com.rettichlp.unicacityaddon.events;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.base.utils.NavigationUtils;
import com.rettichlp.unicacityaddon.events.job.FishermanEventHandler;
import com.rettichlp.unicacityaddon.events.job.JobEventHandler;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class NavigationEventHandler {

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        String msg = e.chatMessage().getPlainText();

        Matcher routeMatcher = PatternHandler.ROUTE_PATTERNS.matcher(msg);
        if (routeMatcher.find() && System.currentTimeMillis() - NavigationUtils.routeMessageClearExecuteTime < 500L) {
            e.setCancelled(true);
            return;
        }

        if (msg.equals("Du hast dein Ziel erreicht.")) {
            if (FishermanEventHandler.dropFish) {
                p.sendChatMessage("/dropfish");
                FishermanEventHandler.dropFish = false;
            }

            if (JobEventHandler.isTabakJob) {
                p.sendChatMessage("/droptabak");
            }
        }
    }
}
