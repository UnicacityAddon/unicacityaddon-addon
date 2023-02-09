package com.rettichlp.unicacityaddon.base.models;

public class PlayerGroup {

    private final String name;
    private final String uuid;

    public PlayerGroup(String name, String uuid) {
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