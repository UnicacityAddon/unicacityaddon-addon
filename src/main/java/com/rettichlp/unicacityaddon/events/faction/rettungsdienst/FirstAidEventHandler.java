package com.rettichlp.unicacityaddon.events.faction.rettungsdienst;

import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.concurrent.TimeUnit;

/**
 * @author Dimiikou
 */
@UCEvent
public class FirstAidEventHandler {

    public static long timeMilliesOnFirstAidReceipt;

    @SubscribeEvent
    public void onFirstAidReceipt(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();
        if (PatternHandler.FIRST_AID_RECEIVE_PATTERN.matcher(msg).find()) {
            timeMilliesOnFirstAidReceipt = System.currentTimeMillis();
            return;
        }

        if (!PatternHandler.FIRST_AID_LICENCE_PATTERN.matcher(msg).find()) return;
        long expiryDate = timeMilliesOnFirstAidReceipt + TimeUnit.DAYS.toMillis(14); // Erhaltsdatum + 14 Tage = Auslaufdatum
        long durationTime = expiryDate - System.currentTimeMillis(); // Auslaufdatum - aktuelle Datum = Dauer des Scheins
        e.setMessage(Message.getBuilder().of("  -").color(ColorCode.GRAY).advance().space()
                .of("Erste-Hilfe-Schein").color(ColorCode.BLUE).advance()
                .of(":").color(ColorCode.DARK_GRAY).advance().space()
                .of("Vorhanden").color(ColorCode.AQUA)
                .hoverEvent(HoverEvent.Action.SHOW_TEXT, getDurationMessage(durationTime)).advance()
                .createComponent());
    }

    private ITextComponent getDurationMessage(long durationTime) {
        long seconds = durationTime / 1000;
        long minutes = seconds / 60;
        seconds -= minutes * 60;
        long hours = minutes / 60;
        minutes -= hours * 60;
        long days = hours / 60;
        hours -= days * 60;

        return Message.getBuilder().of("" + days).color(ColorCode.AQUA).advance().space()
                .of("d ").color(ColorCode.GRAY).advance()
                .of("" + hours).color(ColorCode.AQUA).advance().space()
                .of("h ").color(ColorCode.GRAY).advance()
                .of("" + minutes).color(ColorCode.AQUA).advance().space()
                .of("m ").color(ColorCode.GRAY).advance()
                .of("" + seconds).color(ColorCode.AQUA).advance().space()
                .of("s").color(ColorCode.GRAY).advance().createComponent();
    }
}
