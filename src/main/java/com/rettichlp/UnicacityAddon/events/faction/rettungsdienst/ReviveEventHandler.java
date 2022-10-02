package com.rettichlp.UnicacityAddon.events.faction.rettungsdienst;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.api.request.APIRequest;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.concurrent.TimeUnit;

/**
 * @author RettichLP
 */
@UCEvent
public class ReviveEventHandler {

    private long time = 0;

    @SubscribeEvent
    public void onReviveStart(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        if (PatternHandler.REVIVE_START_PATTERN.matcher(msg).find() && UnicacityAddon.isUnicacity()) {
            time = System.currentTimeMillis();
            return;
        }

        if (PatternHandler.KARMA_CHANGED_PATTERN.matcher(msg).find()) {
            if (System.currentTimeMillis() - time < TimeUnit.SECONDS.toMillis(10))
                APIRequest.sendStatisticAddReviveRequest();
        }
    }
}