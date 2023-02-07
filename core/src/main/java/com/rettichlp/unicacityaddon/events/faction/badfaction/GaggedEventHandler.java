package com.rettichlp.unicacityaddon.events.faction.badfaction;

import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import lombok.NoArgsConstructor;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;

/**
 * @author Dimiikou
 */
@UCEvent
@NoArgsConstructor
public class GaggedEventHandler {

    private static boolean gagged = false;

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
