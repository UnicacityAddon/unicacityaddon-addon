package com.rettichlp.unicacityaddon.events.faction.badfaction;

import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author Dimiikou
 */
@UCEvent
public class GaggedEventHandler {

    private static boolean gagged = false;

    @SubscribeEvent
    public void onClientChat(ClientChatEvent e) {
        if (gagged && !e.getMessage().startsWith("/"))
            e.setMessage("/w " + e.getMessage());
    }

    public static boolean isGagged() {
        return gagged;
    }

    public static void toggleGagged() {
        gagged = !gagged;
    }
}
