package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
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
 * @author Dimiikou
 */
@UCCommand
public class ReichensteuerCommand implements IClientCommand {

    public static int cashInATM = 0;
    public static boolean isActive = false;

    @Override
    @Nonnull
    public String getName() {
        return "reichensteuer";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/reichensteuer";
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
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return TabCompletionBuilder.getBuilder(args).build();
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (FileManager.DATA.getBankBalance() > 100000) {
            if (isActive)
                return;

            p.sendChatMessage("/atminfo");
            isActive = true;
            int removeMoneyAmount = FileManager.DATA.getBankBalance() - 100000;

            (new Timer()).schedule(new TimerTask() {
                @Override
                public void run() {
                    if (cashInATM < removeMoneyAmount) {
                        p.sendChatMessage("/bank abbuchen " + (cashInATM));
                        p.sendInfoMessage("Du musst noch " + (removeMoneyAmount - cashInATM) + " abbuchen.");
                        isActive = false;
                        return;
                    }
                    p.sendChatMessage("/bank abbuchen " + removeMoneyAmount);
                    isActive = false;
                }
            }, 400);

        } else {
            p.sendErrorMessage("Dein Kontostand ist bereits unter 100.001$!");
        }
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
