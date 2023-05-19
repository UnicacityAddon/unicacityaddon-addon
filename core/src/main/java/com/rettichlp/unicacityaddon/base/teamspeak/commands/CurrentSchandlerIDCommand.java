package com.rettichlp.unicacityaddon.base.teamspeak.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.teamspeak.CommandResponse;

/**
 * The following code is based on MPL-licensed code by Paul Zhang.
 * The original code is available at: <a href="https://github.com/paulzhng/UCUtils">https://github.com/paulzhng/UCUtils</a>.
 * Copyright (c) 2019/2020 Paul Zhang
 * <p>
 * The following code is subject to the MPL Version 2.0.
 *
 * @author Fuzzlemann
 */
public class CurrentSchandlerIDCommand extends BaseCommand<CurrentSchandlerIDCommand.Response> {

    public CurrentSchandlerIDCommand(UnicacityAddon unicacityAddon) {
        super(unicacityAddon, "currentschandlerid");
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
