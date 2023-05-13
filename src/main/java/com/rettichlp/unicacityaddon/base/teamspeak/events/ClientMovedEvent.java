package com.rettichlp.unicacityaddon.base.teamspeak.events;

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
@TSEvent.Name("notifyclientmoved")
public class ClientMovedEvent extends TSEvent {

    private final int clientID;
    private final int targetChannelID;
    private final boolean moved;
    private final int invokerID;
    private final String invokerName;
    private final String invokerUniqueID;

    public ClientMovedEvent(String input) {
        super(input);

        this.clientID = CommandResponse.parseInt(map.get("clid"));
        this.targetChannelID = CommandResponse.parseInt(map.get("ctid"));
        this.moved = CommandResponse.parseBoolean(map.get("reasonid"));
        this.invokerID = CommandResponse.parseInt(map.get("invokerid"));
        this.invokerName = map.get("invokername");
        this.invokerUniqueID = map.get("invokeruid");
    }

    public int getClientID() {
        return clientID;
    }

    public int getTargetChannelID() {
        return targetChannelID;
    }

    public boolean isMoved() {
        return moved;
    }

    public int getInvokerID() {
        return invokerID;
    }

    public String getInvokerName() {
        return invokerName;
    }

    public String getInvokerUniqueID() {
        return invokerUniqueID;
    }
}