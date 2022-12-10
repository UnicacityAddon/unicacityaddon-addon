package com.rettichlp.unicacityaddon.commands.faction.rettungsdienst;

import com.google.inject.Inject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import com.rettichlp.unicacityaddon.events.faction.rettungsdienst.MedicationEventHandler;
import net.labymod.api.client.chat.command.Command;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand
public class ARezeptCommand extends Command {

    public static int amount = 0;
    public static DrugType medication;
    public static String target;

    private static final String usage = "/arezept [Spieler] [Rezept] [Anzahl]";

    @Inject
    private ARezeptCommand() {
        super("arezept");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (arguments.length < 3) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        target = arguments[0];
        medication = DrugType.getDrugType(arguments[1]);
        if (medication == null)
            return true;

        if (!MathUtils.isInteger(arguments[2]))
            return true;
        amount = Integer.parseInt(arguments[2]);
        MedicationEventHandler.giveRecipe();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments)
                .addAtIndex(2, Arrays.stream(DrugType.values()).filter(DrugType::isLegal).map(DrugType::getDrugName).sorted().collect(Collectors.toList()))
                .build();
    }
}