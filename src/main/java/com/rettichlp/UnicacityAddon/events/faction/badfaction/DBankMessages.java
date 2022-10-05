package com.rettichlp.UnicacityAddon.events.faction.badfaction;

import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.modules.FBIHackModule;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class DBankMessages {

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        Matcher dropMatcher = PatternHandler.DBANK_DROP_PATTERN.matcher(msg);
        if (dropMatcher.find())
            e.setMessage(Message.getBuilder().of("").advance().createComponent()); // TODO: Nachricht erstellen

        Matcher getMatcher = PatternHandler.DBANK_GET_PATTERN.matcher(msg);
        if (getMatcher.find())
            e.setMessage(Message.getBuilder().of("").advance().createComponent()); // TODO: Nachricht erstellen

        return false;
    }
}
