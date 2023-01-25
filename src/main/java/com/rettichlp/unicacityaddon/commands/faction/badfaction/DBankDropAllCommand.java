package com.rettichlp.unicacityaddon.commands.faction.badfaction;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author RettichLP
 */
@UCCommand
public class DBankDropAllCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "dbankdropall";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/dbankdropall";
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

        // reset drug inventory tracker
        if (args.length > 0 && args[0].equalsIgnoreCase("reset")) {
            FileManager.DATA.setDrugInventoryMap(new HashMap<>());
            return;
        }

        List<String> commandQueue = new ArrayList<>();
        FileManager.DATA.getDrugInventoryMap()
                .forEach((drugType, drugPurityIntegerMap) -> drugPurityIntegerMap
                        .forEach((drugPurity, integer) -> {
                            if (integer > 0)
                                commandQueue.add("/dbank drop " + drugType.getShortName() + " " + integer + " " + drugPurity.getPurity());
                        }));

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (commandQueue.isEmpty()) {
                    timer.cancel();
                } else {
                    p.sendChatMessage(commandQueue.get(0));
                    commandQueue.remove(0);
                }
            }
        }, 0, TimeUnit.SECONDS.toMillis(1));
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return TabCompletionBuilder.getBuilder(args)
                .addAtIndex(1, "reset")
                .build();
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
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
