package com.rettichlp.unicacityaddon.base.models;

/**
 * @author RettichLP
 */
public class ManagementUser {

    private final boolean active;
    private final String uuid;
    private final String version;

    public ManagementUser(boolean active, String uuid, String version) {
        this.active = active;
        this.uuid = uuid;
        this.version = version;
    }

    public boolean isActive() {
        return active;
    }

    public String getUuid() {
        return uuid;
    }

    public String getVersion() {
        return version;
    }
}