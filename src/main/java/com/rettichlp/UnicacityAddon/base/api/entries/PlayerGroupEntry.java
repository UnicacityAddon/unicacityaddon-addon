package com.rettichlp.UnicacityAddon.base.api.entries;

public class PlayerGroupEntry {

    private final String name;
    private final String uuid;

    public PlayerGroupEntry(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }
}