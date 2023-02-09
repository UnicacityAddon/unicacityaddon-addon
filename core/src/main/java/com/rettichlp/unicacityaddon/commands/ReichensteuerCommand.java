package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import net.labymod.api.client.chat.command.Command;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Dimiikou
 */
@UCCommand
public class ReichensteuerCommand extends Command {

    public static boolean isActive = false;
    public static int cashInATM = 0;

    public ReichensteuerCommand() {
        super("reichensteuer");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = UnicacityAddon.PLAYER;

        if (FileManager.DATA.getBankBalance() > 100000) {
            if (isActive)
                return true;

            p.sendServerMessage("/atminfo");
            isActive = true;
            int removeMoneyAmount = FileManager.DATA.getBankBalance() - 100000;

            (new Timer()).schedule(new TimerTask() {
                @Override
                public void run() {
                    if (cashInATM < removeMoneyAmount) {
                        p.sendServerMessage("/bank abbuchen " + (cashInATM));
                        p.sendInfoMessage("Du musst noch " + (removeMoneyAmount - cashInATM) + " abbuchen.");
                        isActive = false;
                        return;
                    }
                    p.sendServerMessage("/bank abbuchen " + removeMoneyAmount);
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