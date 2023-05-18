package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.commands.faction.AFbankEinzahlenCommand;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class ClockCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public ClockCommand(UnicacityAddon unicacityAddon) {
        super(unicacityAddon, "clock", false, "uhrzeit", "uhr");
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