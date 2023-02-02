package com.rettichlp.unicacityaddon.commands.faction;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class AEquipCommand extends Command {

    public static int amount = 0;

    private static final String usage = "/aequip [Menge]";

    public AEquipCommand() {
        super("aequip");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (arguments.length > 0) {
            if (!MathUtils.isInteger(arguments[0])) {
                p.sendSyntaxMessage(usage);
                return true;
            }
            amount = Integer.parseInt(arguments[0]);
        }

        p.sendInfoMessage("Menge für AEquip wurde eingestellt.");
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}