package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class CancelCountdownCommand extends Command {

    private final UnicacityAddon unicacityAddon;

    public CancelCountdownCommand(UnicacityAddon unicacityAddon) {
        super("cancelcountdown");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        CountdownCommand.countdown = -1;
        this.unicacityAddon.player().sendInfoMessage("Der Countdown wurde abgebrochen.");
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}