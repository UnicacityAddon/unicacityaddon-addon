package com.rettichlp.unicacityaddon.commands.teamspeak;

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
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import jdk.internal.joptsimple.internal.Strings;
import net.labymod.api.client.chat.command.Command;

import java.util.Collections;
import java.util.List;

/**
 * @author Fuzzlemann
 * @author RettichLP
 */
@UCCommand
public class MoveToCommand extends Command {

    private static final String usage = "/moveto [Spieler]";

    public MoveToCommand() {
        super("moveto");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (arguments.length < 1) {
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

        List<Client> clients = TSUtils.getClientsByName(Collections.singletonList(name));
        if (clients.isEmpty()) {
            p.sendErrorMessage("Es wurde kein Spieler auf dem TeamSpeak mit diesem Namen gefunden.");
            return true;
        }

        Client client = clients.get(0);
        CommandResponse response = new ClientMoveCommand(client.getChannelID(), TSUtils.getMyClientID()).getResponse();

        if (!response.succeeded()) {
            p.sendErrorMessage("Das Bewegen ist fehlgeschlagen.");
            return true;
        }

        p.sendMessage(Message.getBuilder()
                .prefix()
                .of("Du bist in den Channel von").color(ColorCode.GRAY).advance().space()
                .of(name).color(ColorCode.AQUA).advance().space()
                .of("gegangen.").color(ColorCode.GRAY).advance()
                .createComponent());
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}