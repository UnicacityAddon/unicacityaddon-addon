package com.rettichlp.unicacityaddon.commands;

import com.google.inject.Inject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.modules.BankMoneyModule;
import net.labymod.api.client.chat.command.Command;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Dimiikou
 */
@UCCommand
public class ReichensteuerCommand extends Command {

    public static int cashInATM = 0;
    public static boolean isActive = false;

    private static final String usage = "/reichensteuer";

    @Inject
    private ReichensteuerCommand() {
        super("reichensteuer");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (BankMoneyModule.bankBalance > 100000) {
            if (isActive)
                return true;

            p.sendChatMessage("/atminfo");
            isActive = true;
            int removeMoneyAmount = BankMoneyModule.bankBalance - 100000;

            (new Timer()).schedule(new TimerTask() {
                @Override
                public void run() {
                    if (cashInATM < removeMoneyAmount) {
                        p.sendChatMessage("/bank abbuchen " + (cashInATM));
                        p.sendInfoMessage("Du musst noch " + (removeMoneyAmount - cashInATM) + " abbuchen.");
                        isActive = false;
                        return;
                    }
                    p.sendChatMessage("/bank abbuchen " + removeMoneyAmount);
                    isActive = false;
                }
            }, 400);

        } else {
            p.sendErrorMessage("Dein Kontostand ist bereits unter 100.001$!");
        }
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}