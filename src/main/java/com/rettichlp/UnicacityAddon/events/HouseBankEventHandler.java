package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.json.HouseBankEntry;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class HouseBankEventHandler {

    public static List<HouseBankEntry> houseBanks;
    public static int lastCheckedHouseNumber = 0;
    public static long lastCheck = -1;

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        Matcher houseBankHeaderMatcher = PatternHandler.HOUSEBANK_HEADER_PATTERN.matcher(msg);
        if (houseBankHeaderMatcher.find()) {
            lastCheckedHouseNumber = Integer.parseInt(houseBankHeaderMatcher.group(1));

            if (System.currentTimeMillis() - lastCheck < 500) e.setCanceled(true);
            return false;
        }

        Matcher houseBankValueMatcher = PatternHandler.HOUSEBANK_VALUE_PATTERN.matcher(msg);
        if (houseBankValueMatcher.find()) {
            if (System.currentTimeMillis() - lastCheck < 500) e.setCanceled(true);

            if (!HouseBankEntry.houseNumbers.contains(lastCheckedHouseNumber)) {
                houseBanks.add(new HouseBankEntry(lastCheckedHouseNumber, Integer.parseInt(houseBankValueMatcher.group(1))));
                return false;
            }

            for (HouseBankEntry houseBank : houseBanks)
                if (houseBank.getHouseNumber() == lastCheckedHouseNumber)
                    houseBank.setValue(Integer.parseInt(houseBankValueMatcher.group(1)));

            return false;
        }

        Matcher houseBankRemoveMatcher = PatternHandler.HOUSEBANK_WITHDRAW_PATTERN.matcher(msg);
        if (houseBankRemoveMatcher.find()) {
            (new Timer()).schedule(new TimerTask() {
                @Override
                public void run() {
                    lastCheck = System.currentTimeMillis();
                    AbstractionLayer.getPlayer().sendChatMessage("/hkasse");
                }
            }, 1000);

            return false;
        }

        Matcher houseBankAddMatcher = PatternHandler.HOUSEBANK_DEPOSIT_PATTERN.matcher(msg);
        if (houseBankAddMatcher.find()) {
            (new Timer()).schedule(new TimerTask() {
                @Override
                public void run() {
                    lastCheck = System.currentTimeMillis();
                    AbstractionLayer.getPlayer().sendChatMessage("/hkasse");
                }
            }, 1000);

        }

        return false;
    }
}
