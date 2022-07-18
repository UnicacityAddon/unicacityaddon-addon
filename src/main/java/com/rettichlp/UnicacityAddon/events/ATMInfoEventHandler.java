package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

public class ATMInfoEventHandler {

    @SubscribeEvent
    public boolean onClientChatReceive(ClientChatReceivedEvent e) {
        if (!ConfigElements.getEventATMInfo()) return false;

        Matcher kontoauszugMatcher = PatternHandler.KONTOAUSZUG_PATTERN.matcher(e.getMessage().getUnformattedText());
        if (kontoauszugMatcher.find()) AbstractionLayer.getPlayer().sendChatMessage("/atminfo");
        return false;
    }
}