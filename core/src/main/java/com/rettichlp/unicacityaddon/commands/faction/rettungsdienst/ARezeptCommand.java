package com.rettichlp.unicacityaddon.commands.faction.rettungsdienst;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.services.utils.MathUtils;
import com.rettichlp.unicacityaddon.listener.faction.rettungsdienst.MedicationListener;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "arezept", usage = "[Spieler] [Rezept] [Anzahl]")
public class ARezeptCommand extends UnicacityCommand {

    public static String target;
    public static DrugType medication;
    public static int amount = 0;

    private final UnicacityAddon unicacityAddon;

    public ARezeptCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        if (arguments.length < 3) {
            sendUsage();
            return true;
        }

        target = arguments[0];
        medication = DrugType.getDrugType(arguments[1]);
        if (medication == null)
            return true;

        if (!MathUtils.isInteger(arguments[2]))
            return true;
        amount = Integer.parseInt(arguments[2]);
        MedicationListener.giveRecipe(this.unicacityAddon);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(2, Arrays.stream(DrugType.values()).filter(DrugType::isLegal).map(DrugType::getDrugName).sorted().collect(Collectors.toList()))
                .build();
    }
}