package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.config.ownUse.cocaine.Cocaine;
import com.rettichlp.unicacityaddon.base.config.ownUse.marijuana.Marijuana;
import com.rettichlp.unicacityaddon.base.config.ownUse.methamphetamin.Methamphetamin;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugPurity;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.listener.DrugListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dimiikou
 * @author RettichLP
 */
@UCCommand(prefix = "gifteigenbedarf", usage = "[Spieler]")
public class OwnUseGiftCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public OwnUseGiftCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length < 1) {
            sendUsage();
            return true;
        }

        Cocaine cocaine = this.unicacityAddon.configuration().ownUse().cocaine();
        Marijuana marijuana = this.unicacityAddon.configuration().ownUse().marijuana();
        Methamphetamin methamphetamin = this.unicacityAddon.configuration().ownUse().methamphetamin();

        DrugListener.dealCommandQueue = new ArrayList<>();

        if (cocaine.enabled().get()) {
            DrugPurity drugPurity = cocaine.purity().getOrDefault(DrugPurity.BEST);
            int amount = cocaine.amount().get();
            DrugListener.dealCommandQueue.add("/selldrug " + arguments[0] + " " + DrugType.COCAINE.getDrugName() + " " + drugPurity.getPurity() + " " + amount + " 0");
        }

        if (marijuana.enabled().get()) {
            DrugPurity drugPurity = marijuana.purity().getOrDefault(DrugPurity.GOOD);
            int amount = marijuana.amount().get();
            DrugListener.dealCommandQueue.add("/selldrug " + arguments[0] + " " + DrugType.MARIJUANA.getDrugName() + " " + drugPurity.getPurity() + " " + amount + " 0");
        }

        if (methamphetamin.enabled().get()) {
            DrugPurity drugPurity = methamphetamin.purity().getOrDefault(DrugPurity.BEST);
            int amount = methamphetamin.amount().get();
            DrugListener.dealCommandQueue.add("/selldrug " + arguments[0] + " " + DrugType.METH.getDrugName() + " " + drugPurity.getPurity() + " " + amount + " 0");
        }

        if (!DrugListener.dealCommandQueue.isEmpty()) {
            p.sendServerMessage(DrugListener.dealCommandQueue.remove(0));
        }

        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}