package com.rettichlp.unicacityaddon.base.models.api.player;

/**
 * @author RettichLP
 */
public class PlayerEntry {

    private final String name;
    private final String uuid;

    public PlayerEntry(String name, String uuid) {
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