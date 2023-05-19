package com.rettichlp.unicacityaddon.commands.house;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class HouseStorageCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public HouseStorageCommand(UnicacityAddon unicacityAddon) {
        super(unicacityAddon, "drogenlagerinfo", true, "dlagerinfo", "drugstorage");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        if (arguments.length > 1 && arguments[0].equalsIgnoreCase("delete") && MathUtils.isInteger(arguments[1])) {
            this.unicacityAddon.services().fileService().data().removeHouseData(Integer.parseInt(arguments[1]));
            return true;
        }

        this.unicacityAddon.services().fileService().data().sendAllDrugStorageMessage();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "delete")
                .build();
    }
}