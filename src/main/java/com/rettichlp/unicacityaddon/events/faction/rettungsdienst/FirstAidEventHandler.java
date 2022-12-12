package com.rettichlp.unicacityaddon.events.faction.rettungsdienst;

import com.rettichlp.unicacityaddon.base.manager.FileManager;
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

    public static long firstAidIssuingTime;

    @SubscribeEvent
    public void onFirstAidReceipt(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        if (PatternHandler.FIRST_AID_RECEIVE_PATTERN.matcher(msg).find()) {
            firstAidIssuingTime = System.currentTimeMillis();
            FileManager.saveData();
            return;
        }

        if (PatternHandler.FIRST_AID_LICENCE_PATTERN.matcher(msg).find()) {
            long expirationTime = firstAidIssuingTime + TimeUnit.DAYS.toMillis(14); // Erhaltsdatum + 14 Tage = Auslaufdatum
            long timeLeft = expirationTime - System.currentTimeMillis(); // Auslaufdatum - aktuelle Datum = Dauer des Scheins
            e.setMessage(Message.getBuilder()
                    .space().space()
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of("Erste-Hilfe-Schein").color(ColorCode.BLUE).advance()
                    .of(":").color(ColorCode.DARK_GRAY).advance().space()
                    .of("Vorhanden").color(ColorCode.AQUA)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, getDurationMessage(timeLeft))
                            .advance()
                    .createComponent());
        }
    }

    private ITextComponent getDurationMessage(long timeLeft) {
        long dd = TimeUnit.MILLISECONDS.toDays(timeLeft);
        long hh = TimeUnit.MILLISECONDS.toHours(timeLeft) % 24;
        long mm = TimeUnit.MILLISECONDS.toMinutes(timeLeft) % 60;
        long ss = TimeUnit.MILLISECONDS.toSeconds(timeLeft) % 60;

        return Message.getBuilder()
                .of(String.valueOf(dd)).color(ColorCode.AQUA).advance().space()
                .of("d").color(ColorCode.GRAY).advance().space()
                .of(String.valueOf(hh)).color(ColorCode.AQUA).advance().space()
                .of("h").color(ColorCode.GRAY).advance().space()
                .of(String.valueOf(mm)).color(ColorCode.AQUA).advance().space()
                .of("m").color(ColorCode.GRAY).advance().space()
                .of(String.valueOf(ss)).color(ColorCode.AQUA).advance().space()
                .of("s").color(ColorCode.GRAY).advance()
                .createComponent();
    }
}
