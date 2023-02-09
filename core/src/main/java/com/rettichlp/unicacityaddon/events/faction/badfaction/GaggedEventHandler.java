package com.rettichlp.unicacityaddon.events.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;

/**
 * @author Dimiikou
 */
@UCEvent
public class GaggedEventHandler {

    private static boolean gagged = false;

    private final UnicacityAddon unicacityAddon;

    public GaggedEventHandler(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatMessageSend(ChatMessageSendEvent e) {
        if (gagged && !e.getMessage().startsWith("/"))
            e.changeMessage("/w " + e.getMessage());
    }

    public static boolean isGagged() {
        return gagged;
    }

    public static void toggleGagged() {
        gagged = !gagged;
    }
}
