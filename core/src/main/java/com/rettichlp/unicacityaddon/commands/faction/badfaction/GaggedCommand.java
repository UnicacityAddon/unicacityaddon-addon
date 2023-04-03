package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.listener.faction.badfaction.GaggedListener;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class GaggedCommand extends Command {

    private final UnicacityAddon unicacityAddon;

    public GaggedCommand(UnicacityAddon unicacityAddon) {
        super("geknebelt");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
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