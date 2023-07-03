package com.rettichlp.unicacityaddon.commands.money;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand(prefix = "einzahlen")
public class EinzahlenCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public EinzahlenCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (this.unicacityAddon.fileService().data().getCashBalance() > 0)
            p.sendServerMessage("/bank einzahlen " + this.unicacityAddon.fileService().data().getCashBalance());
        else
            p.sendErrorMessage("Du hast kein Geld auf der Hand!");
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}