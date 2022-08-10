package com.rettichlp.UnicacityAddon.commands.faction.badfaction;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
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
public class EigenbedarfCommand extends CommandBase {

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
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();
        Timer t = new Timer();

        if (ConfigElements.getCocainActivated()) {
            p.sendChatMessage("/dbank get Koks " + ConfigElements.getCocaineAmount() + " " + ConfigElements.getCocainDrugPurity().getPurity());

            if (ConfigElements.getMarihuanaActivated()) {
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        p.sendChatMessage("/dbank get Gras " + ConfigElements.getMarihuanaAmount() + " " + ConfigElements.getMarihuanaDrugPurity().getPurity());

                        if (ConfigElements.getMethActivated())
                            t.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    p.sendChatMessage("/dbank get Meth " + ConfigElements.getMethAmount() + " " + ConfigElements.getMethDrugPurity().getPurity());

                                }
                            }, 1000L);
                    }
                }, 1000L);
            }
            return;
        }

        if (ConfigElements.getMarihuanaActivated()) {
            p.sendChatMessage("/dbank get Marihuana " + ConfigElements.getMarihuanaAmount() + " " + ConfigElements.getMarihuanaDrugPurity().getPurity());

            if (ConfigElements.getMethActivated())
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        p.sendChatMessage("/dbank get Meth " + ConfigElements.getMethAmount() + " " + ConfigElements.getMethDrugPurity().getPurity());
                    }
                }, 1000L);

            return;
        }

        if (ConfigElements.getMethActivated())
            p.sendChatMessage("/dbank get Meth " + ConfigElements.getMethAmount() + " " + ConfigElements.getMethDrugPurity().getPurity());
    }

}
