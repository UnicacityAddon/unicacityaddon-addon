package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.base.location.NavigationUtils;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

public class NavigationEventHandler {

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        if (System.currentTimeMillis() - NavigationUtils.routeMessageClearExecuteTime >= 500L) return false;

        Matcher routeMatcher = PatternHandler.ROUTE_PATTERNS.matcher(e.getMessage().getUnformattedText());
        if (routeMatcher.find()) {
            e.setCanceled(true);
        }
        return false;
    }
}
