package com.rettichlp.UnicacityAddon.commands.job;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
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
@UCCommand
public class ADropMoneyCommand extends CommandBase {

    private int step = 0;

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

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                switch (step++) {
                    case 1:
                        p.sendChatMessage("/bank abbuchen 15000");
                        break;
                    case 2:
                        p.sendChatMessage("/dropmoney");
                        break;
                    case 3:
                        p.sendChatMessage("/bank einzahlen 15000");
                        step = 0;
                        this.cancel();
                        break;
                }
            }
        }, 0, 1000);
    }
}