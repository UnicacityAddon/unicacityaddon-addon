package com.rettichlp.unicacityaddon.base.teamspeak.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.teamspeak.CommandResponse;
import com.rettichlp.unicacityaddon.base.teamspeak.TSParser;
import com.rettichlp.unicacityaddon.base.teamspeak.TSUtils;
import com.rettichlp.unicacityaddon.base.teamspeak.objects.Client;

import java.util.Arrays;
import java.util.Collection;
import java.util.StringJoiner;

/**
 * @author Fuzzlemann
 */
public class ClientMoveCommand extends BaseCommand<CommandResponse> {

    public ClientMoveCommand(UnicacityAddon unicacityAddon, int channelID, Collection<Client> clients) {
        this(unicacityAddon, channelID, clients.stream().mapToInt(Client::getClientID).toArray());
    }

    public ClientMoveCommand(UnicacityAddon unicacityAddon, int channelID, Client... clients) {
        this(unicacityAddon, channelID, Arrays.stream(clients).mapToInt(Client::getClientID).toArray());
    }

    public ClientMoveCommand(UnicacityAddon unicacityAddon, int channelID, int... clientIDs) {
        super(unicacityAddon, parseCommand(channelID, clientIDs));
    }

    public ClientMoveCommand(UnicacityAddon unicacityAddon, int channelID, String password) {
        super(unicacityAddon, "clientmove cid=" + channelID + " cpw= " + TSParser.encode(password) + " clid=" + unicacityAddon.tsUtils().getMyClientID());
    }

    private static String parseCommand(int channelID, int... clientIDs) {
        StringJoiner stringJoiner = new StringJoiner("|");
        for (int clientID : clientIDs) {
            stringJoiner.add("clid=" + clientID);
        }

        return "clientmove cid=" + channelID + " " + stringJoiner;
    }
}
