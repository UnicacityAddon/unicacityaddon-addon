package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author Fuzzlemann
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/events/CheckKFZEventHandler.java">UCUtils by paulzhng</a>
 */
public class CheckKFZEventHandler {

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        Matcher matcher = PatternHandler.CHECK_KFZ_PATTERN.matcher(msg);
        if (!matcher.find()) return false;

        String name = matcher.group(1);
        if (name == null) name = matcher.group(2);

        AbstractionLayer.getPlayer().sendChatMessage("/memberinfo " + name);
        return false;
    }
}