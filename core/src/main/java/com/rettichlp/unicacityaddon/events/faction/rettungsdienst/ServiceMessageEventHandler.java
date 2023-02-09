package com.rettichlp.unicacityaddon.events.faction.rettungsdienst;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class ServiceMessageEventHandler {

    private final UnicacityAddon unicacityAddon;

    public ServiceMessageEventHandler(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();

        Matcher m = PatternHandler.SERVICE_ARRIVED_PATTERN.matcher(msg);
        if (unicacityAddon.configuration().factionMessageSetting().service().get()) {
            if (m.find()) {
                Component hoverMessage = Message.getBuilder().of("Annehmen").color(ColorCode.RED).advance().createComponent();

                e.setMessage(Message.getBuilder()
                        .of("Neuer Notruf").color(ColorCode.RED).bold()
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage) // Grund
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptservice " + m.group(1).replace("[UC]", ""))
                                .advance().space()
                        .of("-").color(ColorCode.GRAY)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage) // Grund
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptservice " + m.group(1).replace("[UC]", ""))
                                .advance().space()
                        .of(m.group(1)).color(ColorCode.DARK_RED)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage) // Grund
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptservice " + m.group(1).replace("[UC]", ""))
                                .advance().space() // Notruf Sender
                        .of("-").color(ColorCode.GRAY)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage) // Grund
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptservice " + m.group(1).replace("[UC]", ""))
                                .advance().space()
                        .of("\"" + m.group(3) + "\"").color(ColorCode.DARK_RED)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage) // Grund
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptservice " + m.group(1).replace("[UC]", ""))
                                .advance()
                        .createComponent());
                return;
            }

            m = PatternHandler.SERVICE_REQUEUED_PATTERN.matcher(msg);
            if (m.find()) {
                Component hoverMessage = Message.getBuilder().of("Annehmen").color(ColorCode.RED).advance().createComponent();

                e.setMessage(Message.getBuilder()
                        .of("Neu geöffnet").color(ColorCode.GOLD).bold()
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage) // Grund
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptservice " + m.group(2).replace("[UC]", ""))
                                .advance().space()
                        .of("-").color(ColorCode.GRAY)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage) // Grund
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptservice " + m.group(2).replace("[UC]", ""))
                                .advance().space()
                        .of(m.group(1)).color(ColorCode.DARK_RED)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage) // Grund
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptservice " + m.group(2).replace("[UC]", ""))
                                .advance().space() // Öffner
                        .of("-").color(ColorCode.GRAY)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage) // Grund
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptservice " + m.group(2).replace("[UC]", ""))
                                .advance().space()
                        .of(m.group(2)).color(ColorCode.DARK_RED)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage) // Grund
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptservice " + m.group(2).replace("[UC]", ""))
                                .advance().space() // Notruf sender
                        .createComponent());
                return;
            }

            m = PatternHandler.SERVICE_LOCATION_PATTERN.matcher(msg);
            if (m.find()) {
                e.setMessage(Message.getBuilder()
                        .of("➥").color(ColorCode.GRAY).advance().space()
                        .of(m.group(1)).color(ColorCode.DARK_RED).advance()
                        .createComponent());
                return;
            }

            m = PatternHandler.SERVICE_LOCATION_PATTERN_ONE_NEAREST.matcher(msg);
            if (m.find()) { // one nearest person
                e.setMessage(Message.getBuilder()
                        .of("➥").color(ColorCode.GRAY).advance().space()
                        .of(m.group(1)).color(ColorCode.DARK_RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2) + " (" + m.group(3) + ")").color(ColorCode.DARK_RED).advance()
                        .createComponent());
                return;
            }

            m = PatternHandler.SERVICE_LOCATION_PATTERN_TWO_NEAREST.matcher(msg);
            if (m.find()) {
                e.setMessage(Message.getBuilder()
                        .of("➥").color(ColorCode.GRAY).advance().space()
                        .of(m.group(1)).color(ColorCode.DARK_RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2) + " (" + m.group(3) + ")").color(ColorCode.DARK_RED).advance().space()
                        .of("|").color(ColorCode.GRAY).advance().space()
                        .of(m.group(5) + " (" + m.group(6) + ")").color(ColorCode.DARK_RED).advance()
                        .createComponent());
                return;
            }

            m = PatternHandler.SERVICE_ACCEPTED_PATTERN.matcher(msg);
            if (m.find()) {
                e.setMessage(Message.getBuilder()
                        .of("Angenommen").color(ColorCode.GREEN).bold().advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(1)).color(ColorCode.DARK_RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.DARK_RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(3) + "m").color(ColorCode.DARK_RED).advance()
                        .createComponent());
                return;
            }

            m = PatternHandler.SERVICE_DELETED_PATTERN.matcher(msg);
            if (m.find()) {
                e.setMessage(Message.getBuilder().of("Gelöscht").color(ColorCode.BLUE).bold().advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.DARK_RED).advance().space() // Löscher
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(1)).color(ColorCode.DARK_RED).advance().createComponent()); // Service sender
                return;
            }

            m = PatternHandler.SERVICE_BLOCKED_PATTERN.matcher(msg);
            if (m.find()) {
                e.setMessage(Message.getBuilder().of("Blockiert").color(ColorCode.BLUE).bold().advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.DARK_RED).advance().space() // Blockierer
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(1)).color(ColorCode.DARK_RED).advance().createComponent()); // Blockierter
                return;
            }

            m = PatternHandler.SERVICE_UNBLOCKED_PATTERN.matcher(msg);
            if (m.find()) {
                e.setMessage(Message.getBuilder().of("Entblockt").color(ColorCode.BLUE).bold().advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.DARK_RED).advance().space() // Entblocker
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(1)).color(ColorCode.DARK_RED).advance().createComponent()); // Blockierter
            }
        }
    }
}
