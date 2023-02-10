package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.api.request.APIConverter;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.models.NaviPoint;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
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
            p.sendServerMessage("/navi");
            return true;
        }

        if (MathUtils.isInteger(arguments[0])) {
            p.sendServerMessage("/navi Haus:" + arguments[0]);
            return true;
        }

        NaviPoint naviPoint = NaviPoint.getNaviPointEntryByTabName(arguments[0].trim());
        if (naviPoint == null) {
            p.sendServerMessage("/navi " + TextUtils.makeStringByArgs(arguments, " "));
            return true;
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