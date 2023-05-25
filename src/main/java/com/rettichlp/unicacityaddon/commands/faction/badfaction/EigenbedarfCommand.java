package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.config.ConfigElements;
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
public class EigenbedarfCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "eigenbedarf";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/eigenbedarf";
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
        Timer t = new Timer();

        if (ConfigElements.getCocainActivated()) {
            p.sendChatMessage("/dbank get Pulver " + ConfigElements.getCocaineAmount() + " " + ConfigElements.getCocainDrugPurity().getPurity());

            if (ConfigElements.getMarihuanaActivated()) {
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        p.sendChatMessage("/dbank get Kräuter " + ConfigElements.getMarihuanaAmount() + " " + ConfigElements.getMarihuanaDrugPurity().getPurity());

                        if (ConfigElements.getMethActivated())
                            t.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    p.sendChatMessage("/dbank get Kristalle " + ConfigElements.getMethAmount() + " " + ConfigElements.getMethDrugPurity().getPurity());

                                }
                            }, 1000L);
                    }
                }, 1000L);
            }
            return;
        }

        if (ConfigElements.getMarihuanaActivated()) {
            p.sendChatMessage("/dbank get Kräuter " + ConfigElements.getMarihuanaAmount() + " " + ConfigElements.getMarihuanaDrugPurity().getPurity());

            if (ConfigElements.getMethActivated())
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        p.sendChatMessage("/dbank get Kristalle " + ConfigElements.getMethAmount() + " " + ConfigElements.getMethDrugPurity().getPurity());
                    }
                }, 1000L);

            return;
        }

        if (ConfigElements.getMethActivated())
            p.sendChatMessage("/dbank get Kristalle " + ConfigElements.getMethAmount() + " " + ConfigElements.getMethDrugPurity().getPurity());
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
