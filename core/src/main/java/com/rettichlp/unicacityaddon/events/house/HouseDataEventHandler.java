package com.rettichlp.unicacityaddon.events.house;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugPurity;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.manager.HouseDataManager;
import com.rettichlp.unicacityaddon.base.models.HouseDataEntry;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import jdk.internal.joptsimple.internal.Strings;
import lombok.NoArgsConstructor;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;

/**
 * @author Dimiikou
 * @author RettichLP
 */
@UCEvent
@NoArgsConstructor
public class HouseDataEventHandler {

    private static int lastCheckedHouseNumber = 0;
    private static long lastCheck = -1;
    private static String waitingCommand = Strings.EMPTY;

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();

        Matcher houseBankHeaderMatcher = PatternHandler.HOUSE_BANK_HEADER_PATTERN.matcher(msg);
        if (houseBankHeaderMatcher.find()) {
            if (System.currentTimeMillis() - lastCheck < 500)
                e.setCancelled(true);
            lastCheckedHouseNumber = Integer.parseInt(houseBankHeaderMatcher.group(1));
            return;
        }

        Matcher houseBankValueMatcher = PatternHandler.HOUSE_BANK_VALUE_PATTERN.matcher(msg);
        if (houseBankValueMatcher.find()) {
            if (System.currentTimeMillis() - lastCheck < 500)
                e.setCancelled(true);
            HouseDataEntry houseDataEntry = HouseDataManager.getHouseData(lastCheckedHouseNumber).setHouseBank(Integer.parseInt(houseBankValueMatcher.group(1)));
            HouseDataManager.saveHouseData(lastCheckedHouseNumber, houseDataEntry);
            return;
        }

        Matcher houseBankRemoveMatcher = PatternHandler.HOUSE_BANK_WITHDRAW_PATTERN.matcher(msg);
        Matcher houseBankAddMatcher = PatternHandler.HOUSE_BANK_DEPOSIT_PATTERN.matcher(msg);
        if (houseBankAddMatcher.find() || houseBankRemoveMatcher.find()) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    lastCheck = System.currentTimeMillis();
                    AbstractionLayer.getPlayer().sendChatMessage("/hkasse");
                }
            }, 1000);
        }

        if (PatternHandler.HOUSE_STORAGE_SUCCESSFUL_INTERACTION.matcher(msg).find() && !waitingCommand.isEmpty()) {
            UnicacityAddon.LOGGER.info("HOUSE_STORAGE_SUCCESSFUL_INTERACTION found with waiting command: " + waitingCommand);

            Matcher drugStorageAddCommandMatcher = PatternHandler.HOUSE_STORAGE_ADD_COMMAND_PATTERN.matcher(waitingCommand);
            if (drugStorageAddCommandMatcher.find()) {
                String[] splittetMsg = waitingCommand.split(" ");

                DrugType drugType = DrugType.getDrugType(splittetMsg[2]);
                DrugPurity drugPurity = DrugPurity.values()[Integer.parseInt(splittetMsg[4])];
                int amount = Integer.parseInt(splittetMsg[3]);

                HouseDataEntry houseDataEntry = HouseDataManager.getHouseData(lastCheckedHouseNumber).addToStorage(drugType, drugPurity, amount);
                HouseDataManager.saveHouseData(lastCheckedHouseNumber, houseDataEntry);
            }

            Matcher drugStorageRemoveCommandMatcher = PatternHandler.HOUSE_STORAGE_REMOVE_COMMAND_PATTERN.matcher(waitingCommand);
            if (drugStorageRemoveCommandMatcher.find()) {
                String[] splittetMsg = waitingCommand.split(" ");

                DrugType drugType = DrugType.getDrugType(splittetMsg[2]);
                DrugPurity drugPurity = DrugPurity.values()[Integer.parseInt(splittetMsg[4])];
                int amount = Integer.parseInt(splittetMsg[3]);

                HouseDataEntry houseDataEntry = HouseDataManager.getHouseData(lastCheckedHouseNumber).removeFromStorage(drugType, drugPurity, amount);
                HouseDataManager.saveHouseData(lastCheckedHouseNumber, houseDataEntry);
            }
        }
    }

    @Subscribe
    public void onClientMessageSend(ChatMessageSendEvent e) {
        String msg = e.getMessage();
        if (msg.startsWith("/drogenlager ")) {
            lastCheck = System.currentTimeMillis();
            AbstractionLayer.getPlayer().sendChatMessage("/hkasse");
            waitingCommand = msg;
        }
    }
}