package com.rettichlp.unicacityaddon.commands.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "dbankdropall", aliases = {"dda", "asservatenkammerdropall", "ada"})
public class DropDrugAllCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public DropDrugAllCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        this.unicacityAddon.utilService().command().loadDrugInventory(() -> {
            List<String> commandQueue = new ArrayList<>();

            this.unicacityAddon.fileService().data().getDrugInventoryMap().entrySet().stream()
                    .filter(drugTypeMapEntry -> drugTypeMapEntry.getKey().equals(DrugType.COCAINE) || drugTypeMapEntry.getKey().equals(DrugType.MARIJUANA) || drugTypeMapEntry.getKey().equals(DrugType.METH) || drugTypeMapEntry.getKey().equals(DrugType.LSD))
                    .forEach(drugTypeMapEntry -> drugTypeMapEntry.getValue().forEach((drugPurity, integer) -> {
                        if (integer > 0) {
                            String type = this.unicacityAddon.player().getFaction().equals(Faction.FBI) ? "asservatenkammer" : "dbank";
                            commandQueue.add("/" + type + " drop " + drugTypeMapEntry.getKey().getDrugName() + " " + integer + " " + drugPurity.getPurity());
                        }
                    }));

            this.unicacityAddon.utilService().command().sendQueuedCommands(commandQueue);
        });

        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "reset")
                .build();
    }
}