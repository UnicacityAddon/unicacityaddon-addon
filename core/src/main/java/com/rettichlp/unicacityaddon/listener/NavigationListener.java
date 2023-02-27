package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.listener.job.FishermanListener;
import com.rettichlp.unicacityaddon.listener.job.JobListener;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class NavigationListener {

    public static long routeMessageClearExecuteTime = -1;

    private final UnicacityAddon unicacityAddon;

    public NavigationListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        AddonPlayer p = UnicacityAddon.PLAYER;
        String msg = e.chatMessage().getPlainText();

        Matcher routeMatcher = PatternHandler.ROUTE_PATTERNS.matcher(msg);
        if (routeMatcher.find() && System.currentTimeMillis() - routeMessageClearExecuteTime < 1000L) {
            e.setCancelled(true);
            return;
        }

        if (msg.equals("Du hast dein Ziel erreicht.")) {
            if (FishermanListener.dropFish) {
                p.sendServerMessage("/dropfish");
                FishermanListener.dropFish = false;
            }

            if (JobListener.isTabakJob) {
                p.sendServerMessage("/droptabak");
            }
        }
    }
}
