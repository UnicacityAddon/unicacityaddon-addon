package com.rettichlp.unicacityaddon.base.teamspeak.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.teamspeak.CommandResponse;

/**
 * @author Fuzzlemann
 */
public class ClientNotifyRegisterCommand extends BaseCommand<CommandResponse> {

    public ClientNotifyRegisterCommand(UnicacityAddon unicacityAddon, int schandlerID, String eventName) {
        super(unicacityAddon, "clientnotifyregister schandlerid=" + schandlerID + " event=" + eventName);
    }
}
