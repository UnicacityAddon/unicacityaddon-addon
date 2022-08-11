package com.rettichlp.UnicacityAddon.events.faction;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.commands.faction.AFbankEinzahlenCommand;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.TimerTask;
import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
public class AFbankEinzahlenEventHandler {

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        if (!AFbankEinzahlenCommand.STARTED.get()) return false;

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
            return false;
        }

        if (msg.equals("[F-Bank] Du hast zu wenig Geld.") || msg.equals("Du befindest dich nicht in der Nähe eines Bankautomaten.")) {
            AFbankEinzahlenCommand.STARTED.set(false);
        }
        return false;
    }
}
