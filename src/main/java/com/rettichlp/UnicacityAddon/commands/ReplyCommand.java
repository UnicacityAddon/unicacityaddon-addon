package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
import com.rettichlp.UnicacityAddon.base.utils.TextUtils;
import com.rettichlp.UnicacityAddon.events.MobileEventHandler;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class ReplyCommand implements IClientCommand {

    @Override @Nonnull
    public String getName() {
        return "reply";
    }

    @Override @Nonnull public String getUsage(@Nonnull ICommandSender sender) {
        return "/reply [Nachricht]";
    }

    @Override @Nonnull public List<String> getAliases() {
        return Collections.singletonList("r");
    }

    @Override public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return ForgeUtils.getOnlinePlayers();
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
    }

    @Override public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();
        if (args.length < 2) {
            p.sendSyntaxMessage(getUsage(sender));
            return;
        }

        String message = TextUtils.makeStringByArgs(args, " ").replace(args[0], "");
        p.sendChatMessage("/sms " + MobileEventHandler.lastCheckedNumber + " " + message);
    }

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public int compareTo(@Nonnull ICommand o) {
        return 0;
    }

}
