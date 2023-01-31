package com.rettichlp.unicacityaddon.events.house;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugPurity;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.manager.HouseDataManager;
import com.rettichlp.unicacityaddon.base.models.HouseData;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import joptsimple.internal.Strings;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;

/**
 * @author Dimiikou
 * @author RettichLP
 */
@UCEvent
public class HouseDataEventHandler {

    public static int lastCheckedHouseNumber = 0;
    public static long lastCheck = -1;

    private static String waitingCommand = Strings.EMPTY;

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        Matcher houseBankHeaderMatcher = PatternHandler.HOUSE_BANK_HEADER_PATTERN.matcher(msg);
        if (houseBankHeaderMatcher.find()) {
            if (System.currentTimeMillis() - lastCheck < 500)
                e.setCanceled(true);
            lastCheckedHouseNumber = Integer.parseInt(houseBankHeaderMatcher.group(1));
            return;
        }

        Matcher houseBankValueMatcher = PatternHandler.HOUSE_BANK_VALUE_PATTERN.matcher(msg);
        if (houseBankValueMatcher.find()) {
            if (System.currentTimeMillis() - lastCheck < 500)
                e.setCanceled(true);
            HouseData houseData = HouseDataManager.getHouseData(lastCheckedHouseNumber).setHouseBank(Integer.parseInt(houseBankValueMatcher.group(1)));
            HouseDataManager.updateHouseData(lastCheckedHouseNumber, houseData);
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
                int amount = Integer.parseInt(drugStorageAddCommandMatcher.group("amount"));
                DrugType drugType = DrugType.getDrugType(drugStorageAddCommandMatcher.group("drugType"));
                DrugPurity drugPurity = DrugPurity.getDrugPurity(drugStorageAddCommandMatcher.group("drugPurity"));

                HouseData houseData = HouseDataManager.getHouseData(lastCheckedHouseNumber).addToStorage(drugType, drugPurity, amount);
                HouseDataManager.updateHouseData(lastCheckedHouseNumber, houseData);

                FileManager.DATA.removeDrugFromInventory(drugType, drugPurity, amount);
            }

            Matcher drugStorageRemoveCommandMatcher = PatternHandler.HOUSE_STORAGE_REMOVE_COMMAND_PATTERN.matcher(waitingCommand);
            if (drugStorageRemoveCommandMatcher.find()) {
                int amount = Integer.parseInt(drugStorageRemoveCommandMatcher.group("amount"));
                DrugType drugType = DrugType.getDrugType(drugStorageRemoveCommandMatcher.group("drugType"));
                DrugPurity drugPurity = DrugPurity.getDrugPurity(drugStorageRemoveCommandMatcher.group("drugPurity"));

                HouseData houseData = HouseDataManager.getHouseData(lastCheckedHouseNumber).removeFromStorage(drugType, drugPurity, amount);
                HouseDataManager.updateHouseData(lastCheckedHouseNumber, houseData);

                FileManager.DATA.addDrugToInventory(drugType, drugPurity, amount);
            }
        }
    }

    @SubscribeEvent
    public void onClientChat(ClientChatEvent e) {
        String msg = e.getMessage();
        if (msg.startsWith("/drogenlager ")) {
            lastCheck = System.currentTimeMillis();
            AbstractionLayer.getPlayer().sendChatMessage("/hkasse");
            waitingCommand = msg;
        }
    }
}