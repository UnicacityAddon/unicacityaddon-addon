package com.rettichlp.unicacityaddon.commands.faction.state;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;

import java.util.Arrays;
import java.util.List;

/**
 * @author Gelegenheitscode
 */
@UCCommand
public class ClearCommand extends UnicacityCommand {

    private static final String usage = "/clear [Spieler...]";

    private final UnicacityAddon unicacityAddon;

    public ClearCommand(UnicacityAddon unicacityAddon) {
        super(unicacityAddon, "clear", true);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length < 1) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        Arrays.stream(arguments).forEach(player -> p.sendServerMessage("/clear " + player));
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}