package com.rettichlp.unicacityaddon.commands.api;

import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.api.exception.APIResponseException;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.api.AddonGroup;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.chat.command.Command;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand
public class PlayerGroupCommand extends Command {

    private static final String usage = "/playergroup [list|add|remove] [Gruppe] [Spieler]";

    public PlayerGroupCommand() {
        super("playergroup");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = UnicacityAddon.PLAYER;

        new Thread(() -> {
            if (arguments.length == 2 && arguments[0].equalsIgnoreCase("list") && Arrays.stream(AddonGroup.values()).anyMatch(addonGroup -> addonGroup.name().equals(arguments[1]))) {
                p.sendEmptyMessage();
                p.sendMessage(Message.getBuilder()
                        .of("Spielergruppe:").color(ColorCode.DARK_AQUA).bold().advance().space()
                        .of(arguments[1]).color(ColorCode.DARK_AQUA).advance()
                        .createComponent());

                AddonGroup.valueOf(arguments[1]).getMemberList().forEach(s -> p.sendMessage(Message.getBuilder()
                                .of("»").color(ColorCode.GRAY).advance().space()
                                .of(s).color(ColorCode.AQUA).advance()
                                .createComponent()));

                p.sendEmptyMessage();

            } else if (arguments.length == 3 && arguments[0].equalsIgnoreCase("add")) {
                try {
                    JsonObject response = APIRequest.sendPlayerAddRequest(arguments[2], arguments[1]);
                    p.sendAPIMessage(response.get("info").getAsString(), true);
                } catch (APIResponseException e) {
                    e.sendInfo();
                }
            } else if (arguments.length == 3 && arguments[0].equalsIgnoreCase("remove")) {
                try {
                    JsonObject response = APIRequest.sendPlayerRemoveRequest(arguments[2], arguments[1]);
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
        return TabCompletionBuilder.getBuilder(arguments)
                .addAtIndex(1, "list", "add", "remove")
                .addAtIndex(2, Arrays.stream(AddonGroup.values()).map(Enum::name).collect(Collectors.toList()))
                .build();
    }
}