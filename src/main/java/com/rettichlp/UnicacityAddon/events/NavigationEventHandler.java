package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.location.NavigationUtils;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.events.job.FishermanEventHandler;
import com.rettichlp.UnicacityAddon.events.job.JobEventHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class NavigationEventHandler {

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        String msg = e.getMessage().getUnformattedText();

        Matcher routeMatcher = PatternHandler.ROUTE_PATTERNS.matcher(msg);
        if (routeMatcher.find() && System.currentTimeMillis() - NavigationUtils.routeMessageClearExecuteTime < 500L) {
            e.setCanceled(true);
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
