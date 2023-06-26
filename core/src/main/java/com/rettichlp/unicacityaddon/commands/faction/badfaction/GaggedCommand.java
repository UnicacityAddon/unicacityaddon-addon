package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand(prefix = "geknebelt")
public class GaggedCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public GaggedCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();
        p.setGagged(!p.isGagged());

        if (p.isGagged()) {
            p.sendInfoMessage("Ab sofort kannst du nur noch flüstern.");
        } else {
            p.sendInfoMessage("Ab sofort kannst du wieder normal reden.");
        }
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}