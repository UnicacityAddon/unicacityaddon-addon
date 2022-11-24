package com.rettichlp.UnicacityAddon.events.faction.badfaction;

import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.faction.badfaction.DrugPurity;
import com.rettichlp.UnicacityAddon.base.faction.badfaction.DrugTypes;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class DBankMessageEventHandler {

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        if (!ConfigElements.getDrugBankMessagesActivated()) return;

        Matcher dropMatcher = PatternHandler.DBANK_DROP_PATTERN.matcher(msg);
        if (dropMatcher.find()) {
            NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("da", "DK"));
            DrugPurity purity = null;
            DrugTypes type = null;

            for (DrugPurity drugPurity : DrugPurity.values())
                if (drugPurity.getPurityString().equals(dropMatcher.group(4))) purity = drugPurity;

            for (DrugTypes drugTypes : DrugTypes.values())
                if (drugTypes.getDrugName().equals(dropMatcher.group(3))) type = drugTypes;

            assert purity != null;
            assert type != null;
            e.setMessage(Message.getBuilder().of("D").color(ColorCode.GOLD).bold().advance()
                    .of("-").color(ColorCode.GRAY).advance()
                    .of("Bank").color(ColorCode.GOLD).bold().advance().space()
                    .of("●").color(ColorCode.DARK_GRAY).advance().space()
                    .of("+").color(ColorCode.GREEN).advance()
                    .of(dropMatcher.group(2) + "g").color(ColorCode.DARK_AQUA).bold().advance().space()
                    .of(purity.getPurity() + "er").color(ColorCode.DARK_AQUA).bold().advance().space()
                    .of(type.getShortName()).color(ColorCode.DARK_AQUA).bold().advance().space()
                    .of("»").color(ColorCode.GRAY).advance().space()
                    .of(numberFormat.format(Integer.parseInt(dropMatcher.group(5))) + "g").color(ColorCode.DARK_AQUA).bold().advance().space()
                    .of("|").color(ColorCode.GRAY).advance().space()
                    .of(dropMatcher.group(1)).color(ColorCode.DARK_AQUA).bold().advance()
                    .createComponent());
        }

        Matcher getMatcher = PatternHandler.DBANK_GET_PATTERN.matcher(msg);
        if (getMatcher.find()) {
            NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("da", "DK"));
            DrugPurity purity = null;
            DrugTypes type = null;

            for (DrugPurity drugPurity : DrugPurity.values())
                if (drugPurity.getPurityString().equals(getMatcher.group(4))) purity = drugPurity;

            for (DrugTypes drugTypes : DrugTypes.values())
                if (drugTypes.getDrugName().equals(getMatcher.group(3))) type = drugTypes;

            assert purity != null;
            assert type != null;
            e.setMessage(Message.getBuilder().of("D").color(ColorCode.GOLD).bold().advance()
                    .of("-").color(ColorCode.GRAY).advance()
                    .of("Bank").color(ColorCode.GOLD).bold().advance().space()
                    .of("●").color(ColorCode.DARK_GRAY).advance().space()
                    .of("-").color(ColorCode.RED).advance()
                    .of(getMatcher.group(2) + "g").color(ColorCode.DARK_AQUA).bold().advance().space()
                    .of(purity.getPurity() + "er").color(ColorCode.DARK_AQUA).bold().advance().space()
                    .of(type.getShortName()).color(ColorCode.DARK_AQUA).bold().advance().space()
                    .of("»").color(ColorCode.GRAY).advance().space()
                    .of(numberFormat.format(Integer.parseInt(getMatcher.group(5))) + "g").color(ColorCode.DARK_AQUA).bold().advance().space()
                    .of("|").color(ColorCode.GRAY).advance().space()
                    .of(getMatcher.group(1)).color(ColorCode.DARK_AQUA).bold().advance()
                    .createComponent());
        }
    }
}
