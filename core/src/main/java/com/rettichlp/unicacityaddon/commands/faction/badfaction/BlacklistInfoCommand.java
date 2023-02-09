package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class BlacklistInfoCommand extends Command {

    public static long executedTime = -1;
    public static String target;

    private static final String usage = "/blacklistinfo [Spieler]";

    public BlacklistInfoCommand() {
        super("blacklistinfo", "blinfo");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = UnicacityAddon.PLAYER;

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
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}