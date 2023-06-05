package com.rettichlp.unicacityaddon.listener.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.annotation.UCEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;

/**
 * @author Dimiikou
 * @author RettichLP
 */
@UCEvent
public class GaggedListener {

    private final UnicacityAddon unicacityAddon;

    public GaggedListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatMessageSend(ChatMessageSendEvent e) {
        if (this.unicacityAddon.player().isGagged() && !e.getMessage().startsWith("/"))
            e.changeMessage("/w " + e.getMessage());
    }
}
