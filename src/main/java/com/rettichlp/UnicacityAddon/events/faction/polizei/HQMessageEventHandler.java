package com.rettichlp.UnicacityAddon.events.faction.polizei;

import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
public class HQMessageEventHandler {

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        Matcher m = PatternHandler.WANTED_REASON.matcher(msg);
        if (ConfigElements.getHQMessagesActivated()) {
            if (m.find()) {
                e.setMessage(Message.getBuilder().of("➥").color(ColorCode.DARK_GRAY).advance().space()
                        .of(m.group(1)).color(ColorCode.BLUE).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.BLUE).advance().createComponent());
                return false;
            }

            m = PatternHandler.WANTED_KILL.matcher(msg);
            if (m.find()) {
                e.setMessage(Message.getBuilder().of("Getötet").color(ColorCode.RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(1)).color(ColorCode.BLUE).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.BLUE).advance().createComponent());
                return false;
            }

            m = PatternHandler.WANTED_DELETE.matcher(msg);
            if (m.find()) {
                e.setMessage(Message.getBuilder().of("Gelöscht").color(ColorCode.RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(1)).color(ColorCode.BLUE).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.BLUE).advance().createComponent());
                return false;
            }

            m = PatternHandler.WANTED_JAIL.matcher(msg);
            if (m.find()) {
                e.setMessage(Message.getBuilder().of("Eingesperrt").color(ColorCode.RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(1)).color(ColorCode.BLUE).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.BLUE).advance().createComponent());
                return false;
            }

            m = PatternHandler.WANTED_GIVEN_REASON_PATTERN.matcher(msg);
            if (m.find()) {
                e.setMessage(Message.getBuilder().of("Gesucht").color(ColorCode.RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(1)).color(ColorCode.BLUE).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.BLUE).advance().createComponent());
                return false;
            }

            m = PatternHandler.WANTED_GIVEN_POINTS_PATTERN.matcher(msg);
            if (m.find()) {
                e.setMessage(Message.getBuilder().of("➥").color(ColorCode.DARK_GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.BLUE).advance().space()
                        .of("Wanteds").advance().createComponent());
                return false;
            }

            m = PatternHandler.WANTEDS_TICKET_PATTERN.matcher(msg);
            if (m.find())
                e.setMessage(Message.getBuilder().of("Ticket").color(ColorCode.RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(1)).color(ColorCode.BLUE).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.BLUE).advance().createComponent());
        }
        return false;
    }
}
