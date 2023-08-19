package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "toggleshout", aliases = {"toggles", "toggleschreien", "sonorus"}) // Sonorus = Harry Potter Anspielung
public class ToggleShoutCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public ToggleShoutCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();
        p.setShouting(!p.isShouting());

        if (p.isShouting()) {
            p.sendInfoMessage("Ab sofort schreist du jede Nachricht.");
        } else {
            p.sendInfoMessage("Ab sofort redest du wieder normal.");
        }
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}