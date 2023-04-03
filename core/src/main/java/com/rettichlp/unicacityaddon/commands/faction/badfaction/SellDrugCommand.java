package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
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

    private final UnicacityAddon unicacityAddon;

    public SellDrugCommand(UnicacityAddon unicacityAddon) {
        super("selldrug");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();
        if (arguments.length < 5) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        p.sendServerMessage("/selldrug " + arguments[0] + " " + arguments[1] + " " + arguments[2] + " " + arguments[3] + " " + arguments[4]);
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