package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.location.Job;
import com.rettichlp.UnicacityAddon.base.location.NavigationUtils;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.event.ClickEvent;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author RettichLP
 */
public class NearestJobCommand extends CommandBase {

    @Override @Nonnull public String getName() {
        return "nearestjob";
    }

    @Override @Nonnull public String getUsage(@Nonnull ICommandSender sender) {
        return "/nearestjob";
    }

    @Override @Nonnull public List<String> getAliases() {
        return Collections.singletonList("njob");
    }

    @Override public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        Map.Entry<Double, Job> nearestJob = NavigationUtils.getNearestJob();

        AbstractionLayer.getPlayer().sendMessage(Message.getBuilder()
                .prefix()
                .of("Job").color(ColorCode.GRAY).advance()
                .space()
                .of(nearestJob.getValue().getName()).color(ColorCode.AQUA).bold().advance()
                .space()
                .of("ist").color(ColorCode.GRAY).advance()
                .space()
                .of(Math.round(nearestJob.getKey()) + "m").color(ColorCode.AQUA).bold().advance()
                .space()
                .of("entfernt.").color(ColorCode.GRAY).advance()
                .space()
                .of("➡ Navi").color(ColorCode.RED).clickEvent(ClickEvent.Action.RUN_COMMAND, nearestJob.getValue().getNaviCommand()).advance()
                .createComponent());
    }
}