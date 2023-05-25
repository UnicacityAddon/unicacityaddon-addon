package com.rettichlp.unicacityaddon.base.teamspeak.objects;

import com.rettichlp.unicacityaddon.base.teamspeak.CommandResponse;

import java.util.Map;

/**
 * The following code is based on MPL-licensed code by Paul Zhang.
 * The original code is available at: <a href="https://github.com/paulzhng/UCUtils">https://github.com/paulzhng/UCUtils</a>.
 * Copyright (c) 2019/2020 Paul Zhang
 * <p>
 * The following code is subject to the MPL Version 2.0.
 *
 * @author Fuzzlemann
 */
public class Client {

    private final int clientID;
    private final int clientDatabaseID;
    private final int channelID;
    private final String name;
    private final int clientType;

    public Client(Map<String, String> map) {
        this.clientID = CommandResponse.parseInt(map.get("clid"));
        this.clientDatabaseID = CommandResponse.parseInt(map.get("client_database_id"));
        this.channelID = CommandResponse.parseInt(map.get("cid"));
        this.name = map.get("client_nickname");
        this.clientType = CommandResponse.parseInt(map.get("client_type"));
    }

    public Client(int clientID, int clientDatabaseID, int channelID, String name, int clientType) {
        this.clientID = clientID;
        this.clientDatabaseID = clientDatabaseID;
        this.channelID = channelID;
        this.name = name;
        this.clientType = clientType;
    }

    public int getClientID() {
        return clientID;
    }

    public int getClientDatabaseID() {
        return clientDatabaseID;
    }

    public int getChannelID() {
        return channelID;
    }

    public String getName() {
        return name;
    }

    public int getClientType() {
        return clientType;
    }
}
