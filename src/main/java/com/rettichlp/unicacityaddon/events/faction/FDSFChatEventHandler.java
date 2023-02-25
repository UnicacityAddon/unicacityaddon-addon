package com.rettichlp.unicacityaddon.events.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.events.MobileEventHandler;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author RettichLP
 */
@UCEvent
public class FDSFChatEventHandler {

    @SubscribeEvent
    public void onFDSFChatSend(ClientChatEvent e) {
        String msg = e.getMessage();
        UPlayer p = AbstractionLayer.getPlayer();

        if (msg.startsWith("/f ") && !MobileEventHandler.hasCommunications && UnicacityAddon.isUnicacity()) {
            p.sendErrorMessage("Du hast keine Kommunikationsmittel!");
            p.sendInfoMessage("Wenn du die Nachricht trotzdem senden möchtest, nutze /fforce [Nachricht]. Die Nachricht ist in der Zwischenablage.");
            p.copyToClipboard(msg.replace("/f ", ""));
            e.setCanceled(true);
        } else if (msg.startsWith("/d ") && !MobileEventHandler.hasCommunications && UnicacityAddon.isUnicacity()) {
            p.sendErrorMessage("Du hast keine Kommunikationsmittel!");
            p.sendInfoMessage("Wenn du die Nachricht trotzdem senden möchtest, nutze /dforce [Nachricht]. Die Nachricht ist in der Zwischenablage.");
            p.copyToClipboard(msg.replace("/d ", ""));
            e.setCanceled(true);
        } else if (msg.startsWith("/sf ") && !MobileEventHandler.hasCommunications && UnicacityAddon.isUnicacity()) {
            p.sendErrorMessage("Du hast keine Kommunikationsmittel!");
            p.sendInfoMessage("Wenn du die Nachricht trotzdem senden möchtest, nutze /sfforce [Nachricht]. Die Nachricht ist in der Zwischenablage.");
            p.copyToClipboard(msg.replace("/sf ", ""));
            e.setCanceled(true);
        }
    }
}