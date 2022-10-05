package com.rettichlp.UnicacityAddon.events.faction.badfaction;

import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.modules.FBIHackModule;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class FBIHackEventHandler {

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        Matcher fbiHackStartedMatcher = PatternHandler.FBI_HACK_STARTED_PATTERN.matcher(e.getMessage().getUnformattedText());

        if (!fbiHackStartedMatcher.find()) return false;

        FBIHackModule.startCountdown(Integer.parseInt(fbiHackStartedMatcher.group(1)));
        return false;
    }
}
