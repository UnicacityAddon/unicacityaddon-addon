package com.rettichlp.unicacityaddon.events.faction.fbi;

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
public class DrugVaultMessageEventHandler {
    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        Matcher m = PatternHandler.DRUG_VAULT_DROP.matcher(msg);
        Matcher m1 = PatternHandler.DRUG_VAULT_GET.matcher(msg);
        Matcher m2 = PatternHandler.DRUG_VAULT_BURN.matcher(msg);
        if (ConfigElements.getDrugVaultMessageActivated())  {
            if (m.find()) {
                e.setMessage(Message.getBuilder().of("Asservatenkammer").color(ColorCode.DARK_AQUA).bold().advance().space()
                        .of("|").color(ColorCode.DARK_GRAY).advance().space()
                        .of("+").color(ColorCode.GREEN).advance()
                        .of(m.group(3)).color(ColorCode.GREEN).advance()
                        .of("g").color(ColorCode.GREEN).advance().space()
                        .of(m.group(4)).color(ColorCode.GREEN).advance().space()
                        .of("(").color(ColorCode.GRAY).advance()
                        .of(m.group(5)).color(ColorCode.YELLOW).advance()
                        .of(")").color(ColorCode.GRAY).advance().space()
                        .of("|").color(ColorCode.DARK_GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.AQUA).advance()
                        .createComponent());


            } else if (m1.find()) {
                e.setMessage(Message.getBuilder().of("Asservatenkammer").color(ColorCode.DARK_AQUA).bold().advance().space()
                        .of("|").color(ColorCode.DARK_GRAY).advance().space()
                        .of("-").color(ColorCode.RED).advance()
                        .of(m1.group(3)).color(ColorCode.RED).advance()
                        .of("g").color(ColorCode.RED).advance().space()
                        .of(m1.group(4)).color(ColorCode.RED).advance().space()
                        .of("(").color(ColorCode.GRAY).advance()
                        .of(m1.group(5)).color(ColorCode.YELLOW).advance()
                        .of(")").color(ColorCode.GRAY).advance().space()
                        .of("|").color(ColorCode.DARK_GRAY).advance().space()
                        .of(m1.group(2)).color(ColorCode.AQUA).advance()
                        .createComponent());

            } else if (m2.find())   {
                e.setMessage(Message.getBuilder().of("Asservatenkammer").color(ColorCode.DARK_AQUA).bold().advance().space()
                        .of("|").color(ColorCode.DARK_GRAY).advance().space()
                        .of("✕").color(ColorCode.GOLD).advance().space()
                        .of(m2.group(3)).color(ColorCode.GOLD).advance()
                        .of("g").color(ColorCode.GOLD).advance().space()
                        .of(m2.group(4)).color(ColorCode.GOLD).advance().space()
                        .of("(").color(ColorCode.GRAY).advance()
                        .of(m2.group(5)).color(ColorCode.YELLOW).advance()
                        .of(")").color(ColorCode.GRAY).advance().space()
                        .of("|").color(ColorCode.DARK_GRAY).advance().space()
                        .of(m2.group(2)).color(ColorCode.AQUA).advance()
                        .createComponent());
            }
        }

    }
}
