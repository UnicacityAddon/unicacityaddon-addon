package com.rettichlp.UnicacityAddon.events.faction.badfaction;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
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
public class AutomatedCalculationOf25EventHandler {

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        Matcher statementOfAccountMatcher = PatternHandler.STATEMENT_OF_ACCOUNT.matcher(e.getMessage().getUnformattedText());

        if (statementOfAccountMatcher.find()) {
            int cash = Integer.parseInt(statementOfAccountMatcher.group(2));
            int bankBalance = Integer.parseInt(statementOfAccountMatcher.group(3));
            int oneQuarterOfAll = (cash + bankBalance) / 4;
            NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("da", "DK"));

            Message.getBuilder()
                    .of("Finanzen von " + statementOfAccountMatcher.group(1)).color(ColorCode.GOLD).advance()
                    .of(":").color(ColorCode.GOLD).advance().space()
                    .of("Geld").color(ColorCode.RED).advance()
                    .of(":").color(ColorCode.DARK_GRAY).advance().space()
                    .of(statementOfAccountMatcher.group(2) + "$ |").color(ColorCode.RED).advance().space()
                    .of("Bank").color(ColorCode.RED).advance()
                    .of(":").color(ColorCode.DARK_GRAY).advance().space()
                    .of(statementOfAccountMatcher.group(3) + "$").color(ColorCode.RED).advance().space()
                    .of("(").color(ColorCode.DARK_GRAY).advance()
                    .of(numberFormat.format(oneQuarterOfAll) + "$").color(ColorCode.RED).advance()
                    .of(")").color(ColorCode.DARK_GRAY).advance().sendTo(p.getPlayer());

            e.setCanceled(true);
        }
        return false;
    }
}
