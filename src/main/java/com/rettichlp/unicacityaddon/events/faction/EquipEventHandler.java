package com.rettichlp.unicacityaddon.events.faction;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.enums.faction.Equip;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class EquipEventHandler {

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        Matcher equipMatcher = PatternHandler.EQUIP_PATTERN.matcher(msg);
        Matcher trackerMatcher = PatternHandler.TRACKER_PATTERN.matcher(msg);
        if (equipMatcher.find() || trackerMatcher.find()) {
            Equip equip = Arrays.stream(Equip.values())
                    .filter(equip1 -> msg.toLowerCase().contains(equip1.getMessageName().toLowerCase()))
                    .findFirst()
                    .orElse(null);

            if (equip != null) {
                FileManager.DATA.addEquipToEquipMap(equip);
            } else {
                AbstractionLayer.getPlayer().sendErrorMessage("Equip wurde nicht gefunden.");
            }
        }
    }
}