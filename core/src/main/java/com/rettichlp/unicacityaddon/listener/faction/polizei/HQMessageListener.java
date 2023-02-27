package com.rettichlp.unicacityaddon.listener.faction.polizei;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class HQMessageListener {

    private final UnicacityAddon unicacityAddon;

    public HQMessageListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();

        Matcher m = PatternHandler.WANTED_REASON.matcher(msg);
        if (unicacityAddon.configuration().factionMessageSetting().hq().get()) {
            if (m.find()) {
                e.setMessage(Message.getBuilder().of("➥").color(ColorCode.DARK_GRAY).advance().space()
                        .of(m.group(1)).color(ColorCode.BLUE).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.BLUE).advance().createComponent());
                return;
            }

            m = PatternHandler.WANTED_KILL.matcher(msg);
            if (m.find()) {
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
            if (m.find())
                e.setMessage(Message.getBuilder().of("Ticket").color(ColorCode.RED).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(1)).color(ColorCode.BLUE).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(m.group(2)).color(ColorCode.BLUE).advance().createComponent());
        }
    }
}
