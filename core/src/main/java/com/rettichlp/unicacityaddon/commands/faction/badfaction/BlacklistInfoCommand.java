package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand(prefix = "blacklistinfo", aliases = {"blinfo"}, usage = "[Spieler]")
public class BlacklistInfoCommand extends UnicacityCommand {

    public static long executedTime = -1;
    public static String target;

    private final UnicacityAddon unicacityAddon;

    public BlacklistInfoCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length != 1) {
            sendUsage();
            return true;
        }

        executedTime = System.currentTimeMillis();
        target = arguments[0];

        p.sendServerMessage("/bl");
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}