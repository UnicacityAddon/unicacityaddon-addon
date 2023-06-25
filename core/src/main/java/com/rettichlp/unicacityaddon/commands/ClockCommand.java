package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.commands.faction.AFbankEinzahlenCommand;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand(prefix = "clock", aliases = {"uhrzeit", "uhr"}, onlyOnUnicacity = false)
public class ClockCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public ClockCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AFbankEinzahlenCommand.sendClockMessage(this.unicacityAddon);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}