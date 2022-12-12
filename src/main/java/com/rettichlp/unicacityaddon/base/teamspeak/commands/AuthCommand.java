package com.rettichlp.unicacityaddon.base.teamspeak.commands;

import com.rettichlp.unicacityaddon.base.teamspeak.CommandResponse;

/**
 * @author Fuzzlemann
 */
public class AuthCommand extends BaseCommand<CommandResponse> {

    public AuthCommand(String apiKey) {
        super("auth apikey=" + apiKey);
    }
}
