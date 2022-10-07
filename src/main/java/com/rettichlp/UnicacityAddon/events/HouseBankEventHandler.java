package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.base.json.HouseBankEntry;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class HouseBankEventHandler {

    public static List<HouseBankEntry> houseBanks;
    public static int lastCheckedHouseNumber = 0;

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        Matcher houseBankHeaderMatcher = PatternHandler.HOUSEBANK_HEADER_PATTERN.matcher(msg);
        if (houseBankHeaderMatcher.find()) {
            lastCheckedHouseNumber = Integer.parseInt(houseBankHeaderMatcher.group(1)); // TODO: Group anpassen
            return false;
        }

        Matcher houseBankValueMatcher = PatternHandler.HOUSEBANK_VALUE_PATTERN.matcher(msg);
        if (houseBankValueMatcher.find()) {
            if (!HouseBankEntry.houseNumbers.contains(lastCheckedHouseNumber)) {
                houseBanks.add(new HouseBankEntry(lastCheckedHouseNumber, Integer.parseInt(houseBankValueMatcher.group(1)))); //TODO: Group anpassen
                return false;
            }

            for (HouseBankEntry houseBank : houseBanks)
                if (houseBank.getHouseNumber() == lastCheckedHouseNumber)
                    houseBank.setValue(Integer.parseInt(houseBankValueMatcher.group(1))); // TODO: Group anpassen

            return false;
        }

        Matcher houseBankRemoveMatcher = PatternHandler.HOUSEBANK_WITHDRAW_PATTERN.matcher(msg);
        if (houseBankRemoveMatcher.find()) {
            for (HouseBankEntry houseBank : houseBanks)
                if (houseBank.getHouseNumber() == lastCheckedHouseNumber)
                    houseBank.removeValue(Integer.parseInt(houseBankRemoveMatcher.group(1))); // TODO: Group anpassen

            return false;
        }
        Matcher houseBankAddMatcher = PatternHandler.HOUSEBANK_DEPOSIT_PATTERN.matcher(msg);
        if (houseBankAddMatcher.find()) {
            for (HouseBankEntry houseBank : houseBanks)
                if (houseBank.getHouseNumber() == lastCheckedHouseNumber)
                    houseBank.addValue(Integer.parseInt(houseBankAddMatcher.group(1))); // TODO: Group anpassen

            return false;
        }

        return false;
    }
}
