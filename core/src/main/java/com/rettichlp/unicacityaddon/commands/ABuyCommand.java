package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
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

    private final UnicacityAddon unicacityAddon;

    public ABuyCommand(UnicacityAddon unicacityAddon) {
        super("abuy");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

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
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}