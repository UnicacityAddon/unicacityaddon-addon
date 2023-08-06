package com.rettichlp.unicacityaddon.commands.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.listener.DrugListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "selldrugall", aliases = {"selldrugs", "sda"}, usage = "[Spieler]")
public class SellDrugAllCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public SellDrugAllCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        if (arguments.length < 1) {
            sendUsage();
            return true;
        }

        this.unicacityAddon.utilService().command().loadDrugInventory(() -> {
            DrugListener.dealCommandQueue = new ArrayList<>();

            this.unicacityAddon.fileService().data().getDrugInventoryMap().entrySet().stream()
                    .filter(drugTypeMapEntry -> drugTypeMapEntry.getKey().equals(DrugType.COCAINE) || drugTypeMapEntry.getKey().equals(DrugType.MARIJUANA) || drugTypeMapEntry.getKey().equals(DrugType.METH) || drugTypeMapEntry.getKey().equals(DrugType.LSD))
                    .forEach(drugTypeMapEntry -> drugTypeMapEntry.getValue().forEach((drugPurity, integer) -> {
                        if (integer > 0) {
                            DrugListener.dealCommandQueue.add("/selldrug " + arguments[0] + " " + drugTypeMapEntry.getKey().getDrugName() + " " + drugPurity.getPurity() + " " + integer + " 0");
                        }
                    }));

            if (!DrugListener.dealCommandQueue.isEmpty()) {
                this.unicacityAddon.player().sendServerMessage(DrugListener.dealCommandQueue.remove(0));
            }
        });

        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}