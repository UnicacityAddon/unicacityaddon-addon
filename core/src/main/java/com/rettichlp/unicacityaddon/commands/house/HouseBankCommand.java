package com.rettichlp.unicacityaddon.commands.house;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.services.utils.MathUtils;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand(prefix = "hauskasseninfo", aliases = {"hkasseninfo", "hkinfo"})
public class HouseBankCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public HouseBankCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
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