package com.rettichlp.unicacityaddon.commands.faction.terroristen;

import com.google.inject.Inject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class ExplosiveBeltCommand extends Command {

    private static final String usage = "/sprenggürtel [Countdown]";

    @Inject
    private ExplosiveBeltCommand() {
        super("sprenggürtel");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (!MathUtils.isInteger(arguments[0])) {
            p.sendErrorMessage("Der Countdown muss eine Zahl sein");
            return true;
        }

//        ExplosiveBeltTimerHudWidget.timer = Integer.parseInt(arguments[0]);
        p.sendChatMessage("/sprenggürtel " + arguments[0]);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}