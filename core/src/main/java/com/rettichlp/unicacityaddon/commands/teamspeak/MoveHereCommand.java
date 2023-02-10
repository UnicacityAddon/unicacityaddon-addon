package com.rettichlp.unicacityaddon.commands.teamspeak;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.CommandResponse;
import com.rettichlp.unicacityaddon.base.teamspeak.TSClientQuery;
import com.rettichlp.unicacityaddon.base.teamspeak.TSUtils;
import com.rettichlp.unicacityaddon.base.teamspeak.commands.ClientMoveCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.objects.Client;
import net.labymod.api.client.chat.command.Command;

import java.util.Collections;
import java.util.List;

/**
 * @author Fuzzlemann
 * @author RettichLP
 */
@UCCommand
public class MoveHereCommand extends Command {

    private static final String usage = "/movehere [Spieler]";

    public MoveHereCommand() {
        super("movehere");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = UnicacityAddon.PLAYER;

        if (arguments.length < 1) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        if (!UnicacityAddon.ADDON.configuration().tsApiKey().getOrDefault("").matches("([A-Z0-9]{4}(-*)){6}")) {
            p.sendErrorMessage("Teamspeak API Key ist nicht gültig!");
            return true;
        }

        if (!TSClientQuery.clientQueryConnected) {
            p.sendErrorMessage("Keine Verbindung zur TeamSpeak ClientQuery!");
            TSClientQuery.reconnect();
            return true;
        }

        String name = arguments[0];

        int channelID = TSUtils.getMyChannelID();

        List<Client> clients = TSUtils.getClientsByName(Collections.singletonList(name));
        if (clients.isEmpty()) {
            p.sendErrorMessage("Es wurde kein Spieler auf dem TeamSpeak mit diesem Namen gefunden.");
            return true;
        }

        CommandResponse response = new ClientMoveCommand(channelID, clients).getResponse();
        if (!response.succeeded()) {
            p.sendErrorMessage("Das Moven ist fehlgeschlagen.");
            return true;
        }

        p.sendInfoMessage("Du hast die Person zu dir gemoved.");
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}