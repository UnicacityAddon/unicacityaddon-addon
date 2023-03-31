package com.rettichlp.unicacityaddon.base.teamspeak.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.teamspeak.CommandResponse;

/**
 * @author Fuzzlemann
 */
public class AuthCommand extends BaseCommand<CommandResponse> {

    public AuthCommand(UnicacityAddon unicacityAddon, String apiKey) {
        super(unicacityAddon, "auth apikey=" + apiKey);
    }
}
