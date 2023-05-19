package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import net.labymod.api.client.chat.command.Command;
import org.jetbrains.annotations.NotNull;

/**
 * @author RettichLP
 */
public abstract class UnicacityCommand extends Command {

    private final UnicacityAddon unicacityAddon;
    private final boolean onlyOnUnicacity;

    /**
     * Instantiates a new Command.
     */
    protected UnicacityCommand(UnicacityAddon unicacityAddon, String prefix, boolean onlyOnUnicacity, String... aliases) {
        super(prefix, aliases);
        this.unicacityAddon = unicacityAddon;
        this.onlyOnUnicacity = onlyOnUnicacity;
    }

    public abstract boolean execute(@NotNull final String[] arguments);

    @Override
    public boolean execute(String prefix, String[] arguments) {
        return (this.unicacityAddon.utils().isUnicacity() || !this.onlyOnUnicacity) && this.execute(arguments);
    }
}
