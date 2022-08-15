package com.rettichlp.UnicacityAddon.events.job;

import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.commands.job.ADropMoneyCommand;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author Dimiikou
 */
@UCEvent
public class ADropMoneyEventHandler {

    @SubscribeEvent
    public boolean onClientChatReceive(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        if (!ADropMoneyCommand.execute) return false;

        if (msg.equals("Du hast zu wenig Geld!")) {
            ADropMoneyCommand.execute = false;
            return false;
        }

        if (msg.equalsIgnoreCase("=== Kontoauszug ===")
                || msg.equalsIgnoreCase("=================")
                || msg.equals("")
                || PatternHandler.PREVIOUS_BANK_VALUE_PATTERN.matcher(msg).find()
                || PatternHandler.CASH_FROM_BANK_PATTERN.matcher(msg).find()
                || PatternHandler.CASH_TO_BANK_PATTERN.matcher(msg).find()
                || PatternHandler.BANK_NEW_BALANCE_PATTERN.matcher(msg).find())
            e.setCanceled(true);

        return false;
    }
}
