package com.rettichlp.unicacityaddon.commands.faction.terroristen;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class ExplosiveBeltCommand extends Command {

    private static final String usage = "/sprenggürtel [Countdown]";

    private final UnicacityAddon unicacityAddon;

    public ExplosiveBeltCommand(UnicacityAddon unicacityAddon) {
        super("sprenggürtel");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length < 1 || !MathUtils.isInteger(arguments[0])) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        this.unicacityAddon.fileService().data().setTimer(Integer.parseInt(arguments[0]));
        p.sendServerMessage("/sprenggürtel " + arguments[0]);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}