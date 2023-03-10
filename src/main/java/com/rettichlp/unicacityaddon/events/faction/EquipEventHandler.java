package com.rettichlp.unicacityaddon.events.faction;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.enums.faction.Equip;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;

/**
 * @author Dimiikou
 * @author RettichLP
 */
@UCEvent
public class EquipEventHandler {

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        Matcher equipMatcher = PatternHandler.EQUIP_PATTERN.matcher(msg);
        if (equipMatcher.find()) {
            String equipString = equipMatcher.group(1);

            Optional<Equip> equipOptional = Arrays.stream(Equip.values())
                    .filter(eq -> eq.getMessageName().equalsIgnoreCase(equipString))
                    .findAny();

            if (equipOptional.isPresent()) {
                FileManager.DATA.addEquipToEquipMap(equipOptional.get());
            } else {
                AbstractionLayer.getPlayer().sendErrorMessage("Equip wurde nicht gefunden.");
            }
            return;
        }

        Matcher trackerMatcher = PatternHandler.TRACKER_PATTERN.matcher(msg);
        if (trackerMatcher.find()) {
            FileManager.DATA.addEquipToEquipMap(Equip.TRACKER);
        }
    }
}