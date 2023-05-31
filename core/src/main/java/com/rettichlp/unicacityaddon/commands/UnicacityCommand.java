package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import net.labymod.api.client.chat.command.Command;
import org.jetbrains.annotations.NotNull;

/**
 * @author RettichLP
 */
public abstract class UnicacityCommand extends Command {

    private final UnicacityAddon unicacityAddon;
    private final UCCommand ucCommand;

    /**
     * Instantiates a new Command.
     */
    protected UnicacityCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(ucCommand.prefix(), ucCommand.aliases());
        this.unicacityAddon = unicacityAddon;
        this.ucCommand = ucCommand;
    }

    public abstract boolean execute(@NotNull final String[] arguments);

    @Override
    public boolean execute(String prefix, String[] arguments) {
        return (this.unicacityAddon.utils().isUnicacity() || !this.ucCommand.onlyOnUnicacity()) && this.execute(arguments);
    }

    public void sendUsage() {
        this.unicacityAddon.player().sendSyntaxMessage("/" + this.ucCommand.prefix() + " " + this.ucCommand.usage());
    }
}
