package com.rettichlp.unicacityaddon.events.faction.badfaction;

import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.modules.FBIHackModule;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class FBIHackEventHandler {

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        Matcher fbiHackStartedMatcher = PatternHandler.FBI_HACK_STARTED_PATTERN.matcher(e.getMessage().getUnformattedText());

        if (fbiHackStartedMatcher.find())
            FBIHackModule.startCountdown(Integer.parseInt(fbiHackStartedMatcher.group(1)));
    }
}