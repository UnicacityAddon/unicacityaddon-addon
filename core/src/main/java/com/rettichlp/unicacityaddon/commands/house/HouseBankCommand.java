package com.rettichlp.unicacityaddon.commands.house;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class HouseBankCommand extends UnicacityCommand {

    private static final String usage = "/hauskasseninfo";

    private final UnicacityAddon unicacityAddon;

    public HouseBankCommand(UnicacityAddon unicacityAddon) {
        super(unicacityAddon, "hauskasseninfo", true, "hkasseninfo", "hkinfo");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        if (arguments.length > 1 && arguments[0].equalsIgnoreCase("remove") && MathUtils.isInteger(arguments[1])) {
            this.unicacityAddon.services().fileService().data().removeHouseData(Integer.parseInt(arguments[1]));
            return true;
        }

        this.unicacityAddon.services().fileService().data().sendAllHouseBankMessage();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "remove")
                .build();
    }
}