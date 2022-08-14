package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author Dimiikou
 */
public class RespawnEventHandler {

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();
        if (!PatternHandler.REVIVE_BY_MEDIC_FINISH_PATTERN.matcher(msg).find()) return false;
        if (MobileEventHandler.hasCommunications) AbstractionLayer.getPlayer().sendChatMessage("/togglephone");
        return false;
    }
}
