package com.rettichlp.UnicacityAddon.events.faction.badfaction;

import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author Dimiikou
 */
@UCEvent
public class GaggedEventHandler {

    private static boolean gagged = false;

    @SubscribeEvent
    public boolean onClientChat(ClientChatEvent e) {
        if (!gagged || e.getMessage().startsWith("/")) return false;

        e.setMessage("/w " + e.getMessage());
        return false;
    }

    public static boolean isGagged() {
        return gagged;
    }

    public static void toggleGagged() {
        gagged = !gagged;
    }
}
