package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.commands.ReichensteuerCommand;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class ReichensteuerEventHandler {

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {

        Matcher m = PatternHandler.ATM_INFO.matcher(e.getMessage().getUnformattedText());
        if (!m.find()) return false;

        ReichensteuerCommand.cashInATM = Integer.parseInt(m.group(2));
        return false;
    }
}
