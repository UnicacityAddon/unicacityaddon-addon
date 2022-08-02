package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
public class ATMInfoEventHandler {

    @SubscribeEvent public boolean onClientChatReceive(ClientChatReceivedEvent e) {
        if (!ConfigElements.getEventATM()) return false;

        UPlayer p = AbstractionLayer.getPlayer();

        Matcher kontoauszugMatcher = PatternHandler.KONTOAUSZUG_PATTERN.matcher(e.getMessage().getUnformattedText());
        if (kontoauszugMatcher.find()) {

            if (ConfigElements.getEventATMFBank()) {
                p.sendChatMessage("/fbank");
            }

            if (ConfigElements.getEventATMGRKasse()) {
                p.sendChatMessage("/grkasse info");
            }

            if (ConfigElements.getEventATMInfo()) {
                p.sendChatMessage("/atminfo");
            }
        }
        return false;
    }
}