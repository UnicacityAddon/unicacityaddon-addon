package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.config.ownUse.cocaine.Cocaine;
import com.rettichlp.unicacityaddon.base.config.ownUse.marijuana.Marijuana;
import com.rettichlp.unicacityaddon.base.config.ownUse.methamphetamin.Methamphetamin;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugPurity;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dimiikou
 * @author RettichLP
 */
@UCCommand(prefix = "eigenbedarf")
public class OwnUseCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public OwnUseCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        Cocaine cocaine = this.unicacityAddon.configuration().ownUse().cocaine();
        Marijuana marijuana = this.unicacityAddon.configuration().ownUse().marijuana();
        Methamphetamin methamphetamin = this.unicacityAddon.configuration().ownUse().methamphetamin();

        List<String> commandQueue = new ArrayList<>();

        if (cocaine.enabled().get()) {
            DrugPurity drugPurity = cocaine.purity().getOrDefault(DrugPurity.BEST);
            int amount = cocaine.amount().get();
            commandQueue.add("/dbank get " + DrugType.COCAINE.getDrugName() + " " + amount + " " + drugPurity.getPurity());
        }

        if (marijuana.enabled().get()) {
            DrugPurity drugPurity = marijuana.purity().getOrDefault(DrugPurity.GOOD);
            int amount = marijuana.amount().get();
            commandQueue.add("/dbank get " + DrugType.MARIJUANA.getDrugName() + " " + amount + " " + drugPurity.getPurity());
        }

        if (methamphetamin.enabled().get()) {
            DrugPurity drugPurity = methamphetamin.purity().getOrDefault(DrugPurity.BEST);
            int amount = methamphetamin.amount().get();
            commandQueue.add("/dbank get " + DrugType.METH.getDrugName() + " " + amount + " " + drugPurity.getPurity());
        }

        this.unicacityAddon.utilService().command().sendQueuedCommands(commandQueue);

        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}