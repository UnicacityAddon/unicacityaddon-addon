package com.rettichlp.unicacityaddon.base.teamspeak.commands;

import com.rettichlp.unicacityaddon.base.teamspeak.CommandResponse;

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
public class WhoAmICommand extends BaseCommand<WhoAmICommand.Response> {

    public WhoAmICommand() {
        super("whoami");
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