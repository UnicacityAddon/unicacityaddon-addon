package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.utils.MathUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * @author RettichLP
 */
public class ABuyCommand extends CommandBase {

    public static int amount = 0;
    public static int delay = 200;

    @Override @Nonnull public String getName() {
        return "abuy";
    }

    @Override @Nonnull public String getUsage(@Nonnull ICommandSender sender) {
        return "/abuy [Menge] (Delay)";
    }

    @Override @Nonnull public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (args.length == 1) {
            if (!MathUtils.isInteger(args[0])) {
                p.sendSyntaxMessage(getUsage(sender));
                return;
            }
            amount = Integer.parseInt(args[0]);
        } else if (args.length > 1) {
            if (!MathUtils.isInteger(args[0]) || !MathUtils.isInteger(args[1])) {
                p.sendSyntaxMessage(getUsage(sender));
                return;
            }
            amount = Integer.parseInt(args[0]);
            delay = Integer.parseInt(args[1]);
        }

        p.sendInfoMessage("Menge für ABuy wurde eingestellt.");
    }
}