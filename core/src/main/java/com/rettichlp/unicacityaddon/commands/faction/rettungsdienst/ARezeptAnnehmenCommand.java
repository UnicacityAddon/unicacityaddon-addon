package com.rettichlp.unicacityaddon.commands.faction.rettungsdienst;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.services.utils.MathUtils;
import com.rettichlp.unicacityaddon.listener.faction.rettungsdienst.MedicationListener;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "arezeptannehmen", aliases = {"arannehmen"}, usage = "[Anzahl]")
public class ARezeptAnnehmenCommand extends UnicacityCommand {

    public static int amount = 0;

    private final UnicacityAddon unicacityAddon;

    public ARezeptAnnehmenCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        if (arguments.length < 1) {
            sendUsage();
            return true;
        }

        if (!MathUtils.isInteger(arguments[0]))
            return true;
        amount = Integer.parseInt(arguments[0]);
        MedicationListener.acceptRecipe(this.unicacityAddon);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}