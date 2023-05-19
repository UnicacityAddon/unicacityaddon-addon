package com.rettichlp.unicacityaddon.commands.money;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "atmfill")
public class ATMFillCommand extends UnicacityCommand {

    public static int cashInATM = 0;
    public static boolean isActive = false;

    private final UnicacityAddon unicacityAddon;

    public ATMFillCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (!isActive) {
            p.sendServerMessage("/atminfo");
            isActive = true;

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    int fillAmount = 100000 - cashInATM;
                    int bankDepositAmount = Math.min(fillAmount, ATMFillCommand.this.unicacityAddon.services().fileService().data().getCashBalance());

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