package com.rettichlp.unicacityaddon.commands.faction.rettungsdienst;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;
import com.rettichlp.unicacityaddon.listener.faction.rettungsdienst.MedicationListener;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class ARezeptAnnehmenCommand extends UnicacityCommand {

    public static int amount = 0;

    private static final String usage = "/arezeptannehmen [Anzahl]";

    private final UnicacityAddon unicacityAddon;

    public ARezeptAnnehmenCommand(UnicacityAddon unicacityAddon) {
        super(unicacityAddon, "arezeptannehmen", true, "arannehmen");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length < 1) {
            p.sendSyntaxMessage(usage);
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