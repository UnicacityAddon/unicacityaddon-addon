package com.rettichlp.UnicacityAddon.events.faction.rettungsdienst;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class ServiceMessageEventHandler {

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        Matcher m = PatternHandler.SERVICE_ARRIVED_PATTERN.matcher(msg);
        if (ConfigElements.getServiceMessagesActivated()) {
            if (m.find()) {
                ITextComponent hoverMessage = Message.getBuilder().of("Annehmen").color(ColorCode.RED).advance().createComponent();

                e.setMessage(Message.getBuilder().of("Neuer Notruf").color(ColorCode.RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(1)).color(ColorCode.YELLOW).advance().space() // Notruf Sender
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(3)).color(ColorCode.YELLOW).hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage) // Grund
                        .clickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptservice " + m.group(1).replace("[UC]", ""))
                        .advance().createComponent());
                return false;
            }

            m = PatternHandler.SERVICE_REQUEUED_PATTERN.matcher(msg);
            if (m.find()) {
                ITextComponent hoverMessage = Message.getBuilder().of("Annehmen").color(ColorCode.RED).advance().createComponent();

                e.setMessage(Message.getBuilder().of("Neu geöffnet").color(ColorCode.RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(1)).color(ColorCode.YELLOW).advance().space() // Öffner
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.YELLOW).hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage) // Notruf sender
                        .clickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptservice " + m.group(1).replace("[UC]", ""))
                        .advance().createComponent());
                return false;
            }

            /*
                 "^Der näheste Punkt ist (.+)\\.$");
                 "^Der näheste Punkt ist (.+)\\. Die nähesten Personen sind ((?:\\[UC])*\\w+) \\(((\\d+)m)\\)\\.$");
                 "^Der näheste Punkt ist (.+)\\. Die nähesten Personen sind ((?:\\[UC])*\\w+) \\(((\\d+)m)\\), ((?:\\[UC])*\\w+) \\(((\\d+)m)\\)$\\.");
             */

            m = PatternHandler.SERVICE_LOCATION_PATTERN.matcher(msg);
            if (m.find()) {
                e.setMessage(Message.getBuilder().of("➥").color(ColorCode.DARK_GRAY).advance().space()
                        .of(m.group(1)).color(ColorCode.YELLOW).advance().createComponent());
                return false;
            }

            m = PatternHandler.SERVICE_LOCATION_PATTERN_ONE_NEAREST.matcher(msg);
            if (m.find()) { // Eine näheste Person
                if (m.group(2).replace("[UC]", "").equals(AbstractionLayer.getPlayer().getName()))
                    e.setMessage(Message.getBuilder().of("➥").color(ColorCode.DARK_GRAY).advance().space()
                            .of(m.group(1)).color(ColorCode.YELLOW).advance().space()
                            .of("-").color(ColorCode.GRAY).advance().space()
                            .of("Ja " + m.group(3)).color(ColorCode.YELLOW).advance().createComponent());
                else
                    e.setMessage(Message.getBuilder().of("➥").color(ColorCode.DARK_GRAY).advance().space()
                            .of(m.group(1)).color(ColorCode.YELLOW).advance().space()
                            .of("-").color(ColorCode.GRAY).advance().space()
                            .of("Nein").color(ColorCode.YELLOW).hoverEvent(HoverEvent.Action.SHOW_TEXT,
                                    Message.getBuilder().of("➥" + m.group(2) + m.group(3)).color(ColorCode.YELLOW).advance().createComponent())
                            .advance().createComponent());
                return false;
            }

            m = PatternHandler.SERVICE_LOCATION_PATTERN_TWO_NEAREST.matcher(msg);
            if (m.find()) {
                if (m.group(2).replace("[UC]", "").equals(AbstractionLayer.getPlayer().getName())) {
                    e.setMessage(Message.getBuilder().of("➥").color(ColorCode.DARK_GRAY).advance().space()
                            .of(m.group(1)).color(ColorCode.YELLOW).advance().space()
                            .of("-").color(ColorCode.GRAY).advance().space()
                            .of("Ja " + m.group(3)).color(ColorCode.YELLOW).hoverEvent(HoverEvent.Action.SHOW_TEXT,
                                    Message.getBuilder().of("➥" + m.group(2) + m.group(3)).color(ColorCode.YELLOW).advance().newline()
                                            .of("➥" + m.group(5) + m.group(6)).color(ColorCode.YELLOW).advance().createComponent())
                            .advance().createComponent());
                    return false;

                } else if (m.group(5).replace("[UC]", "").equals(AbstractionLayer.getPlayer().getName())) { // 5 + 6 2. nähester
                    e.setMessage(Message.getBuilder().of("➥").color(ColorCode.DARK_GRAY).advance().space()
                            .of(m.group(1)).color(ColorCode.YELLOW).advance().space()
                            .of("-").color(ColorCode.GRAY).advance().space()
                            .of("Vlt " + m.group(3) + " (" + m.group(6) + ")").color(ColorCode.YELLOW).hoverEvent(HoverEvent.Action.SHOW_TEXT,
                                    Message.getBuilder().of("➥" + m.group(2) + " " + m.group(3)).color(ColorCode.YELLOW).advance().newline()
                                            .of("➥" + m.group(5) + " " + m.group(6)).color(ColorCode.YELLOW).advance().createComponent())
                            .advance().createComponent());
                    return false;
                }

                e.setMessage(Message.getBuilder().of("➥").color(ColorCode.DARK_GRAY).advance().space()
                        .of(m.group(1)).color(ColorCode.YELLOW).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of("Nein").color(ColorCode.YELLOW).hoverEvent(HoverEvent.Action.SHOW_TEXT,
                                Message.getBuilder().of("➥" + m.group(2) + " " + m.group(3)).color(ColorCode.YELLOW).advance().newline()
                                        .of("➥" + m.group(5) + " " + m.group(6)).color(ColorCode.YELLOW).advance().createComponent())
                        .advance().createComponent());
            }

            m = PatternHandler.SERVICE_ACCEPTED_PATTERN.matcher(msg);
            if (m.find()) {
                e.setMessage(Message.getBuilder().of("Angenommen").color(ColorCode.GREEN).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(1)).color(ColorCode.YELLOW).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.YELLOW).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(3) + "m").color(ColorCode.YELLOW).advance().createComponent());
                return false;
            }

            m = PatternHandler.SERVICE_DELETED_PATTERN.matcher(msg);
            if (m.find()) {
                e.setMessage(Message.getBuilder().of("Gelöscht").color(ColorCode.RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.YELLOW).advance().space() // Löscher
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(1)).color(ColorCode.YELLOW).advance().createComponent()); // Service sender
                return false;
            }

            m = PatternHandler.SERVICE_BLOCKED_PATTERN.matcher(msg);
            if (m.find()) {
                e.setMessage(Message.getBuilder().of("Blockiert").color(ColorCode.RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.YELLOW).advance().space() // Blockierer
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(1)).color(ColorCode.YELLOW).advance().createComponent()); // Blockierter
                return false;
            }

            m = PatternHandler.SERVICE_UNBLOCKED_PATTERN.matcher(msg);
            if (m.find()) {
                e.setMessage(Message.getBuilder().of("Entblockt").color(ColorCode.GREEN).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.YELLOW).advance().space() // Entblocker
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(1)).color(ColorCode.YELLOW).advance().createComponent()); // Blockierter
                return false;
            }
        }
        return false;
    }
}
