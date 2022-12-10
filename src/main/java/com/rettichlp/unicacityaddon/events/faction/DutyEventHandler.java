package com.rettichlp.unicacityaddon.events.faction;

import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author Dimiikou
 */
@UCEvent
public class DutyEventHandler {

    public static boolean inDuty = false;

    @SubscribeEvent
    public void onClientChatReceive(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        if (PatternHandler.DUTY_JOIN_PATTERN.matcher(msg).find()) inDuty = true;
        if (PatternHandler.DUTY_LEAVE_PATTERN.matcher(msg).find()) inDuty = false;
    }
    
}
