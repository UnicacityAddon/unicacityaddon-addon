package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class CancelCountdownCommand extends Command {

    public CancelCountdownCommand() {
        super("cancelcountdown");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        CountdownCommand.countdown = -1;
        UnicacityAddon.PLAYER.sendInfoMessage("Der Countdown wurde abgebrochen.");
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}