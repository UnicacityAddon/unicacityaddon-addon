package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.NaviPoint;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.services.utils.MathUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.rettichlp.unicacityaddon.base.io.api.API.find;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "navi")
public class NaviCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public NaviCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();
        if (arguments.length < 1) {
            return false;
        }

        if (MathUtils.isInteger(arguments[0])) {
            p.sendServerMessage("/navi Haus:" + arguments[0]);
            return true;
        }

        NaviPoint naviPoint = find(this.unicacityAddon.api().getNaviPointList(), n -> n.getName().equalsIgnoreCase(arguments[0]));
        if (naviPoint == null) {
            return false;
        }

        p.setNaviRoute(naviPoint.getLocation());
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, this.unicacityAddon.api().getNaviPointList().stream().map(NaviPoint::getName).sorted().collect(Collectors.toList()))
                .build();
    }
}