package com.rettichlp.unicacityaddon.listener.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.faction.FactionBankDepositCommand;
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
        if (!FactionBankDepositCommand.STARTED.get())
            return;

        String msg = e.chatMessage().getPlainText();
        Matcher cashFromFBankMatcher = PatternHandler.CASH_FROM_FBANK_PATTERN.matcher(msg);
        Matcher cashToFBankMatcher = PatternHandler.CASH_TO_FBANK_PATTERN.matcher(msg);
        if ((cashFromFBankMatcher.find() && msg.contains("$ (+")) || (cashToFBankMatcher.find() && msg.contains("$ (-"))) {
            FactionBankDepositCommand.STARTED.set(false);
            // send clock command
            FactionBankDepositCommand.timer.schedule(new TimerTask() {
                public void run() {
                    FactionBankDepositCommand.sendClockMessage(AFbankEinzahlenListener.this.unicacityAddon);
                    AFbankEinzahlenListener.this.unicacityAddon.player().sendMessage(Message.getBuilder()
                            .prefix()
                            .of("Nicht eingezahlt wurden: ").color(ColorCode.GRAY).advance()
                            .of(FactionBankDepositCommand.amount + "$").color(ColorCode.BLUE).advance()
                            .of(".").color(ColorCode.GRAY).advance()
                            .createComponent());
                }
            }, 200L);
        }

        if (msg.equals("[F-Bank] Du hast zu wenig Geld.") || msg.equals("Du befindest dich nicht in der Nähe eines Bankautomaten.")) {
            FactionBankDepositCommand.STARTED.set(false);
        }
    }
}
