package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class UpdateAddonCommand extends Command {

    private static final String usage = "/updateunicacityaddon";

    public UpdateAddonCommand() {
        super("updateunicacityaddon");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();
//        if (!UpdateUtils.latestVersion.equals(UnicacityAddon.VERSION)) {
//            if (SystemUtils.IS_OS_WINDOWS || SystemUtils.IS_OS_UNIX) {
//                UpdateUtils.update();
//            } else
//                p.sendErrorMessage("Dieser Befehl wird nur unter Windows unterstützt.");
//        } else
//            p.sendInfoMessage("Du spielst bereits mit der neusten Version.");
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}