package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.NaviPoint;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;

import java.util.List;
import java.util.Map;

/**
 * @author Gelegenheitscode
 * @author RettichLP
 */
@UCCommand(prefix = "nearestnavipoint", aliases = {"nnavi"})
public class NearestNaviPointCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public NearestNaviPointCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        Map.Entry<Double, NaviPoint> nearestNaviPoint = this.unicacityAddon.navigationService().getNearestNaviPoint();

        this.unicacityAddon.player().sendMessage(Message.getBuilder()
                .prefix()
                .of("Navipunkt").color(ColorCode.GRAY).advance().space()
                .of(nearestNaviPoint.getValue().getName()).color(ColorCode.AQUA).bold().advance().space()
                .of("ist").color(ColorCode.GRAY).advance().space()
                .of(Math.round(nearestNaviPoint.getKey()) + "m").color(ColorCode.AQUA).bold().advance().space()
                .of("entfernt.").color(ColorCode.GRAY).advance().space()
                .of("➡ Navi").color(ColorCode.RED)
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder()
                                .of("Route anzeigen").color(ColorCode.RED).advance()
                                .createComponent())
                        .clickEvent(ClickEvent.Action.RUN_COMMAND, nearestNaviPoint.getValue().getNaviCommand())
                        .advance()
                .createComponent());
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}