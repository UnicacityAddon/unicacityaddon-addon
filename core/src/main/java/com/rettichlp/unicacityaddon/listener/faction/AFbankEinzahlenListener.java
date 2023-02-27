package com.rettichlp.unicacityaddon.listener.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.faction.AFbankEinzahlenCommand;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.TimerTask;
import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
public class AFbankEinzahlenListener {

    private final UnicacityAddon unicacityAddon;

    public AFbankEinzahlenListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        if (!AFbankEinzahlenCommand.STARTED.get())
            return;

        String msg = e.chatMessage().getPlainText();
        Matcher taxesMatcher = PatternHandler.FBANK_TAXES.matcher(msg);
        if (taxesMatcher.find()) {
            AFbankEinzahlenCommand.STARTED.set(false);
            // send clock command
            AFbankEinzahlenCommand.timer.schedule(new TimerTask() {
                public void run() {
                    AFbankEinzahlenCommand.sendClockMessage();
                    UnicacityAddon.PLAYER.sendMessage(Message.getBuilder()
                            .prefix()
                            .of("Nicht eingezahlt wurden: ").color(ColorCode.GRAY).advance()
                            .of(AFbankEinzahlenCommand.amount + "$").color(ColorCode.BLUE).advance()
                            .of(".").color(ColorCode.GRAY).advance()
                            .createComponent());
                }
            }, 200L);
        }

        if (msg.equals("[F-Bank] Du hast zu wenig Geld.") || msg.equals("Du befindest dich nicht in der Nähe eines Bankautomaten.")) {
            AFbankEinzahlenCommand.STARTED.set(false);
        }
    }
}
