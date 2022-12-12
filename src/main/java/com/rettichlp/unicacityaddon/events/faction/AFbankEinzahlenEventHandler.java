package com.rettichlp.unicacityaddon.events.faction;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.faction.AFbankEinzahlenCommand;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.TimerTask;
import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class AFbankEinzahlenEventHandler {

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        if (!AFbankEinzahlenCommand.STARTED.get())
            return;

        String msg = e.getMessage().getUnformattedText();
        Matcher taxesMatcher = PatternHandler.FBANK_TAXES.matcher(msg);
        if (taxesMatcher.find()) {
            AFbankEinzahlenCommand.STARTED.set(false);
            // send clock command
            AFbankEinzahlenCommand.timer.schedule(new TimerTask() {
                public void run() {
                    AFbankEinzahlenCommand.sendClockMessage();
                    Message.getBuilder()
                            .prefix()
                            .of("Nicht eingezahlt wurden: ").color(ColorCode.GRAY).advance()
                            .of(AFbankEinzahlenCommand.amount + "$").color(ColorCode.BLUE).advance()
                            .of(".").color(ColorCode.GRAY).advance()
                            .sendTo(AbstractionLayer.getPlayer().getPlayer());
                }
            }, 200L);
        }

        if (msg.equals("[F-Bank] Du hast zu wenig Geld.") || msg.equals("Du befindest dich nicht in der Nähe eines Bankautomaten.")) {
            AFbankEinzahlenCommand.STARTED.set(false);
        }
    }
}
