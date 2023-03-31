package com.rettichlp.unicacityaddon.base.teamspeak.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.teamspeak.CommandResponse;

import java.util.Map;

/**
 * @author Fuzzlemann
 */
public class WhoAmICommand extends BaseCommand<WhoAmICommand.Response> {

    public WhoAmICommand(UnicacityAddon unicacityAddon) {
        super(unicacityAddon, "whoami");
    }

    public static class Response extends CommandResponse {

        private final int clientID;
        private final int channelID;

        public Response(String rawResponse) {
            super(rawResponse);
            Map<String, String> response = getResponse();
            this.clientID = parseInt(response.get("clid"));
            this.channelID = parseInt(response.get("cid"));
        }

        public int getClientID() {
            return clientID;
        }

        public int getChannelID() {
            return channelID;
        }
    }
}