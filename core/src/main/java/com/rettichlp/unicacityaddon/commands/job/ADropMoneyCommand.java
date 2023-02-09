package com.rettichlp.unicacityaddon.commands.job;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import net.labymod.api.client.chat.command.Command;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Dimiikou
 */
@UCCommand
public class ADropMoneyCommand extends Command {

    private int step = 0;

    public ADropMoneyCommand() {
        super("adropmoney");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = UnicacityAddon.PLAYER;

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                switch (step++) {
                    case 1:
                        p.sendServerMessage("/bank abbuchen 15000");
                        break;
                    case 2:
                        p.sendServerMessage("/dropmoney");
                        break;
                    case 3:
                        p.sendServerMessage("/bank einzahlen 15000");
                        step = 0;
                        this.cancel();
                        break;
                }
            }
        }, 0, 1000);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}