package com.rettichlp.unicacityaddon.commands.faction.terroristen;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.services.utils.MathUtils;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand(prefix = "sprenggürtel", usage = "[Countdown]")
public class ExplosiveBeltCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public ExplosiveBeltCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length < 1 || !MathUtils.isInteger(arguments[0])) {
            sendUsage();
            return true;
        }

        this.unicacityAddon.services().fileService().data().setTimer(Integer.parseInt(arguments[0]));
        p.sendServerMessage("/sprenggürtel " + arguments[0]);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}