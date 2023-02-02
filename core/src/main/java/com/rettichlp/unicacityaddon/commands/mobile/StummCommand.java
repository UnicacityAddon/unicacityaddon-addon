package com.rettichlp.unicacityaddon.commands.mobile;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.events.MobileEventHandler;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class StummCommand extends Command {

    private static final String usage = "/stumm";

    public StummCommand() {
        super("stumm", "nichtstören", "donotdisturb");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();
        MobileEventHandler.muted = !MobileEventHandler.muted;

        if (MobileEventHandler.muted)
            p.sendInfoMessage("Du hast den Handy auf stumm gestellt.");
        else
            p.sendInfoMessage("Du hast dein Handy wieder laut gestellt.");
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}