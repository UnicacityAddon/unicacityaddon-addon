package com.rettichlp.unicacityaddon.commands;

import com.google.inject.Inject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class ABuyCommand extends Command {

    public static int amount = 0;
    public static int delay = 200;

    private static final String usage = "/abuy [Menge] (Delay)";

    @Inject
    private ABuyCommand() {
        super("abuy");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (arguments.length == 1) {
            if (!MathUtils.isInteger(arguments[0])) {
                p.sendSyntaxMessage(usage);
                return true;
            }
            amount = Integer.parseInt(arguments[0]);
        } else if (arguments.length > 1) {
            if (!MathUtils.isInteger(arguments[0]) || !MathUtils.isInteger(arguments[1])) {
                p.sendSyntaxMessage(usage);
                return true;
            }
            amount = Integer.parseInt(arguments[0]);
            delay = Integer.parseInt(arguments[1]);
        }

        p.sendInfoMessage("Menge für ABuy wurde eingestellt.");
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}