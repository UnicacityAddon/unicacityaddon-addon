package com.rettichlp.unicacityaddon.listener.faction.rettungsdienst;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.concurrent.TimeUnit;

/**
 * @author Dimiikou
 */
@UCEvent
public class FirstAidEventHandler {

    private final UnicacityAddon unicacityAddon;

    public FirstAidEventHandler(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();

        if (PatternHandler.FIRST_AID_RECEIVE_PATTERN.matcher(msg).find()) {
            FileManager.DATA.setFirstAidDate(System.currentTimeMillis());
            return;
        }

        if (PatternHandler.FIRST_AID_LICENCE_PATTERN.matcher(msg).find()) {
            long expirationTime = FileManager.DATA.getFirstAidDate() + TimeUnit.DAYS.toMillis(14); // Erhaltsdatum + 14 Tage = Auslaufdatum
            long timeLeft = expirationTime - System.currentTimeMillis(); // Auslaufdatum - aktuelle Datum = Dauer des Scheins
            e.setMessage(Message.getBuilder()
                    .space().space()
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of("Erste-Hilfe-Schein").color(ColorCode.BLUE).advance()
                    .of(":").color(ColorCode.DARK_GRAY).advance().space()
                    .of("Vorhanden").color(ColorCode.AQUA)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of(TextUtils.parseTimerWithTimeUnit(timeLeft)).color(ColorCode.RED).advance().createComponent())
                            .advance()
                    .createComponent());
        }
    }
}