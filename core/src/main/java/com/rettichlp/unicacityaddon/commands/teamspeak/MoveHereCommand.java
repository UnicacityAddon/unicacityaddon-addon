package com.rettichlp.unicacityaddon.commands.teamspeak;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.teamspeak.CommandResponse;
import com.rettichlp.unicacityaddon.base.teamspeak.TSClientQuery;
import com.rettichlp.unicacityaddon.base.teamspeak.commands.ClientMoveCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.objects.Client;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;

import java.util.Collections;
import java.util.List;

/**
 * @author Fuzzlemann
 * @author RettichLP
 */
@UCCommand(prefix = "movehere", onlyOnUnicacity = false, usage = "[Spieler]")
public class MoveHereCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public MoveHereCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length < 1) {
            sendUsage();
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

        int channelID = this.unicacityAddon.services().utilService().tsUtils().getMyChannelID();

        List<Client> clients = this.unicacityAddon.services().utilService().tsUtils().getClientsByName(Collections.singletonList(name));
        if (clients.isEmpty()) {
            p.sendErrorMessage("Es wurde kein Spieler auf dem TeamSpeak mit diesem Namen gefunden.");
            return true;
        }

        CommandResponse response = new ClientMoveCommand(this.unicacityAddon, channelID, clients).getResponse();
        if (response.failed()) {
            p.sendErrorMessage("Das Moven ist fehlgeschlagen.");
            return true;
        }

        p.sendInfoMessage("Du hast die Person zu dir gemoved.");
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}