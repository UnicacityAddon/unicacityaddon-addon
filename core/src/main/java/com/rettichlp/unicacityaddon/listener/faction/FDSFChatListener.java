package com.rettichlp.unicacityaddon.listener.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.listener.MobileListener;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;

/**
 * @author RettichLP
 */
@UCEvent
public class FDSFChatListener {

    public static boolean forceSendNextMessage = false;

    private final UnicacityAddon unicacityAddon;

    public FDSFChatListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatMessageSend(ChatMessageSendEvent e) {
        AddonPlayer p = this.unicacityAddon.player();
        String msg = e.getMessage();

        if (!MobileListener.hasCommunications && this.unicacityAddon.utilService().isUnicacity() && !forceSendNextMessage) {
            if (msg.startsWith("/f ")) {
                p.sendErrorMessage("Du hast keine Kommunikationsmittel!");
                p.sendInfoMessage("Wenn du die Nachricht trotzdem senden möchtest, nutze /fforce [Nachricht]. Die Nachricht ist in der Zwischenablage.");
                p.copyToClipboard(msg.replace("/f ", ""));
                e.setCancelled(true);
            } else if (msg.startsWith("/d ")) {
                p.sendErrorMessage("Du hast keine Kommunikationsmittel!");
                p.sendInfoMessage("Wenn du die Nachricht trotzdem senden möchtest, nutze /dforce [Nachricht]. Die Nachricht ist in der Zwischenablage.");
                p.copyToClipboard(msg.replace("/d ", ""));
                e.setCancelled(true);
            } else if (msg.startsWith("/sf ")) {
                p.sendErrorMessage("Du hast keine Kommunikationsmittel!");
                p.sendInfoMessage("Wenn du die Nachricht trotzdem senden möchtest, nutze /sfforce [Nachricht]. Die Nachricht ist in der Zwischenablage.");
                p.copyToClipboard(msg.replace("/sf ", ""));
                e.setCancelled(true);
            }
        }
    }
}