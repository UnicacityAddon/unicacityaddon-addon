package com.rettichlp.UnicacityAddon.base.teamspeak.commands;

import com.rettichlp.UnicacityAddon.base.teamspeak.CommandResponse;

/**
 * @author Fuzzlemann
 */
public class CurrentSchandlerIDCommand extends BaseCommand<CurrentSchandlerIDCommand.Response> {

    public CurrentSchandlerIDCommand() {
        super("currentschandlerid");
    }

    public static class Response extends CommandResponse {
        private final int schandlerID;

        public Response(String rawResponse) {
            super(rawResponse);
            this.schandlerID = parseInt(getResponse().get("schandlerid"));
        }

        public int getSchandlerID() {
            return schandlerID;
        }
    }
}
