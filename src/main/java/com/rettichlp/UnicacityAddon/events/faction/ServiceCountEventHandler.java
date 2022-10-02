package com.rettichlp.UnicacityAddon.events.faction;

import com.rettichlp.UnicacityAddon.base.api.request.APIRequest;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.commands.faction.ServiceCountCommand;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author Dimiikou
 */
@UCEvent
public class ServiceCountEventHandler {

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        if (PatternHandler.SERVICE_DONE_PATTERN.matcher(e.getMessage().getUnformattedText()).find()) {
            ServiceCountCommand.addService();
            APIRequest.sendStatisticAddServiceRequest();
        }

        return false;
    }
}
