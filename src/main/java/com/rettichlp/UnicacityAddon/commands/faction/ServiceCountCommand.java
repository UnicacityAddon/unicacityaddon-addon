package com.rettichlp.UnicacityAddon.commands.faction;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class ServiceCountCommand extends CommandBase {

    public static int serviceCount;

    @Override
    @Nonnull
    public String getName() {
        return "servicecount";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/servicecount (reset)";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();
        if (args.length > 0) {
            if (!args[0].equalsIgnoreCase("reset")) return;
            serviceCount = 0;
            p.sendInfoMessage("Servicecount wurde zurückgesetzt.");
            return;
        }

        Message.getBuilder().prefix().space()
                .of("Du hast bereits").color(ColorCode.GRAY).advance().space()
                .of("" + serviceCount).color(ColorCode.DARK_AQUA).advance().space()
                .of("Notrufe bearbeitet.").color(ColorCode.GRAY).advance().sendTo(p.getPlayer());
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return Arrays.asList("reset");
    }

    public static void addService() {
        serviceCount++;
    }
}