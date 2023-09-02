package com.rettichlp.unicacityaddon.listener.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;

/**
 * @author Dimiikou
 * @author RettichLP
 */
@UCEvent
public class PronunciationListener {

    private final UnicacityAddon unicacityAddon;

    public PronunciationListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatMessageSend(ChatMessageSendEvent e) {
        if (!e.getMessage().startsWith("/")) {
            AddonPlayer p = this.unicacityAddon.player();
            if (p.isWhispering())
                e.changeMessage("/w " + e.getMessage());
            else if (p.isShouting())
                e.changeMessage("/s " + e.getMessage());
        }
    }
}
