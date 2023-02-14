package com.rettichlp.unicacityaddon.events.faction.state;

import com.rettichlp.unicacityaddon.base.config.ConfigElements;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author Gelegenheitscode
 */
@UCEvent
public class DrugMessageEventHandler {

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

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
}
