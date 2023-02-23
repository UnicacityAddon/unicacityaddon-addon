package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.base.utils.NavigationUtils;
import com.rettichlp.unicacityaddon.listener.job.FishermanEventHandler;
import com.rettichlp.unicacityaddon.listener.job.JobEventHandler;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class NavigationEventHandler {

    private final UnicacityAddon unicacityAddon;

    public NavigationEventHandler(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        AddonPlayer p = UnicacityAddon.PLAYER;
        String msg = e.chatMessage().getPlainText();

        Matcher routeMatcher = PatternHandler.ROUTE_PATTERNS.matcher(msg);
        if (routeMatcher.find() && System.currentTimeMillis() - NavigationUtils.routeMessageClearExecuteTime < 500L) {
            e.setCancelled(true);
            return;
        }

        if (msg.equals("Du hast dein Ziel erreicht.")) {
            if (FishermanEventHandler.dropFish) {
                p.sendServerMessage("/dropfish");
                FishermanEventHandler.dropFish = false;
            }

            if (JobEventHandler.isTabakJob) {
                p.sendServerMessage("/droptabak");
            }
        }
    }
}
