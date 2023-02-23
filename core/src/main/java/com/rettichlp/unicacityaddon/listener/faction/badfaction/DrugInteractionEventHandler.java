package com.rettichlp.unicacityaddon.listener.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.config.ownUse.OwnUseSetting;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugPurity;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class DrugInteractionEventHandler {

    private static int amount;
    private static DrugType lastDrugType;
    private static DrugPurity lastDrugPurity;
    private static long time;
    private static String type;

    private final UnicacityAddon unicacityAddon;

    public DrugInteractionEventHandler(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        AddonPlayer p = UnicacityAddon.PLAYER;
        String msg = e.chatMessage().getPlainText();

        Matcher drugGetMatcher = PatternHandler.DRUG_GET_PATTERN.matcher(msg);
        if (drugGetMatcher.find()) {
            extractDrugData(drugGetMatcher);
            type = "ADD";
            return;
        }

        Matcher drugGiveMatcher = PatternHandler.DRUG_GIVE_PATTERN.matcher(msg);
        if (drugGiveMatcher.find()) {
            extractDrugData(drugGiveMatcher);
            type = "REMOVE";
            return;
        }

        Matcher dbankGetMatcher = PatternHandler.DBANK_GET_PATTERN.matcher(msg);
        if (dbankGetMatcher.find() && msg.contains(p.getName())) {
            int amount = Integer.parseInt(dbankGetMatcher.group("amount"));
            DrugType drugType = DrugType.getDrugType(dbankGetMatcher.group("drugType"));
            DrugPurity drugPurity = DrugPurity.getDrugPurity(dbankGetMatcher.group("drugPurity"));
            FileManager.DATA.addDrugToInventory(drugType, drugPurity, amount);
            return;
        }

        Matcher dbankGiveMatcher = PatternHandler.DBANK_GIVE_PATTERN.matcher(msg);
        if (dbankGiveMatcher.find() && msg.contains(p.getName())) {
            int amount = Integer.parseInt(dbankGiveMatcher.group("amount"));
            DrugType drugType = DrugType.getDrugType(dbankGiveMatcher.group("drugType"));
            DrugPurity drugPurity = DrugPurity.getDrugPurity(dbankGiveMatcher.group("drugPurity"));
            FileManager.DATA.removeDrugFromInventory(drugType, drugPurity, amount);
            return;
        }

        Matcher medicationGetMatcher = PatternHandler.MEDICATION_GET_PATTERN.matcher(msg);
        if (medicationGetMatcher.find()) {
            int amount = Integer.parseInt(medicationGetMatcher.group("amount"));
            DrugType drugType = DrugType.getDrugType(medicationGetMatcher.group("drugType"));
            FileManager.DATA.addDrugToInventory(drugType, DrugPurity.BEST, amount);
            return;
        }

        Matcher drugUseMatcher = PatternHandler.DRUG_USE_PATTERN.matcher(msg);
        if (drugUseMatcher.find()) {
            DrugType drugType = DrugType.getDrugType(drugUseMatcher.group("drugType"));
            DrugPurity drugPurity = DrugPurity.BEST;

            OwnUseSetting ownUseSetting = unicacityAddon.configuration().ownUseSetting();
            if (drugType != null) {
                switch (drugType) {
                    case COCAINE:
                        drugPurity = ownUseSetting.kokainSetting().purity().get();
                        break;
                    case MARIJUANA:
                        drugPurity = ownUseSetting.marihuanaSetting().purity().get();
                        break;
                    case METH:
                        drugPurity = ownUseSetting.methamphetaminSetting().purity().get();
                        break;
                }
            }

            FileManager.DATA.removeDrugFromInventory(drugType, drugPurity, 1);
        }

        Matcher drugDealAcceptedMatcher = PatternHandler.DRUG_DEAL_ACCEPTED.matcher(msg);
        Matcher trunkInteractionAcceptedMatcher = PatternHandler.TRUNK_INTERACTION_ACCEPTED_PATTERN.matcher(msg);
        if ((drugDealAcceptedMatcher.find() || trunkInteractionAcceptedMatcher.find()) && System.currentTimeMillis() - time < TimeUnit.MINUTES.toMillis(3)) {
            if (type.equals("ADD")) {
                FileManager.DATA.addDrugToInventory(lastDrugType, lastDrugPurity, amount);
            } else if (type.equals("REMOVE")) {
                FileManager.DATA.removeDrugFromInventory(lastDrugType, lastDrugPurity, amount);
            }
        }
    }

    @Subscribe
    public void onChatMessageSend(ChatMessageSendEvent e) {
        String msg = e.getMessage();

        Matcher trunkGetMatcher = PatternHandler.TRUNK_GET_COMMAND_PATTERN.matcher(msg);
        if (trunkGetMatcher.find()) {
            extractDrugData(trunkGetMatcher);
            type = "ADD";
            return;
        }

        Matcher trunkGiveMatcher = PatternHandler.TRUNK_GIVE_COMMAND_PATTERN.matcher(msg);
        if (trunkGiveMatcher.find()) {
            extractDrugData(trunkGiveMatcher);
            type = "REMOVE";
            return;
        }

        Matcher drugUseMatcher = PatternHandler.DRUG_USE_COMMAND_PATTERN.matcher(e.getMessage());
        if (drugUseMatcher.find()) {
            DrugType drugType = DrugType.getDrugType(drugUseMatcher.group("drugType"));
            DrugPurity drugPurity = DrugPurity.getDrugPurity(drugUseMatcher.group("drugPurity"));
            FileManager.DATA.removeDrugFromInventory(drugType, drugPurity, 1);
        }
    }

    private void extractDrugData(Matcher matcher) {
        amount = Integer.parseInt(matcher.group("amount"));
        lastDrugType = DrugType.getDrugType(matcher.group("drugType"));
        lastDrugPurity = DrugPurity.getDrugPurity(matcher.group("drugPurity"));
        time = System.currentTimeMillis();
    }
}
