package com.rettichlp.unicacityaddon.commands.house;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class HouseBankCommand extends Command {

    private static final String usage = "/hauskasseninfo";

    private UnicacityAddon unicacityAddon;

    public HouseBankCommand(UnicacityAddon unicacityAddon) {
        super("hauskasseninfo", "hkasseninfo", "hkinfo");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        if (arguments.length > 1 && arguments[0].equalsIgnoreCase("remove") && MathUtils.isInteger(arguments[1])) {
            this.unicacityAddon.fileManager().data().removeHouseData(Integer.parseInt(arguments[1]));
            return true;
        }

        this.unicacityAddon.fileManager().data().sendAllHouseBankMessage();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "remove")
                .build();
    }
}