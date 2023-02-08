package com.rettichlp.unicacityaddon.commands.api;

import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.models.NaviPoint;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand
public class NaviPointCommand extends Command {

    private static final String usage = "/navipoint [add|remove] [Name] (x) (y) (z) (Artikel)";

    public NaviPointCommand() {
        super("navipoint");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (arguments.length == 6 && arguments[0].equalsIgnoreCase("add")) {
            JsonObject response = APIRequest.sendNaviPointAddRequest(arguments[1], arguments[2], arguments[3], arguments[4], arguments[5]);
            if (response == null)
                return true;
            p.sendAPIMessage(response.get("info").getAsString(), true);
        } else if (arguments.length == 2 && arguments[0].equalsIgnoreCase("remove")) {
            JsonObject response = APIRequest.sendNaviPointRemoveRequest(arguments[1]);
            if (response == null)
                return true;
            p.sendAPIMessage(response.get("info").getAsString(), true);
        } else {
            p.sendSyntaxMessage(usage);
        }
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        FloatVector3 targetPos = AbstractionLayer.getPlayer().getPosition();
        return TabCompletionBuilder.getBuilder(arguments)
                .addAtIndex(1, "add", "remove")
                .addAtIndex(2, Syncer.NAVIPOINTLIST.stream().map(NaviPoint::getName).sorted().collect(Collectors.toList()))
                .addAtIndex(3, String.valueOf(targetPos.getX())) // x
                .addAtIndex(4, String.valueOf(targetPos.getY())) // y
                .addAtIndex(5, String.valueOf(targetPos.getZ())) // z
                .addAtIndex(6, "der", "die", "das", "none")
                .build();
    }
}