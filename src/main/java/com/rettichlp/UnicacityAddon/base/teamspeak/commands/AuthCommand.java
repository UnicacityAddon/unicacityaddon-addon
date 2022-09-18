package com.rettichlp.UnicacityAddon.base.teamspeak.commands;

import com.rettichlp.UnicacityAddon.base.teamspeak.CommandResponse;

/**
 * @author Fuzzlemann
 */
public class AuthCommand extends BaseCommand<CommandResponse> {
    public AuthCommand(String apiKey) {
        super("auth apikey=" + apiKey);
    }
}
