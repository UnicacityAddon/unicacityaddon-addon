package com.rettichlp.unicacityaddon.events.faction;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.enums.faction.Equip;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.models.EquipLogEntry;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import lombok.NoArgsConstructor;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
@NoArgsConstructor
public class EquipEventHandler {

    public static List<EquipLogEntry> equipLogEntryList = new ArrayList<>();

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();

        Matcher trackerMatcher = PatternHandler.TRACKER_PATTERN.matcher(msg);
        if (trackerMatcher.find()) {
            boolean found = false;

            for (EquipLogEntry equipLogEntry : equipLogEntryList)
                if (equipLogEntry.getEquip() == Equip.TRACKER) {
                    equipLogEntry.addEquip();
                    found = !found;
                }

            if (!found)
                equipLogEntryList.add(new EquipLogEntry(Equip.TRACKER, 1));
            FileManager.saveData();
        }

        Matcher equipMatcher = PatternHandler.EQUIP_PATTERN.matcher(msg);
        if (!equipMatcher.find())
            return;

        Equip equip = null;
        boolean found = false;

        for (Equip equipEntry : Equip.values())
            if (msg.contains(equipEntry.getMessageName()))
                equip = equipEntry;

        if (equip == null) {
            AbstractionLayer.getPlayer().sendErrorMessage("Equiptyp wurde nicht gefunden.");
            return;
        }

        for (EquipLogEntry equipLogEntry : equipLogEntryList)
            if (equipLogEntry.getEquip() == equip) {
                equipLogEntry.addEquip();
                found = !found;
            }

        if (!found)
            equipLogEntryList.add(new EquipLogEntry(equip, 1));
        FileManager.saveData();
    }
}