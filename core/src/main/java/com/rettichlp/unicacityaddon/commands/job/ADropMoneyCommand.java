package com.rettichlp.unicacityaddon.commands.job;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
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

    private final UnicacityAddon unicacityAddon;

    public ADropMoneyCommand(UnicacityAddon unicacityAddon) {
        super("adropmoney");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

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
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}