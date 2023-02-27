package com.rettichlp.unicacityaddon.commands.faction.rettungsdienst;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import com.rettichlp.unicacityaddon.listener.faction.rettungsdienst.MedicationListener;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class ARezeptAnnehmenCommand extends Command {

    public static int amount = 0;

    private static final String usage = "/arezeptannehmen [Anzahl]";

    public ARezeptAnnehmenCommand() {
        super("arezeptannehmen", "arannehmen");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = UnicacityAddon.PLAYER;

        if (arguments.length < 1) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        if (!MathUtils.isInteger(arguments[0]))
            return true;
        amount = Integer.parseInt(arguments[0]);
        MedicationListener.acceptRecipe();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}