package com.rettichlp.UnicacityAddon.events.faction;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.faction.Equip;
import com.rettichlp.UnicacityAddon.base.json.EquipLogEntry;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class EquipEventHandler {

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        Matcher trackerMatcher = PatternHandler.TRACKER_PATTERN.matcher(msg);
        if (trackerMatcher.find()) {
            boolean found = false;

            for (EquipLogEntry equipLogEntry : EquipLogEntry.equipEntry)
                if (equipLogEntry.getEquip() == Equip.TRACKER) {
                    equipLogEntry.addEquip();
                    found = !found;
                }

            if (!found) EquipLogEntry.equipEntry.add(new EquipLogEntry(Equip.TRACKER, 1));
        }

        Matcher equipMatcher = PatternHandler.EQUIP_PATTERN.matcher(msg);
        if (!equipMatcher.find()) return false;

        Equip equip = null;
        boolean found = false;

        for (Equip equipEntry : Equip.values())
            if (equipEntry.getMessageName().equalsIgnoreCase(equipMatcher.group(2))) equip = equipEntry;

        if (equip == null) {
            AbstractionLayer.getPlayer().sendErrorMessage("Equiptyp wurde nicht gefunden.");
            return false;
        }

        for (EquipLogEntry equipLogEntry : EquipLogEntry.equipEntry)
            if (equipLogEntry.getEquip() == equip) {
                equipLogEntry.addEquip();
                found = !found;
            }

        if (!found) EquipLogEntry.equipEntry.add(new EquipLogEntry(equip, 1));

        return false;
    }

}
