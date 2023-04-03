package com.rettichlp.unicacityaddon.commands.teamspeak;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.CommandResponse;
import com.rettichlp.unicacityaddon.base.teamspeak.TSClientQuery;
import com.rettichlp.unicacityaddon.base.teamspeak.commands.ClientMoveCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.objects.Client;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
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

    private final UnicacityAddon unicacityAddon;

    public MoveToCommand(UnicacityAddon unicacityAddon) {
        super("moveto");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length < 1) {
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

        List<Client> clients = this.unicacityAddon.tsUtils().getClientsByName(Collections.singletonList(name));
        if (clients.isEmpty()) {
            p.sendErrorMessage("Es wurde kein Spieler auf dem TeamSpeak mit diesem Namen gefunden.");
            return true;
        }

        Client client = clients.get(0);
        CommandResponse response = new ClientMoveCommand(this.unicacityAddon, client.getChannelID(), this.unicacityAddon.tsUtils().getMyClientID()).getResponse();

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
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}