package com.rettichlp.unicacityaddon.commands;

import com.google.inject.Inject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.models.NaviPointEntry;
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

    private static final String usage = "/navi";

    @Inject
    private NaviCommand() {
        super("navi");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();
        if (arguments.length < 1) {
            p.sendChatMessage("/navi");
            return true;
        }

        if (MathUtils.isInteger(arguments[0])) {
            p.sendChatMessage("/navi Haus:" + arguments[0]);
            return true;
        }

        NaviPointEntry naviPointEntry = NaviPointEntry.getNaviPointEntryByTabName(arguments[0].trim());
        if (naviPointEntry == null) {
            p.sendChatMessage("/navi " + TextUtils.makeStringByArgs(arguments, " "));
            return true;
        }

        p.setNaviRoute(naviPointEntry.getFloatVector3());
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments)
                .addAtIndex(1, Syncer.NAVIPOINTLIST.stream().map(NaviPointEntry::getName).sorted().collect(Collectors.toList()))
                .build();
    }
}