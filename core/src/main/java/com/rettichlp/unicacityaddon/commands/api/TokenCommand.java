package com.rettichlp.unicacityaddon.commands.api;

import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.api.TokenManager;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class TokenCommand extends Command {

    private static final String usage = "/token (create|revoke)";

    public TokenCommand() {
        super("token");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = UnicacityAddon.PLAYER;

        if (arguments.length == 0) {
            p.sendMessage(Message.getBuilder()
                    .prefix()
                    .of("Mit diesem").color(ColorCode.GRAY).advance().space()
                    .of("Token").color(ColorCode.AQUA)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of(TokenManager.API_TOKEN).color(ColorCode.RED).advance().createComponent())
                            .clickEvent(ClickEvent.Action.RUN_COMMAND, "/token copy")
                            .advance().space()
                    .of("kann jeder in deinem Namen Anfragen an die API senden.").color(ColorCode.GRAY).advance()
                    .createComponent());
        } else if (arguments.length == 1 && arguments[0].equalsIgnoreCase("create")) {
            JsonObject response = APIRequest.sendTokenCreateRequest();
            if (response == null)
                return true;
            p.sendAPIMessage(response.get("info").getAsString(), true);
        } else if (arguments.length == 1 && arguments[0].equalsIgnoreCase("copy")) {
            p.copyToClipboard(TokenManager.API_TOKEN);
            // TODO: 10.12.2022 LabyMod.getInstance().notifyMessageRaw(ColorCode.GREEN.getCode() + "Kopiert!", "Token in Zwischenablage kopiert.");
        } else if (arguments.length == 1 && arguments[0].equalsIgnoreCase("revoke")) {
            JsonObject response = APIRequest.sendTokenRevokeRequest();
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
                .addAtIndex(1, "create", "revoke")
                .build();
    }
}