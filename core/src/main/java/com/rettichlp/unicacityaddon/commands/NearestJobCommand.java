package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.location.Job;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;

import java.util.List;
import java.util.Map;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "nearestjob", aliases = {"njob"})
public class NearestJobCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public NearestJobCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        Map.Entry<Double, Job> nearestJob = this.unicacityAddon.navigationService().getNearestJob();

        this.unicacityAddon.player().sendMessage(Message.getBuilder()
                .prefix()
                .of("Job").color(ColorCode.GRAY).advance().space()
                .of(nearestJob.getValue().getName()).color(ColorCode.AQUA).bold().advance().space()
                .of("ist").color(ColorCode.GRAY).advance().space()
                .of(Math.round(nearestJob.getKey()) + "m").color(ColorCode.AQUA).bold().advance().space()
                .of("entfernt.").color(ColorCode.GRAY).advance().space()
                .of("➡ Navi").color(ColorCode.RED)
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder()
                                .of("Route anzeigen").color(ColorCode.RED).advance()
                                .createComponent())
                        .clickEvent(ClickEvent.Action.RUN_COMMAND, nearestJob.getValue().getNaviCommand())
                        .advance()
                .createComponent());
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}