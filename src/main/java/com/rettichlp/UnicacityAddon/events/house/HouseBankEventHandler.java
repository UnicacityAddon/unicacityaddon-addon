package com.rettichlp.UnicacityAddon.events.house;

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
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        Matcher houseBankHeaderMatcher = PatternHandler.HOUSE_BANK_HEADER_PATTERN.matcher(msg);
        if (houseBankHeaderMatcher.find()) {
            lastCheckedHouseNumber = Integer.parseInt(houseBankHeaderMatcher.group(1));

            if (System.currentTimeMillis() - lastCheck < 500) e.setCanceled(true);
            return;
        }

        Matcher houseBankValueMatcher = PatternHandler.HOUSE_BANK_VALUE_PATTERN.matcher(msg);
        if (houseBankValueMatcher.find()) {
            if (System.currentTimeMillis() - lastCheck < 500) e.setCanceled(true);

            if (!HouseBankEntry.houseNumbers.contains(lastCheckedHouseNumber)) {
                houseBanks.add(new HouseBankEntry(lastCheckedHouseNumber, Integer.parseInt(houseBankValueMatcher.group(1))));
                return;
            }

            for (HouseBankEntry houseBank : houseBanks)
                if (houseBank.getHouseNumber() == lastCheckedHouseNumber)
                    houseBank.setValue(Integer.parseInt(houseBankValueMatcher.group(1)));

            return;
        }

        Matcher houseBankRemoveMatcher = PatternHandler.HOUSE_BANK_WITHDRAW_PATTERN.matcher(msg);
        if (houseBankRemoveMatcher.find()) {
            (new Timer()).schedule(new TimerTask() {
                @Override
                public void run() {
                    lastCheck = System.currentTimeMillis();
                    AbstractionLayer.getPlayer().sendChatMessage("/hkasse");
                }
            }, 1000);

            return;
        }

        Matcher houseBankAddMatcher = PatternHandler.HOUSE_BANK_DEPOSIT_PATTERN.matcher(msg);
        if (houseBankAddMatcher.find()) {
            (new Timer()).schedule(new TimerTask() {
                @Override
                public void run() {
                    lastCheck = System.currentTimeMillis();
                    AbstractionLayer.getPlayer().sendChatMessage("/hkasse");
                }
            }, 1000);

        }
    }
}