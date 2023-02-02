package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.events.faction.badfaction.GaggedEventHandler;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class GaggedCommand extends Command {

    private static final String usage = "/geknebelt";

    public GaggedCommand() {
    super("geknebelt");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();
        GaggedEventHandler.toggleGagged();

        if (GaggedEventHandler.isGagged()) p.sendInfoMessage("Ab sofort kannst du nur noch flüstern.");
        else p.sendInfoMessage("Ab sofort kannst du wieder normal reden.");
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}