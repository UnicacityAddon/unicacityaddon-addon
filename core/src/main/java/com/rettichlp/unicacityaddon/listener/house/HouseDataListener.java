package com.rettichlp.unicacityaddon.listener.house;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugPurity;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.models.HouseData;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
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
public class HouseDataListener {

    public static int lastCheckedHouseNumber = 0;
    public static long lastCheck = -1;

    private static String waitingCommand = "";

    private final UnicacityAddon unicacityAddon;

    public HouseDataListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

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
            HouseData houseData = FileManager.DATA.getHouseData(lastCheckedHouseNumber).setHouseBank(Integer.parseInt(houseBankValueMatcher.group(1)));
            FileManager.DATA.updateHouseData(lastCheckedHouseNumber, houseData);
            return;
        }

        Matcher houseBankRemoveMatcher = PatternHandler.HOUSE_BANK_WITHDRAW_PATTERN.matcher(msg);
        Matcher houseBankAddMatcher = PatternHandler.HOUSE_BANK_DEPOSIT_PATTERN.matcher(msg);
        if (houseBankAddMatcher.find() || houseBankRemoveMatcher.find()) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    lastCheck = System.currentTimeMillis();
                    UnicacityAddon.PLAYER.sendServerMessage("/hkasse");
                }
            }, 1000);
        }

        if (PatternHandler.HOUSE_STORAGE_SUCCESSFUL_INTERACTION.matcher(msg).find() && !waitingCommand.isEmpty()) {
            this.unicacityAddon.logger().info("HOUSE_STORAGE_SUCCESSFUL_INTERACTION found with waiting command: " + waitingCommand);

            Matcher drugStorageAddCommandMatcher = PatternHandler.HOUSE_STORAGE_ADD_COMMAND_PATTERN.matcher(waitingCommand);
            if (drugStorageAddCommandMatcher.find()) {
                int amount = Integer.parseInt(drugStorageAddCommandMatcher.group("amount"));
                DrugType drugType = DrugType.getDrugType(drugStorageAddCommandMatcher.group("drugType"));
                DrugPurity drugPurity = DrugPurity.getDrugPurity(drugStorageAddCommandMatcher.group("drugPurity"));

                HouseData houseData = FileManager.DATA.getHouseData(lastCheckedHouseNumber).addToStorage(drugType, drugPurity, amount);
                FileManager.DATA.updateHouseData(lastCheckedHouseNumber, houseData);

                FileManager.DATA.removeDrugFromInventory(drugType, drugPurity, amount);
            }

            Matcher drugStorageRemoveCommandMatcher = PatternHandler.HOUSE_STORAGE_REMOVE_COMMAND_PATTERN.matcher(waitingCommand);
            if (drugStorageRemoveCommandMatcher.find()) {
                int amount = Integer.parseInt(drugStorageRemoveCommandMatcher.group("amount"));
                DrugType drugType = DrugType.getDrugType(drugStorageRemoveCommandMatcher.group("drugType"));
                DrugPurity drugPurity = DrugPurity.getDrugPurity(drugStorageRemoveCommandMatcher.group("drugPurity"));

                HouseData houseData = FileManager.DATA.getHouseData(lastCheckedHouseNumber).removeFromStorage(drugType, drugPurity, amount);
                FileManager.DATA.updateHouseData(lastCheckedHouseNumber, houseData);

                FileManager.DATA.addDrugToInventory(drugType, drugPurity, amount);
            }
        }
    }

    @Subscribe
    public void onChatMessageSend(ChatMessageSendEvent e) {
        String msg = e.getMessage();
        if (msg.startsWith("/drogenlager ")) {
            lastCheck = System.currentTimeMillis();
            UnicacityAddon.PLAYER.sendServerMessage("/hkasse");
            waitingCommand = msg;
        }
    }
}