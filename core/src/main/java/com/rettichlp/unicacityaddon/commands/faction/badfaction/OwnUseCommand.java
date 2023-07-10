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
import java.util.concurrent.TimeUnit;

/**
 * @author Dimiikou
 * @author RettichLP
 */
@UCCommand(prefix = "eigenbedarf")
public class OwnUseCommand extends UnicacityCommand {

    private static final List<String> scheduledTasks = new ArrayList<>();

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

        if (cocaine.enabled().get()) {
            DrugPurity drugPurity = cocaine.purity().getOrDefault(DrugPurity.BEST);
            int amount = cocaine.amount().getOrDefault(25);
            scheduledTasks.add("/dbank get " + DrugType.COCAINE.getDrugName() + " " + amount + " " + drugPurity.getPurity());
        }

        if (marijuana.enabled().get()) {
            DrugPurity drugPurity = marijuana.purity().getOrDefault(DrugPurity.GOOD);
            int amount = marijuana.amount().getOrDefault(25);
            scheduledTasks.add("/dbank get " + DrugType.MARIJUANA.getDrugName() + " " + amount + " " + drugPurity.getPurity());
        }

        if (methamphetamin.enabled().get()) {
            DrugPurity drugPurity = methamphetamin.purity().getOrDefault(DrugPurity.BEST);
            int amount = methamphetamin.amount().getOrDefault(5);
            scheduledTasks.add("/dbank get " + DrugType.METH.getDrugName() + " " + amount + " " + drugPurity.getPurity());
        }

        new Thread(() -> scheduledTasks.forEach(s -> {
            this.unicacityAddon.player().sendServerMessage(s);
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
            } catch (InterruptedException e) {
                this.unicacityAddon.logger().warn(e.getMessage());
            }
        })).start();

        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}