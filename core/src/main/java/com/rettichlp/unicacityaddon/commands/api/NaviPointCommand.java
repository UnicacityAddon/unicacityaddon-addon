package com.rettichlp.unicacityaddon.commands.api;

import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.api.exception.APIResponseException;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.models.NaviPoint;
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

    private final UnicacityAddon unicacityAddon;

    public NaviPointCommand(UnicacityAddon unicacityAddon) {
        super("navipoint");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        new Thread(() -> {
            if (arguments.length == 6 && arguments[0].equalsIgnoreCase("add")) {
                try {
                    JsonObject response = this.unicacityAddon.api().sendNaviPointAddRequest(arguments[1], arguments[2], arguments[3], arguments[4], arguments[5]);
                    p.sendAPIMessage(response.get("info").getAsString(), true);
                } catch (APIResponseException e) {
                    e.sendInfo();
                }
            } else if (arguments.length == 2 && arguments[0].equalsIgnoreCase("remove")) {
                try {
                    JsonObject response = this.unicacityAddon.api().sendNaviPointRemoveRequest(arguments[1]);
                    p.sendAPIMessage(response.get("info").getAsString(), true);
                } catch (APIResponseException e) {
                    e.sendInfo();
                }
            } else {
                p.sendSyntaxMessage(usage);
            }
        }).start();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        FloatVector3 targetPos = this.unicacityAddon.player().getPosition();
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "add", "remove")
                .addAtIndex(2, this.unicacityAddon.api().getNaviPointList().stream().map(NaviPoint::getName).sorted().collect(Collectors.toList()))
                .addAtIndex(3, String.valueOf(targetPos.getX())) // x
                .addAtIndex(4, String.valueOf(targetPos.getY())) // y
                .addAtIndex(5, String.valueOf(targetPos.getZ())) // z
                .addAtIndex(6, "der", "die", "das", "none")
                .build();
    }
}