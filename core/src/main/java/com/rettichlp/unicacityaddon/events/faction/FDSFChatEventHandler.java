package com.rettichlp.unicacityaddon.events.faction;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.events.MobileEventHandler;
import lombok.NoArgsConstructor;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;

/**
 * @author RettichLP
 */
@UCEvent
@NoArgsConstructor
public class FDSFChatEventHandler {

    @Subscribe
    public void onChatMessageSend(ChatMessageSendEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        String msg = e.getMessage();

        if (msg.startsWith("/f ") && !MobileEventHandler.hasCommunications) {
            p.sendErrorMessage("Du hast keine Kommunikationsmittel!");
            p.sendInfoMessage("Wenn du die Nachricht trotzdem senden möchtest, nutze /fforce [Nachricht]. Die Nachricht ist in der Zwischenablage.");
            p.copyToClipboard(msg.replace("/f ", ""));
            e.setCancelled(true);
        } else if (msg.startsWith("/d ") && !MobileEventHandler.hasCommunications) {
            p.sendErrorMessage("Du hast keine Kommunikationsmittel!");
            p.sendInfoMessage("Wenn du die Nachricht trotzdem senden möchtest, nutze /dforce [Nachricht]. Die Nachricht ist in der Zwischenablage.");
            p.copyToClipboard(msg.replace("/d ", ""));
            e.setCancelled(true);
        } else if (msg.startsWith("/sf ") && !MobileEventHandler.hasCommunications) {
            p.sendErrorMessage("Du hast keine Kommunikationsmittel!");
            p.sendInfoMessage("Wenn du die Nachricht trotzdem senden möchtest, nutze /sfforce [Nachricht]. Die Nachricht ist in der Zwischenablage.");
            p.copyToClipboard(msg.replace("/sf ", ""));
            e.setCancelled(true);
        }
    }
}