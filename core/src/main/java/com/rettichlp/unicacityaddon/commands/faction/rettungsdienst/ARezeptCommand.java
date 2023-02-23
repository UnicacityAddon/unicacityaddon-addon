package com.rettichlp.unicacityaddon.commands.faction.rettungsdienst;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import com.rettichlp.unicacityaddon.listener.faction.rettungsdienst.MedicationEventHandler;
import net.labymod.api.client.chat.command.Command;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand
public class ARezeptCommand extends Command {

    public static String target;
    public static DrugType medication;
    public static int amount = 0;

    private static final String usage = "/arezept [Spieler] [Rezept] [Anzahl]";

    public ARezeptCommand() {
        super("arezept");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = UnicacityAddon.PLAYER;

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