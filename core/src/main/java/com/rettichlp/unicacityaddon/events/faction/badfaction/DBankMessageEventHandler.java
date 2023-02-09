package com.rettichlp.unicacityaddon.events.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugPurity;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class DBankMessageEventHandler {

    private final UnicacityAddon unicacityAddon;

    public DBankMessageEventHandler(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();

        if (!unicacityAddon.configuration().factionMessageSetting().dBank().get())
            return;

        Matcher dropMatcher = PatternHandler.DBANK_GIVE_PATTERN.matcher(msg);
        if (dropMatcher.find()) {
            NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("da", "DK"));
            DrugPurity purity = null;
            DrugType type = null;

            for (DrugPurity drugPurity : DrugPurity.values())
                if (drugPurity.getPurityString().equals(dropMatcher.group(4)))
                    purity = drugPurity;

            for (DrugType drugType : DrugType.values())
                if (drugType.getDrugName().equals(dropMatcher.group(3)))
                    type = drugType;

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
            DrugType type = null;

            for (DrugPurity drugPurity : DrugPurity.values())
                if (drugPurity.getPurityString().equals(getMatcher.group(4)))
                    purity = drugPurity;

            for (DrugType drugType : DrugType.values())
                if (drugType.getDrugName().equals(getMatcher.group(3)))
                    type = drugType;

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
