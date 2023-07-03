package com.rettichlp.unicacityaddon.commands.api;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.NaviPoint;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "navipoint", usage = "[add|remove] [Name] (x) (y) (z) (der|die|das)")
public class NaviPointCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public NaviPointCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        new Thread(() -> {
            if (arguments.length == 6 && arguments[0].equalsIgnoreCase("add")) {
                String info = this.unicacityAddon.api().sendNaviPointAddRequest(arguments[1], arguments[2], arguments[3], arguments[4], arguments[5]).getInfo();
                p.sendAPIMessage(info, true);
            } else if (arguments.length == 2 && arguments[0].equalsIgnoreCase("remove")) {
                String info = this.unicacityAddon.api().sendNaviPointRemoveRequest(arguments[1]).getInfo();
                p.sendAPIMessage(info, true);
            } else {
                sendUsage();
            }
        }).start();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        FloatVector3 location = this.unicacityAddon.player().getLocation();
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "add", "remove")
                .addAtIndex(2, this.unicacityAddon.api().getNaviPointList().stream().map(NaviPoint::getName).sorted().collect(Collectors.toList()))
                .addAtIndex(3, String.valueOf(location != null ? location.getX() : "")) // x
                .addAtIndex(4, String.valueOf(location != null ? location.getY() : "")) // y
                .addAtIndex(5, String.valueOf(location != null ? location.getZ() : "")) // z
                .addAtIndex(6, "der", "die", "das", "none")
                .build();
    }
}