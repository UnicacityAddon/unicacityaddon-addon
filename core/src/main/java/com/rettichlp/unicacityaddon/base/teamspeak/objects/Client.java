package com.rettichlp.unicacityaddon.base.teamspeak.objects;

import com.rettichlp.unicacityaddon.base.teamspeak.CommandResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
@Getter
@AllArgsConstructor
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
}
