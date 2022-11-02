package com.rettichlp.UnicacityAddon.events.job;

import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author RettichLP
 */
@UCEvent
public class DropTabakEventHandler {

    public static boolean isTabakJob = false;

    @SubscribeEvent
    public void onClientChatReceive(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        if (PatternHandler.TABAK_DROP_PATTERN.matcher(msg).find())
            isTabakJob = true;

        if (PatternHandler.TABAK_FINISH_PATTERN.matcher(msg).find())
            isTabakJob = false;
    }
}