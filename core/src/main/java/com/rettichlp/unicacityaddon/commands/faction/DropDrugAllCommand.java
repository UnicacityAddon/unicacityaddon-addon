package com.rettichlp.unicacityaddon.commands.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "dbankdropall", aliases = {"dda", "asservatenkammerdropall", "ada"})
public class DropDrugAllCommand extends UnicacityCommand {

    public static boolean cocaineCheck = true;
    public static boolean marihuanaCheck = true;
    public static boolean methCheck = true;
    public static boolean active = false;
    public static int lastWindowId = 0;

    private final UnicacityAddon unicacityAddon;

    public DropDrugAllCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        this.unicacityAddon.services().fileService().data().setDrugInventoryMap(new HashMap<>());
        if (arguments.length > 0 && arguments[0].equalsIgnoreCase("reset")) {
            return true;
        }

        active = cocaineCheck = marihuanaCheck = methCheck = true;
        p.sendServerMessage("/inv");
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "reset")
                .build();
    }

    public static void dropCommandExecution(UnicacityAddon unicacityAddon) {
        AddonPlayer p = unicacityAddon.player();
        List<String> commandQueue = new ArrayList<>();

        unicacityAddon.services().fileService().data().getDrugInventoryMap().entrySet().stream()
                .filter(drugTypeMapEntry -> drugTypeMapEntry.getKey().equals(DrugType.COCAINE) || drugTypeMapEntry.getKey().equals(DrugType.MARIJUANA) || drugTypeMapEntry.getKey().equals(DrugType.METH) || drugTypeMapEntry.getKey().equals(DrugType.LSD))
                .forEach(drugTypeMapEntry -> drugTypeMapEntry.getValue().forEach((drugPurity, integer) -> {
                    if (integer > 0) {
                        String type = p.getFaction().equals(Faction.FBI) ? "asservatenkammer" : "dbank";
                        commandQueue.add("/" + type + " drop " + drugTypeMapEntry.getKey().getDrugName() + " " + integer + " " + drugPurity.getPurity());
                    }
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
    }
}