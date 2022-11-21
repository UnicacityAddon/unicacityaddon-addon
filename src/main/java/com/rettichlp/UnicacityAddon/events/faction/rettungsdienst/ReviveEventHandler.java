package com.rettichlp.UnicacityAddon.events.faction.rettungsdienst;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.api.enums.StatisticType;
import com.rettichlp.UnicacityAddon.base.api.request.APIRequest;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.events.MobileEventHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.concurrent.TimeUnit;

/**
 * @author RettichLP
 */
@UCEvent
public class ReviveEventHandler {

    private static long time = 0;

    @SubscribeEvent
    public void onReviveStart(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        if (PatternHandler.REVIVE_BY_MEDIC_FINISH_PATTERN.matcher(msg).find()) {
            if (MobileEventHandler.hasCommunications)
                AbstractionLayer.getPlayer().sendChatMessage("/togglephone");
            return;
        }

        if (PatternHandler.REVIVE_START_PATTERN.matcher(msg).find() && UnicacityAddon.isUnicacity())
            time = System.currentTimeMillis();
    }

    public static void handleRevive() {
        if (System.currentTimeMillis() - time < TimeUnit.SECONDS.toMillis(10))
            APIRequest.sendStatisticAddRequest(StatisticType.REVIVE);
    }
}