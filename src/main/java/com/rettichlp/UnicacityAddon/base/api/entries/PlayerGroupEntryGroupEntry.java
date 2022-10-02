package com.rettichlp.UnicacityAddon.base.api.entries;

public class PlayerGroupEntryGroupEntry {

    private final String name;
    private final String uuid;

    public PlayerGroupEntryGroupEntry(String name, String uuid) {
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