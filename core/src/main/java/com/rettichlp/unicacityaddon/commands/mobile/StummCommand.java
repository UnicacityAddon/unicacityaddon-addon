package com.rettichlp.unicacityaddon.commands.mobile;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.listener.MobileListener;
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
        AddonPlayer p = UnicacityAddon.PLAYER;
        MobileListener.muted = !MobileListener.muted;

        if (MobileListener.muted)
            p.sendInfoMessage("Du hast dein Handy auf stumm gestellt.");
        else
            p.sendInfoMessage("Du hast dein Handy wieder laut gestellt.");
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}