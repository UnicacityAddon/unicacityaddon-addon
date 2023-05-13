package com.rettichlp.unicacityaddon.base.teamspeak.commands;

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
public class ClientNotifyRegisterCommand extends BaseCommand<CommandResponse> {

    public ClientNotifyRegisterCommand(int schandlerID, String eventName) {
        super("clientnotifyregister schandlerid=" + schandlerID + " event=" + eventName);
    }
}