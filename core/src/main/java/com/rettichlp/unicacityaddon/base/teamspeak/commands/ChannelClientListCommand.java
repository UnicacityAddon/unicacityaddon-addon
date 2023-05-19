package com.rettichlp.unicacityaddon.base.teamspeak.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.teamspeak.CommandResponse;
import com.rettichlp.unicacityaddon.base.teamspeak.objects.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The following code is based on MPL-licensed code by Paul Zhang.
 * The original code is available at: <a href="https://github.com/paulzhng/UCUtils">https://github.com/paulzhng/UCUtils</a>.
 * Copyright (c) 2019/2020 Paul Zhang
 * <p>
 * The following code is subject to the MPL Version 2.0.
 *
 * @author Fuzzlemann
 */
public class ChannelClientListCommand extends BaseCommand<ChannelClientListCommand.Response> {

    public ChannelClientListCommand(UnicacityAddon unicacityAddon, int channelID) {
        super(unicacityAddon, "channelclientlist cid=" + channelID);
    }

    public static class Response extends CommandResponse {

        private final List<Client> clients = new ArrayList<>();

        public Response(String rawResponse) {
            super(rawResponse);
            List<Map<String, String>> maps = getResponseList();

            for (Map<String, String> clientMap : maps) {
                clients.add(new Client(clientMap));
            }
        }

        public List<Client> getClients() {
            return clients;
        }
    }
}
