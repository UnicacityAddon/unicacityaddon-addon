package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import java.util.regex.Matcher;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MobileEventHandler {

    public static int lastCheckedNumber = 0;

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        Matcher numberMatcher = PatternHandler.NUMBER_PATTERN.matcher(msg);
        if (!numberMatcher.find()) return;

        lastCheckedNumber = Integer.parseInt(numberMatcher.group(1));

        e.setCanceled(true);
    }
}
