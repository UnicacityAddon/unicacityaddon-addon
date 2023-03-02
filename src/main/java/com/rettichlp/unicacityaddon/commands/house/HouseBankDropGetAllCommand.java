package com.rettichlp.unicacityaddon.commands.house;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import com.rettichlp.unicacityaddon.events.house.HouseDataEventHandler;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author RettichLP
 */
@UCCommand
public class HouseBankDropGetAllCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "hauskasse";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/hauskasse";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return Collections.singletonList("hkasse");
    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return TabCompletionBuilder.getBuilder(args)
                .addAtIndex(1, "get", "drop", "info")
                .addAtIndex(2, "all")
                .build();
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();
        if (args.length > 1 && args[1].equalsIgnoreCase("all")) {
            p.sendChatMessage("/hauskasse");
            HouseDataEventHandler.lastCheck = System.currentTimeMillis();
            if (args[0].equalsIgnoreCase("get")) {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        int houseBankBalance = FileManager.DATA.getHouseData(HouseDataEventHandler.lastCheckedHouseNumber).getHouseBank();
                        if (houseBankBalance > 0) {
                            p.sendChatMessage("/hauskasse get " + houseBankBalance);
                        } else {
                            p.sendErrorMessage("Deine Hauskasse ist leer.");
                        }
                    }
                }, 1000);
                return;
            } else if (args[0].equalsIgnoreCase("drop")) {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        int houseBankBalance = FileManager.DATA.getHouseData(HouseDataEventHandler.lastCheckedHouseNumber).getHouseBank();
                        int toTransfer = Math.min(15000 - houseBankBalance, FileManager.DATA.getCashBalance());
                        if (toTransfer > 0) {
                            p.sendChatMessage("/hauskasse drop " + toTransfer);
                        } else {
                            p.sendErrorMessage("Deine Hauskasse ist voll oder du hast kein Geld auf der Hand.");
                        }
                    }
                }, 1000);
                return;
            }
            return;
        }

        p.sendChatMessage("/hauskasse" + (args.length > 0 ? " " + TextUtils.makeStringByArgs(args, " ") : ""));
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