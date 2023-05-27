package com.rettichlp.unicacityaddon.commands.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "aequip", usage = "[Anzahl]")
public class AEquipCommand extends UnicacityCommand {

    public static int amount = 0;

    private final UnicacityAddon unicacityAddon;

    public AEquipCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length > 0) {
            if (!MathUtils.isInteger(arguments[0])) {
                sendUsage(p);
                return true;
            }
            amount = Integer.parseInt(arguments[0]);
        }

        p.sendInfoMessage("Menge für AEquip wurde eingestellt.");
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}