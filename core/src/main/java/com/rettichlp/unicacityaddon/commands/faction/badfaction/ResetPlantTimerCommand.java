package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "resetplanttimer", onlyOnUnicacity = false)
public class ResetPlantTimerCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public ResetPlantTimerCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        this.unicacityAddon.fileService().data().setPlantFertilizeTime(0L);
        this.unicacityAddon.fileService().data().setPlantWaterTime(0L);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}