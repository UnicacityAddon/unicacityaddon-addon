package com.rettichlp.UnicacityAddon.base.teamspeak.commands;

import com.rettichlp.UnicacityAddon.base.teamspeak.CommandResponse;

/**
 * @author Fuzzlemann
 */
public class ClientNotifyRegisterCommand extends BaseCommand<CommandResponse> {
    public ClientNotifyRegisterCommand(int schandlerID, String eventName) {
        super("clientnotifyregister schandlerid=" + schandlerID + " event=" + eventName);
    }
}
