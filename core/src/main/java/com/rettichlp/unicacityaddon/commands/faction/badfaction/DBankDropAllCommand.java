package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import net.labymod.api.client.chat.command.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author RettichLP
 */
@UCCommand
public class DBankDropAllCommand extends Command {

    private final UnicacityAddon unicacityAddon;

    public DBankDropAllCommand(UnicacityAddon unicacityAddon) {
        super("dbankdropall");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        // reset drug inventory tracker
        if (arguments.length > 0 && arguments[0].equalsIgnoreCase("reset")) {
            this.unicacityAddon.fileService().data().setDrugInventoryMap(new HashMap<>());
            return true;
        }

        List<String> commandQueue = new ArrayList<>();
        this.unicacityAddon.fileService().data().getDrugInventoryMap()
                .forEach((drugType, drugPurityIntegerMap) -> drugPurityIntegerMap
                        .forEach((drugPurity, integer) -> {
                            if (integer > 0)
                                commandQueue.add("/dbank drop " + drugType.getShortName() + " " + integer + " " + drugPurity.getPurity());
                        }));

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (commandQueue.isEmpty()) {
                    timer.cancel();
                } else {
                    p.sendServerMessage(commandQueue.get(0));
                    commandQueue.remove(0);
                }
            }
        }, 0, TimeUnit.SECONDS.toMillis(1));

        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "reset")
                .build();
    }
}