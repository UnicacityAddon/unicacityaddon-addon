package com.rettichlp.unicacityaddon.commands.house;

import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class HouseBankCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "hauskasseninfo";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/hauskasseninfo";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return Arrays.asList("hkasseninfo", "hkinfo");
    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return TabCompletionBuilder.getBuilder(args)
                .addAtIndex(1, "remove")
                .build();
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        if (args.length > 1 && args[0].equalsIgnoreCase("remove") && MathUtils.isInteger(args[1])) {
            FileManager.DATA.removeHouseData(Integer.parseInt(args[1]));
            return;
        }

        FileManager.DATA.sendAllHouseBankMessage();
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