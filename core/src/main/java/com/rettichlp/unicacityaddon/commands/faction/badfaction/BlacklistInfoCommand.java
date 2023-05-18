package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class BlacklistInfoCommand extends UnicacityCommand {

    public static long executedTime = -1;
    public static String target;

    private static final String usage = "/blacklistinfo [Spieler]";

    private final UnicacityAddon unicacityAddon;

    public BlacklistInfoCommand(UnicacityAddon unicacityAddon) {
        super(unicacityAddon, "blacklistinfo", true, "blinfo");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length != 1) {
            p.sendSyntaxMessage(usage);
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