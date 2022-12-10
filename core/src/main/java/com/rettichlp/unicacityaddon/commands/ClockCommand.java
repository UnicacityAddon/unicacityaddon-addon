package com.rettichlp.unicacityaddon.commands;

import com.google.inject.Inject;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.commands.faction.AFbankEinzahlenCommand;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class ClockCommand extends Command {

    private static final String usage = "/clock";

    @Inject
    private ClockCommand() {
        super("clock", "uhrzeit", "uhr");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AFbankEinzahlenCommand.sendClockMessage();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}