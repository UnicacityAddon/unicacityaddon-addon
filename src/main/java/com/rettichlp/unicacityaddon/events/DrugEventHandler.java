package com.rettichlp.unicacityaddon.events;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.config.ConfigElements;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugPurity;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 * @author Gelegenheitscode
 */
@UCEvent
public class DrugEventHandler {

    private static int amount;
    private static DrugType lastDrugType;
    private static DrugPurity lastDrugPurity;
    private static long time;
    private static String type;

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();
        UPlayer p = AbstractionLayer.getPlayer();

        Matcher drugGetMatcher = PatternHandler.DRUG_GET_PATTERN.matcher(msg);
        if (drugGetMatcher.find()) {
            extractDrugData(drugGetMatcher);
            type = "ADD";

            if (p.getFaction().equals(Faction.FBI) && p.getRank() > 1) {
                String name = drugGetMatcher.group(1);
                p.sendMessage(Message.getBuilder().of("Drogenabnahme?").color(ColorCode.DARK_AQUA).advance().space()
                        .of("[DA5]").color(ColorCode.AQUA).bold()
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Gebe dem Spieler die Wantedmodifikation").color(ColorCode.GOLD).advance().createComponent())
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/mw " + name + " da5")
                                .advance()
                        .space()
                        .of("[DA10]").color(ColorCode.AQUA).bold()
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Gebe dem Spieler die Wantedmodifikation").color(ColorCode.GOLD).advance().createComponent())
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/mw " + name + " da10")
                                .advance()
                        .space()
                        .of("[DA15]").color(ColorCode.AQUA).bold()
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Gebe dem Spieler die Wantedmodifikation").color(ColorCode.GOLD).advance().createComponent())
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/mw " + name + " da15")
                                .advance()
                        .createComponent());
            }
            return;
        }

        Matcher drugGiveMatcher = PatternHandler.DRUG_GIVE_PATTERN.matcher(msg);
        if (drugGiveMatcher.find()) {
            extractDrugData(drugGiveMatcher);
            type = "REMOVE";
            return;
        }

