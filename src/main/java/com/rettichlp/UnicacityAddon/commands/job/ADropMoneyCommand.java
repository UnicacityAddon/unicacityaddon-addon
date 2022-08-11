package com.rettichlp.UnicacityAddon.commands.job;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Dimiikou
 */
public class ADropMoneyCommand extends CommandBase {

    public static boolean execute = false;

    @Override
    @Nonnull
    public String getName() {
        return "adropmoney";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/adropmoney";
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
        Timer t = new Timer();

        execute = true;
        p.sendChatMessage("/bank abbuchen 15000");

        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if (execute) {
                    p.sendChatMessage("/bank einzahlen 15000");
                    p.sendChatMessage("/dropmoney");
                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            execute = false;
                        }
                    }, 200L);

                }
            }
        }, 1500L);
    }
}