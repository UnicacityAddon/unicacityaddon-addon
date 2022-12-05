package com.rettichlp.unicacityaddon.base.teamspeak.commands;

import com.rettichlp.unicacityaddon.base.teamspeak.CommandResponse;

/**
 * @author Fuzzlemann
 */
public class ClientNotifyRegisterCommand extends BaseCommand<CommandResponse> {
    public ClientNotifyRegisterCommand(int schandlerID, String eventName) {
        super("clientnotifyregister schandlerid=" + schandlerID + " event=" + eventName);
    }
}
