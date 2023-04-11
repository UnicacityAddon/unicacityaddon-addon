package com.rettichlp.unicacityaddon.commands.teamspeak;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.teamspeak.CommandResponse;
import com.rettichlp.unicacityaddon.base.teamspeak.TSClientQuery;
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
public class MoveCommand extends Command {

    private static final String usage = "/move [Spieler] [Ziel]";

    private final UnicacityAddon unicacityAddon;

    public MoveCommand(UnicacityAddon unicacityAddon) {
        super("move");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length < 2) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        if (!this.unicacityAddon.configuration().tsApiKey().getOrDefault("").matches("([A-Z0-9]{4}(-*)){6}")) {
            p.sendErrorMessage("Teamspeak API Key ist nicht gültig!");
            return true;
        }

        if (!TSClientQuery.clientQueryConnected) {
            p.sendErrorMessage("Keine Verbindung zur TeamSpeak ClientQuery!");
            TSClientQuery.reconnect(this.unicacityAddon);
            return true;
        }

        String name = arguments[0];
        String target = arguments[1];

        List<Client> clientsMoved = this.unicacityAddon.tsUtils().getClientsByName(Collections.singletonList(name));
        List<Client> clientsMoveTo = this.unicacityAddon.tsUtils().getClientsByName(Collections.singletonList(target));

        if (clientsMoved.isEmpty() || clientsMoveTo.isEmpty()) {
            p.sendErrorMessage("Einer der Spieler befindet sich nicht auf dem TeamSpeak.");
            return true;
        }

        Client moveToClient = clientsMoveTo.get(0);
        CommandResponse response = new ClientMoveCommand(this.unicacityAddon, moveToClient.getChannelID(), clientsMoved).getResponse();
        if (!response.succeeded()) {
            p.sendErrorMessage("Das Moven ist fehlgeschlagen.");
            return true;
        }

        p.sendInfoMessage("Du hast die Person gemoved.");
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}