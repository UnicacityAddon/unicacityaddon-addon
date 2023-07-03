package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.NaviPoint;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.List;
import java.util.Map;

@UCCommand(prefix = "nearestnavipoint", aliases = {"nnavi"})
public class NearestNavipointCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public NearestNavipointCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {

        AddonPlayer p = this.unicacityAddon.player();

        int posX = (int) p.getLocation().getX();
        int posY = (int) p.getLocation().getY();
        int posZ = (int) p.getLocation().getZ();

        Map.Entry<Double, NaviPoint> nearestNaviPoint = this.unicacityAddon.navigationService().getNearestNaviPoint(posX, posY, posZ);
        NaviPoint navipoint = nearestNaviPoint.getValue();

        if (navipoint != null) {
            this.unicacityAddon.player().sendMessage(Message.getBuilder()
                    .prefix()
                    .of("Der Navipunkt").color(ColorCode.GRAY).advance().space()
                    .of(navipoint.getName()).color(ColorCode.AQUA).bold().advance().space()
                    .of("ist").color(ColorCode.GRAY).advance().space()
                    .of((int) navipoint.getLocation().distance(new FloatVector3(posX, posY, posZ)) + "m").color(ColorCode.AQUA).bold().advance().space()
                    .of("entfernt.").color(ColorCode.GRAY).advance().space()
                    .of("➡ Navi").color(ColorCode.RED)
                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder()
                            .of("Route anzeigen").color(ColorCode.RED).advance()
                            .createComponent())
                    .clickEvent(ClickEvent.Action.RUN_COMMAND, "/navi " + navipoint.getName())
                    .advance()
                    .createComponent());
        } else {
            p.sendErrorMessage("Die Navipunkte konnten nicht geladen werden!");
            p.sendInfoMessage("Versuche /sync um diese neu zu laden!");
        }
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}
