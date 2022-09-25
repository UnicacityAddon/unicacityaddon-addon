package com.rettichlp.UnicacityAddon.commands.faction.badfaction;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.events.GaggedEventHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class GaggedCommand extends CommandBase {

    @Override @Nonnull
    public String getName() {
        return "geknebelt";
    }

    @Override @Nonnull public String getUsage(@Nonnull ICommandSender sender) {
        return "/geknebelt";
    }

    @Override @Nonnull public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();
        GaggedEventHandler.toggleGagged();

        if (GaggedEventHandler.isGagged()) p.sendInfoMessage("Ab sofort kannst du nur noch flüstern.");
        else p.sendInfoMessage("Ab sofort kannst du wieder normal reden.");
    }
}
