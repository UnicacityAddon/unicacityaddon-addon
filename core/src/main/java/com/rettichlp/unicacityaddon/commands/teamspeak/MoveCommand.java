package com.rettichlp.unicacityaddon.commands.teamspeak;

import com.google.inject.Inject;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.CommandResponse;
import com.rettichlp.unicacityaddon.base.teamspeak.TSClientQuery;
import com.rettichlp.unicacityaddon.base.teamspeak.TSUtils;
import com.rettichlp.unicacityaddon.base.teamspeak.commands.ClientMoveCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.objects.Client;
import jdk.internal.joptsimple.internal.Strings;
import net.labymod.api.client.chat.command.Command;

import java.util.Collections;
import java.util.List;

/**
 * @author Fuzzlemann
 * @author RettichLP
 */
@UCCommand
public class MoveCommand extends Command {

    private static final String usage = "/move [Spieler] [Ziel]";

    @Inject
    private MoveCommand() {
        super("move");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (arguments.length < 2) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        if (!UnicacityAddon.configuration.tsApiKey().getOrDefault(Strings.EMPTY).matches("([A-Z0-9]{4}(-*)){6}")) {
            p.sendErrorMessage("Teamspeak API Key ist nicht gültig!");
            return true;
        }

        if (!TSClientQuery.clientQueryConnected) {
            p.sendErrorMessage("Keine Verbindung zur TeamSpeak ClientQuery!");
            TSClientQuery.reconnect();
            return true;
        }

        String name = arguments[0];
        String target = arguments[1];

        List<Client> clientsMoved = TSUtils.getClientsByName(Collections.singletonList(name));
        List<Client> clientsMoveTo = TSUtils.getClientsByName(Collections.singletonList(target));

        if (clientsMoved.isEmpty() || clientsMoveTo.isEmpty()) {
            p.sendErrorMessage("Einer der Spieler befindet sich nicht auf dem TeamSpeak.");
            return true;
        }

        Client moveToClient = clientsMoveTo.get(0);
        CommandResponse response = new ClientMoveCommand(moveToClient.getChannelID(), clientsMoved).getResponse();
        if (!response.succeeded()) {
            p.sendErrorMessage("Das Moven ist fehlgeschlagen.");
            return true;
        }

        p.sendInfoMessage("Du hast die Person gemoved.");
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}