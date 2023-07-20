package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugPurity;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.services.utils.MathUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "aselldrug", usage = "[Spieler] [Droge] [Reinheit] [Menge]")
public class ASellDrugCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public ASellDrugCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length < 4 || !MathUtils.isInteger(arguments[2])) {
            sendUsage();
            return true;
        }

        DrugType drugType = DrugType.getDrugType(arguments[1]);
        if (drugType == null) {
            sendUsage();
            return true;
        }

        DrugPurity drugPurity = DrugPurity.getDrugPurity(arguments[2]);

        int price = switch (drugType) {
            case COCAINE -> switch (drugPurity) {
                case BEST -> this.unicacityAddon.configuration().drug().cocaine().best().get();
                case GOOD -> this.unicacityAddon.configuration().drug().cocaine().good().get();
                case MEDIUM -> this.unicacityAddon.configuration().drug().cocaine().medium().get();
                case BAD -> this.unicacityAddon.configuration().drug().cocaine().bad().get();
            };
            case MARIJUANA -> switch (drugPurity) {
                case BEST -> this.unicacityAddon.configuration().drug().marijuana().best().get();
                case GOOD -> this.unicacityAddon.configuration().drug().marijuana().good().get();
                case MEDIUM -> this.unicacityAddon.configuration().drug().marijuana().medium().get();
                case BAD -> this.unicacityAddon.configuration().drug().marijuana().bad().get();
            };
            case METH -> switch (drugPurity) {
                case BEST -> this.unicacityAddon.configuration().drug().methamphetamin().best().get();
                case GOOD -> this.unicacityAddon.configuration().drug().methamphetamin().good().get();
                case MEDIUM -> this.unicacityAddon.configuration().drug().methamphetamin().medium().get();
                case BAD -> this.unicacityAddon.configuration().drug().methamphetamin().bad().get();
            };
            case LSD, IRON, MASK, GUNPOWDER, ANTIBIOTIKA, HUSTENSAFT, SCHMERZMITTEL -> 0;
        };

        p.sendServerMessage("/selldrug " + arguments[0] + " " + arguments[1] + " " + arguments[2] + " " + arguments[3] + " " + price);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(2, Arrays.stream(DrugType.values()).map(DrugType::getDrugName).sorted().collect(Collectors.toList()))
                .addAtIndex(3, "0", "1", "2", "3")
                .build();
    }
}