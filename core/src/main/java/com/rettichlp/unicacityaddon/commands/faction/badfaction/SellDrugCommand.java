package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import net.labymod.api.client.chat.command.Command;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dimiikou
 */
@UCCommand
public class SellDrugCommand extends Command {

    private static final String usage = "/selldrug [Spieler] [Droge] [Reinheit] [Menge] [Preis]";

    public SellDrugCommand() {
        super("selldrug");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();
        if (arguments.length < 5) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        p.sendChatMessage("/selldrug " + arguments[0] + " " + arguments[1] + " " + arguments[2] + " " + arguments[3] + " " + arguments[4]);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments)
                .addAtIndex(2, Arrays.stream(DrugType.values()).map(DrugType::getDrugName).sorted().collect(Collectors.toList()))
                .addAtIndex(3, "0", "1", "2", "3")
                .build();
    }
}