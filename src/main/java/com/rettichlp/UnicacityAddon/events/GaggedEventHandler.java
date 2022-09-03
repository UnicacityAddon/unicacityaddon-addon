package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author Dimiikou
 */
@UCEvent
public class GaggedEventHandler {

    private static boolean isGagged = false;

    @SubscribeEvent
    public boolean onClientChat(ClientChatEvent e) {
        if (!isGagged) return false;
        if (e.getMessage().startsWith("/")) return false;

        e.setMessage("/w " + e.getMessage());
        return false;
    }

    public static boolean getGagged() {
        return isGagged;
    }

    public static void changeGaggedState() {
        if (isGagged) {
            isGagged = false;
            return;
        }

        isGagged = true;
    }
}
