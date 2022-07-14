package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.command.UCCommand;
import com.rettichlp.UnicacityAddon.base.command.UnicacityCommand;
import com.rettichlp.UnicacityAddon.base.location.Job;
import com.rettichlp.UnicacityAddon.base.location.NavigationUtils;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import net.minecraft.util.text.event.ClickEvent;

import java.util.List;
import java.util.Map;

public class NearestJobCommand implements UnicacityCommand {

    @Override
    @UCCommand(value = "nearestjob", usage = "/%label%")
    public boolean onCommand(UPlayer p, List<String> args) {
        Map.Entry<Double, Job> nearestJob = NavigationUtils.getNearestJob();

        p.sendMessage(Message.getBuilder()
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

        return true;
    }
}