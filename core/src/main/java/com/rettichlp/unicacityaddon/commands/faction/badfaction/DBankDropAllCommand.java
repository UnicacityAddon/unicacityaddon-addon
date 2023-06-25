package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "dbankdropall")
public class DBankDropAllCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public DBankDropAllCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        // reset drug inventory tracker
        if (arguments.length > 0 && arguments[0].equalsIgnoreCase("reset")) {
            this.unicacityAddon.services().file().data().setDrugInventoryMap(new HashMap<>());
            return true;
        }

        List<String> commandQueue = new ArrayList<>();
        this.unicacityAddon.services().file().data().getDrugInventoryMap()
                .forEach((drugType, drugPurityIntegerMap) -> drugPurityIntegerMap
                        .forEach((drugPurity, integer) -> {
                            if (integer > 0)
                                commandQueue.add("/dbank drop " + drugType.getDrugName() + " " + integer + " " + drugPurity.getPurity());
                        }));

        this.unicacityAddon.services().util().command().sendQueuedCommands(commandQueue);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "reset")
                .build();
    }
}