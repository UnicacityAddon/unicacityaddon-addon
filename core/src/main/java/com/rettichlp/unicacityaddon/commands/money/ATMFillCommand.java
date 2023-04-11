package com.rettichlp.unicacityaddon.commands.money;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import net.labymod.api.client.chat.command.Command;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author RettichLP
 */
@UCCommand
public class ATMFillCommand extends Command {

    public static int cashInATM = 0;
    public static boolean isActive = false;

    private static final String usage = "/atmfill";

    private final UnicacityAddon unicacityAddon;

    public ATMFillCommand(UnicacityAddon unicacityAddon) {
        super("atmfill");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (!isActive) {
            p.sendServerMessage("/atminfo");
            isActive = true;

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    int fillAmount = 100000 - cashInATM;
                    int bankDepositAmount = Math.min(fillAmount, ATMFillCommand.this.unicacityAddon.fileService().data().getCashBalance());

                    if (bankDepositAmount > 0) {
                        p.sendServerMessage("/bank einzahlen " + bankDepositAmount);
                    } else {
                        p.sendInfoMessage("Der ATM ist bereits voll.");
                    }

                    isActive = false;
                }
            }, 400);
        }
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}