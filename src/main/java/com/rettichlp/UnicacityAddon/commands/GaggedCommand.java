package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
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
        if (GaggedEventHandler.getGagged())
            Message.getBuilder().prefix().of("Ab sofort kannst du nurnoch flüstern.").color(ColorCode.GRAY).advance()
                    .sendTo(AbstractionLayer.getPlayer().getPlayer());
        if (!GaggedEventHandler.getGagged())
            Message.getBuilder().prefix().of("Ab sofort kannst wieder normal reden.").color(ColorCode.GRAY).advance()
                    .sendTo(AbstractionLayer.getPlayer().getPlayer());

        GaggedEventHandler.changeGaggedState();
    }
}
