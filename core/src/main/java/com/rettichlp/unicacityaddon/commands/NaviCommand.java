package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.api.request.APIConverter;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.models.NaviPoint;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import net.labymod.api.client.chat.command.Command;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand
public class NaviCommand extends Command {

    public NaviCommand() {
        super("navi");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = UnicacityAddon.PLAYER;
        if (arguments.length < 1) {
            return false;
        }

        if (MathUtils.isInteger(arguments[0])) {
            p.sendServerMessage("/navi Haus:" + arguments[0]);
            return true;
        }

        NaviPoint naviPoint = NaviPoint.getNaviPointByTabName(arguments[0].trim());
        if (naviPoint == null) {
            return false;
        }

        p.setNaviRoute(naviPoint.getBlockPos());
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments)
                .addAtIndex(1, APIConverter.NAVIPOINTLIST.stream().map(NaviPoint::getName).sorted().collect(Collectors.toList()))
                .build();
    }
}