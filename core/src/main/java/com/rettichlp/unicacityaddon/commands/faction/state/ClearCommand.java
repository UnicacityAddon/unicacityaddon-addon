package com.rettichlp.unicacityaddon.commands.faction.state;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;

import java.util.Arrays;
import java.util.List;

/**
 * @author Gelegenheitscode
 * @author RettichLP
 */
@UCCommand(prefix = "clear", usage = "[Spieler...]")
public class ClearCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public ClearCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        switch (arguments.length) {
            case 0 -> // wrong command syntax
                    sendUsage();
            case 1 -> {
                // correct command syntax for Unicacity, command is passed to Unicacity
                return false;
            }
            default -> // call clear command for each parameter (name)
                    Arrays.stream(arguments).forEach(player -> p.sendServerMessage("/clear " + player));
        }

        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}