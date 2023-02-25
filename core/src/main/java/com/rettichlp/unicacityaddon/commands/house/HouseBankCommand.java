package com.rettichlp.unicacityaddon.commands.house;

import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
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

    public HouseBankCommand() {
        super("hauskasseninfo", "hkasseninfo", "hkinfo");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        if (arguments.length > 1 && arguments[0].equalsIgnoreCase("remove") && MathUtils.isInteger(arguments[1])) {
            FileManager.DATA.removeHouseData(Integer.parseInt(arguments[1]));
            return true;
        }

        FileManager.DATA.sendAllHouseBankMessage();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments)
                .addAtIndex(1, "remove")
                .build();
    }
}