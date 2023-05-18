package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;
import com.rettichlp.unicacityaddon.listener.faction.badfaction.GaggedListener;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class GaggedCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public GaggedCommand(UnicacityAddon unicacityAddon) {
        super(unicacityAddon, "geknebelt", true);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();
        GaggedListener.toggleGagged();

        if (GaggedListener.isGagged())
            p.sendInfoMessage("Ab sofort kannst du nur noch flüstern.");
        else
            p.sendInfoMessage("Ab sofort kannst du wieder normal reden.");
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}