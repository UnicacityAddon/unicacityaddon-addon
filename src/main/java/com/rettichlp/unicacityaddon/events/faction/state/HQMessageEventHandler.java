package com.rettichlp.unicacityaddon.events.faction.state;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.config.ConfigElements;
import com.rettichlp.unicacityaddon.base.enums.api.StatisticType;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class HQMessageEventHandler {

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        Matcher m = PatternHandler.WANTED_REASON.matcher(msg);
        if (ConfigElements.getHQMessagesActivated()) {
            if (m.find()) {
                e.setMessage(Message.getBuilder().of("➥").color(ColorCode.DARK_GRAY).advance().space()
                        .of(m.group(1)).color(ColorCode.BLUE).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.BLUE).advance().createComponent());
                return;
            }

            m = PatternHandler.WANTED_KILL.matcher(msg);
            if (m.find()) {
                if (m.group(2).equals(AbstractionLayer.getPlayer().getName()))
                    APIRequest.sendStatisticAddRequest(StatisticType.KILL);

                e.setMessage(Message.getBuilder().of("Getötet").color(ColorCode.RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(1)).color(ColorCode.BLUE).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.BLUE).advance().createComponent());
                return;
            }

            m = PatternHandler.WANTED_DELETE.matcher(msg);
            if (m.find()) {
                e.setMessage(Message.getBuilder().of("Gelöscht").color(ColorCode.RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(1)).color(ColorCode.BLUE).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.BLUE).advance().createComponent());
                return;
            }

            m = PatternHandler.WANTED_JAIL.matcher(msg);
            if (m.find()) {
                e.setMessage(Message.getBuilder().of("Eingesperrt").color(ColorCode.RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(1)).color(ColorCode.BLUE).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.BLUE).advance().createComponent());
                return;
            }

            m = PatternHandler.WANTED_GIVEN_REASON_PATTERN.matcher(msg);
            if (m.find()) {
                e.setMessage(Message.getBuilder().of("Gesucht").color(ColorCode.RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(1)).color(ColorCode.BLUE).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.BLUE).advance().createComponent());
                return;
            }

            m = PatternHandler.WANTED_GIVEN_POINTS_PATTERN.matcher(msg);
            if (m.find()) {
                e.setMessage(Message.getBuilder().of("➥").color(ColorCode.DARK_GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.BLUE).advance().space()
                        .of("Wanteds").color(ColorCode.BLUE).advance().createComponent());
                return;
            }

            m = PatternHandler.WANTEDS_TICKET_PATTERN.matcher(msg);
            if (m.find()) {
                e.setMessage(Message.getBuilder().of("Ticket").color(ColorCode.RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(1)).color(ColorCode.BLUE).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.BLUE).advance().createComponent());
                return;
            }

            m = PatternHandler.TAKE_DRIVING_LICENSE_PATTERN.matcher(msg);
            if (m.find()) {
                e.setMessage(Message.getBuilder().of("Führerscheinabnahme").color(ColorCode.RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(3)).color(ColorCode.BLUE).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.BLUE).advance().createComponent());
                return;
            }

            m = PatternHandler.GIVE_DRIVING_LICENSE_PATTERN.matcher(msg);
            if (m.find()) {
                e.setMessage(Message.getBuilder().of("Führerscheinrückgabe").color(ColorCode.RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(3)).color(ColorCode.BLUE).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.BLUE).advance().createComponent());
                return;
            }

            m = PatternHandler.TAKE_GUN_LICENSE_PATTERN.matcher(msg);
            if (m.find()) {
                e.setMessage(Message.getBuilder().of("Waffenscheinabnahme").color(ColorCode.RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(3)).color(ColorCode.BLUE).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.BLUE).advance().createComponent());
                return;
            }

            m = PatternHandler.GIVE_GUN_LICENSE_PATTERN.matcher(msg);
            if (m.find()) {
                e.setMessage(Message.getBuilder().of("Waffenscheinrückgabe").color(ColorCode.RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(3)).color(ColorCode.BLUE).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.BLUE).advance().createComponent());
                return;
            }

            m = PatternHandler.TAKE_DRUGS_PATTERN.matcher(msg);
            if (m.find()) {
                e.setMessage(Message.getBuilder().of("Drogenabnahme").color(ColorCode.RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(3)).color(ColorCode.BLUE).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.BLUE).advance().createComponent());
                return;
            }

            m = PatternHandler.TAKE_GUNS_PATTERN.matcher(msg);
            if (m.find()) {
                e.setMessage(Message.getBuilder().of("Waffenabnahme").color(ColorCode.RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(3)).color(ColorCode.BLUE).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.BLUE).advance().createComponent());
            }
        }
    }
}
