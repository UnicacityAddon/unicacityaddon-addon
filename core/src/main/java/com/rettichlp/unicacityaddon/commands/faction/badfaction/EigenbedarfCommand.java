package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.google.inject.Inject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.config.ConfigElements;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import net.labymod.api.client.chat.command.Command;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Dimiikou
 */
@UCCommand
public class EigenbedarfCommand extends Command {

    private static final String usage = "/eigenbedarf";

    @Inject
    private EigenbedarfCommand() {
        super("eigenbedarf");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();
        Timer t = new Timer();

        if (ConfigElements.getCocainActivated()) {
            p.sendChatMessage("/dbank get Koks " + ConfigElements.getCocaineAmount() + " " + ConfigElements.getCocainDrugPurity().getPurity());

            if (ConfigElements.getMarihuanaActivated()) {
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        p.sendChatMessage("/dbank get Gras " + ConfigElements.getMarihuanaAmount() + " " + ConfigElements.getMarihuanaDrugPurity().getPurity());

                        if (ConfigElements.getMethActivated())
                            t.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    p.sendChatMessage("/dbank get Meth " + ConfigElements.getMethAmount() + " " + ConfigElements.getMethDrugPurity().getPurity());

                                }
                            }, 1000L);
                    }
                }, 1000L);
            }
            return true;
        }

        if (ConfigElements.getMarihuanaActivated()) {
            p.sendChatMessage("/dbank get Marihuana " + ConfigElements.getMarihuanaAmount() + " " + ConfigElements.getMarihuanaDrugPurity().getPurity());

            if (ConfigElements.getMethActivated())
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        p.sendChatMessage("/dbank get Meth " + ConfigElements.getMethAmount() + " " + ConfigElements.getMethDrugPurity().getPurity());
                    }
                }, 1000L);

            return true;
        }

        if (ConfigElements.getMethActivated())
            p.sendChatMessage("/dbank get Meth " + ConfigElements.getMethAmount() + " " + ConfigElements.getMethDrugPurity().getPurity());
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}
