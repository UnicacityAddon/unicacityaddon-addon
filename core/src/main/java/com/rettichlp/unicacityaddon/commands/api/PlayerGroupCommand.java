package com.rettichlp.unicacityaddon.commands.api;

import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class PlayerGroupCommand extends Command {

    private static final String usage = "/playergroup [add|remove] [Spieler] [Gruppe]";

    public PlayerGroupCommand() {
        super("playergroup");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (arguments.length == 2 && arguments[0].equalsIgnoreCase("list")) {
            p.sendEmptyMessage();
            p.sendMessage(Message.getBuilder()
                    .of("Spielergruppe:").color(ColorCode.DARK_AQUA).bold().advance().space()
                    .of(arguments[1]).color(ColorCode.DARK_AQUA).advance()
                    .createComponent());

            Syncer.getPlayerGroupEntryList(arguments[1]).forEach(playerGroupEntry -> p.sendMessage(Message.getBuilder()
                    .of("»").color(ColorCode.GRAY).advance().space()
                    .of(playerGroupEntry.getName()).color(ColorCode.AQUA).advance()
                    .createComponent()));

            p.sendEmptyMessage();

        } else if (arguments.length == 3 && arguments[0].equalsIgnoreCase("add")) {
            JsonObject response = APIRequest.sendPlayerAddRequest(arguments[2], arguments[1]);
            if (response == null)
                return true;
            p.sendAPIMessage(response.get("info").getAsString(), true);
        } else if (arguments.length == 3 && arguments[0].equalsIgnoreCase("remove")) {
            JsonObject response = APIRequest.sendPlayerRemoveRequest(arguments[2], arguments[1]);
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
        return TabCompletionBuilder.getBuilder(arguments)
                .addAtIndex(1, "list", "add", "remove")
                .addAtIndex(2, Syncer.getPlayerGroupList())
                .build();
    }
}