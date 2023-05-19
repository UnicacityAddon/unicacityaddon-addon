package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dimiikou
 * @author RettichLP
 */
@UCCommand(prefix = "selldrug", usage = "[Spieler] [Droge] [Reinheit] [Menge] [Preis]")
public class SellDrugCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public SellDrugCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length < 5) {
            sendUsage(p);
            return true;
        }

        // command is only used to add tab completion
        return false;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(2, Arrays.stream(DrugType.values()).map(DrugType::getDrugName).sorted().collect(Collectors.toList()))
                .addAtIndex(3, "0", "1", "2", "3")
                .build();
    }
}