        Matcher dbankGetMatcher = PatternHandler.DBANK_GET_PATTERN.matcher(msg);
        if (dbankGetMatcher.find()) {
            int amount = Integer.parseInt(dbankGetMatcher.group("amount"));
            DrugType drugType = DrugType.getDrugType(dbankGetMatcher.group("drugType"));
            DrugPurity drugPurity = DrugPurity.getDrugPurity(dbankGetMatcher.group("drugPurity"));

            if (msg.contains(p.getName())) {
                FileManager.DATA.addDrugToInventory(drugType, drugPurity, amount);
            }

            if (ConfigElements.getDrugBankMessagesActivated()) {
                NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("da", "DK"));
                e.setMessage(Message.getBuilder().of("D").color(ColorCode.GOLD).bold().advance()
                        .of("-").color(ColorCode.GRAY).advance()
                        .of("Bank").color(ColorCode.GOLD).bold().advance().space()
                        .of("●").color(ColorCode.DARK_GRAY).advance().space()
                        .of("-").color(ColorCode.RED).advance()
                        .of(amount + "g").color(ColorCode.DARK_AQUA).bold().advance().space()
                        .of(drugPurity.getPurity() + "er").color(ColorCode.DARK_AQUA).bold().advance().space()
                        .of(drugType.getDrugName()).color(ColorCode.DARK_AQUA).bold().advance().space()
                        .of("»").color(ColorCode.GRAY).advance().space()
                        .of(numberFormat.format(Integer.parseInt(dbankGetMatcher.group(5))) + "g").color(ColorCode.DARK_AQUA).bold().advance().space()
                        .of("|").color(ColorCode.GRAY).advance().space()
                        .of(dbankGetMatcher.group(1)).color(ColorCode.DARK_AQUA).bold().advance()
                        .createComponent());
            }
            return;
        }

        Matcher dbankGiveMatcher = PatternHandler.DBANK_GIVE_PATTERN.matcher(msg);
        if (dbankGiveMatcher.find()) {
            int amount = Integer.parseInt(dbankGiveMatcher.group("amount"));
            DrugType drugType = DrugType.getDrugType(dbankGiveMatcher.group("drugType"));
            DrugPurity drugPurity = DrugPurity.getDrugPurity(dbankGiveMatcher.group("drugPurity"));

            if (msg.contains(p.getName())) {
                FileManager.DATA.removeDrugFromInventory(drugType, drugPurity, amount);
            }

            if (ConfigElements.getDrugBankMessagesActivated()) {
                NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("da", "DK"));
                e.setMessage(Message.getBuilder().of("D").color(ColorCode.GOLD).bold().advance()
                        .of("-").color(ColorCode.GRAY).advance()
                        .of("Bank").color(ColorCode.GOLD).bold().advance().space()
                        .of("●").color(ColorCode.DARK_GRAY).advance().space()
                        .of("+").color(ColorCode.GREEN).advance()
                        .of(amount + "g").color(ColorCode.DARK_AQUA).bold().advance().space()
                        .of(drugPurity.getPurity() + "er").color(ColorCode.DARK_AQUA).bold().advance().space()
                        .of(drugType.getDrugName()).color(ColorCode.DARK_AQUA).bold().advance().space()
                        .of("»").color(ColorCode.GRAY).advance().space()
                        .of(numberFormat.format(Integer.parseInt(dbankGiveMatcher.group(5))) + "g").color(ColorCode.DARK_AQUA).bold().advance().space()
                        .of("|").color(ColorCode.GRAY).advance().space()
                        .of(dbankGiveMatcher.group(1)).color(ColorCode.DARK_AQUA).bold().advance()
                        .createComponent());
            }
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

            if (drugType != null) {
                switch (drugType) {
                    case COCAINE:
                        drugPurity = ConfigElements.getCocainDrugPurity();
                        break;
                    case MARIJUANA:
                        drugPurity = ConfigElements.getMarihuanaDrugPurity();
                        break;
                    case METH:
                        drugPurity = ConfigElements.getMethDrugPurity();
                        break;
                }
            }

            FileManager.DATA.removeDrugFromInventory(drugType, drugPurity, 1);
            return;
        }

        Matcher drugDealAcceptedMatcher = PatternHandler.DRUG_DEAL_ACCEPTED.matcher(msg);
        Matcher trunkInteractionAcceptedMatcher = PatternHandler.TRUNK_INTERACTION_ACCEPTED_PATTERN.matcher(msg);
        if ((drugDealAcceptedMatcher.find() || trunkInteractionAcceptedMatcher.find()) && System.currentTimeMillis() - time < TimeUnit.MINUTES.toMillis(3)) {
            if (type.equals("ADD")) {
                FileManager.DATA.addDrugToInventory(lastDrugType, lastDrugPurity, amount);
            } else if (type.equals("REMOVE")) {
                FileManager.DATA.removeDrugFromInventory(lastDrugType, lastDrugPurity, amount);
            }
            return;
        }

        if (ConfigElements.getDrugVaultMessageActivated()) {
            Matcher drugVaultDropMatcher = PatternHandler.DRUG_VAULT_DROP_PATTERN.matcher(msg);
            if (drugVaultDropMatcher.find()) {
                e.setMessage(Message.getBuilder().of("Asservatenkammer").color(ColorCode.DARK_AQUA).bold().advance().space()
                        .of("|").color(ColorCode.DARK_GRAY).advance().space()
                        .of("+").color(ColorCode.GREEN).advance()
                        .of(drugVaultDropMatcher.group(3)).color(ColorCode.GREEN).advance()
                        .of("g").color(ColorCode.GREEN).advance().space()
                        .of(drugVaultDropMatcher.group(4)).color(ColorCode.GREEN).advance().space()
                        .of("(").color(ColorCode.GRAY).advance()
                        .of(drugVaultDropMatcher.group(5)).color(ColorCode.YELLOW).advance()
                        .of(")").color(ColorCode.GRAY).advance().space()
                        .of("|").color(ColorCode.DARK_GRAY).advance().space()
                        .of(drugVaultDropMatcher.group(2)).color(ColorCode.AQUA).advance()
                        .createComponent());
                return;
            }

            Matcher drugVaultGetMatcher = PatternHandler.DRUG_VAULT_GET_PATTERN.matcher(msg);
            if (drugVaultGetMatcher.find()) {
                e.setMessage(Message.getBuilder().of("Asservatenkammer").color(ColorCode.DARK_AQUA).bold().advance().space()
                        .of("|").color(ColorCode.DARK_GRAY).advance().space()
                        .of("-").color(ColorCode.RED).advance()
                        .of(drugVaultGetMatcher.group(3)).color(ColorCode.RED).advance()
                        .of("g").color(ColorCode.RED).advance().space()
                        .of(drugVaultGetMatcher.group(4)).color(ColorCode.RED).advance().space()
                        .of("(").color(ColorCode.GRAY).advance()
                        .of(drugVaultGetMatcher.group(5)).color(ColorCode.YELLOW).advance()
                        .of(")").color(ColorCode.GRAY).advance().space()
                        .of("|").color(ColorCode.DARK_GRAY).advance().space()
                        .of(drugVaultGetMatcher.group(2)).color(ColorCode.AQUA).advance()
                        .createComponent());
                return;
            }

            Matcher drugVaultBurnMatcher = PatternHandler.DRUG_VAULT_BURN_PATTERN.matcher(msg);
            if (drugVaultBurnMatcher.find()) {
                e.setMessage(Message.getBuilder().of("Asservatenkammer").color(ColorCode.DARK_AQUA).bold().advance().space()
                        .of("|").color(ColorCode.DARK_GRAY).advance().space()
                        .of("✕").color(ColorCode.GOLD).advance().space()
                        .of(drugVaultBurnMatcher.group(3)).color(ColorCode.GOLD).advance()
                        .of("g").color(ColorCode.GOLD).advance().space()
                        .of(drugVaultBurnMatcher.group(4)).color(ColorCode.GOLD).advance().space()
                        .of("(").color(ColorCode.GRAY).advance()
                        .of(drugVaultBurnMatcher.group(5)).color(ColorCode.YELLOW).advance()
                        .of(")").color(ColorCode.GRAY).advance().space()
                        .of("|").color(ColorCode.DARK_GRAY).advance().space()
                        .of(drugVaultBurnMatcher.group(2)).color(ColorCode.AQUA).advance()
                        .createComponent());
                return;
            }
            
            Matcher drugVaultInfoMatcher = PatternHandler.DRUG_VAULT_INFO_PATTERN.matcher(msg);
            if (drugVaultInfoMatcher.find()) {
                e.setCanceled(true);
                p.sendMessage(Message.getBuilder()
                        .of("»").color(ColorCode.DARK_GRAY).advance().space()
                        .of(drugVaultInfoMatcher.group(1) + " Reinheit").color(ColorCode.GOLD).advance()
                        .of(":").color(ColorCode.DARK_GRAY).advance().space()
                        .of(drugVaultInfoMatcher.group(2)).color(ColorCode.YELLOW).advance()
                        .of("g").color(ColorCode.YELLOW).advance().createComponent());
                p.sendMessage(Message.getBuilder()
                        .of("»").color(ColorCode.DARK_GRAY).advance().space()
                        .of(drugVaultInfoMatcher.group(3) + " Reinheit").color(ColorCode.GOLD).advance()
                        .of(":").color(ColorCode.DARK_GRAY).advance().space()
                        .of(drugVaultInfoMatcher.group(4)).color(ColorCode.YELLOW).advance()
                        .of("g").color(ColorCode.YELLOW).advance().createComponent());
                p.sendMessage(Message.getBuilder()
                        .of("»").color(ColorCode.DARK_GRAY).advance().space()
                        .of(drugVaultInfoMatcher.group(5) + " Reinheit").color(ColorCode.GOLD).advance()
                        .of(":").color(ColorCode.DARK_GRAY).advance().space()
                        .of(drugVaultInfoMatcher.group(6)).color(ColorCode.YELLOW).advance()
                        .of("g").color(ColorCode.YELLOW).advance().createComponent());
                p.sendMessage(Message.getBuilder()
                        .of("»").color(ColorCode.DARK_GRAY).advance().space()
                        .of(drugVaultInfoMatcher.group(7) + " Reinheit").color(ColorCode.GOLD).advance()
                        .of(":").color(ColorCode.DARK_GRAY).advance().space()
                        .of(drugVaultInfoMatcher.group(8)).color(ColorCode.YELLOW).advance()
                        .of("g").color(ColorCode.YELLOW).advance()
                        .createComponent());
                return;
            }
            
            Matcher drugVaultInfoLSDMatcher = PatternHandler.DRUG_VAULT_INFOLSD_PATTERN.matcher(msg);
            if (drugVaultInfoLSDMatcher.find()) {
                e.setMessage(Message.getBuilder()
                        .of("»").color(ColorCode.DARK_GRAY).advance().space()
                        .of("LSD").color(ColorCode.GOLD).advance()
                        .of(":").color(ColorCode.DARK_GRAY).advance().space()
                        .of(drugVaultInfoLSDMatcher.group(1)).color(ColorCode.YELLOW).advance().space()
                        .of("Stück").color(ColorCode.YELLOW).advance().createComponent());
                return;
            }
            
            Matcher drugVaultInfoTitleMatcher = PatternHandler.DRUG_VAULT_INFOTITLE_PATTERN.matcher(msg);
            if (drugVaultInfoTitleMatcher.find()) {
                e.setMessage(Message.getBuilder()
                        .of("===").color(ColorCode.DARK_GRAY).advance().space()
                        .of("Asservatenkammer").color(ColorCode.GOLD).advance().space()
                        .of("[").color(ColorCode.DARK_GRAY).advance()
                        .of(drugVaultInfoTitleMatcher.group(1)).color(ColorCode.YELLOW).advance()
                        .of("]").color(ColorCode.DARK_GRAY).advance().space()
                        .of("===").color(ColorCode.DARK_GRAY).advance().createComponent());
                return;
            }
        }

        if (ConfigElements.getPlantBurnMessageActivated()) {
            Matcher plantBurnMatcher = PatternHandler.PLANT_BURN_PATTERN.matcher(msg);
            if (plantBurnMatcher.find()) {
                e.setMessage(Message.getBuilder().of("Plant-Burn").color(ColorCode.RED).bold().advance().space()
                        .of("|").color(ColorCode.DARK_GRAY).advance().space()
                        .of(plantBurnMatcher.group(2)).color(ColorCode.GREEN).advance().space()
                        .of("(").color(ColorCode.GRAY).advance()
                        .of(plantBurnMatcher.group(3)).color(ColorCode.GOLD).advance()
                        .of(")").color(ColorCode.GRAY).advance().space()
                        .createComponent());
            }
        }
    }

    @SubscribeEvent
    public void onClientChat(ClientChatEvent e) {
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
