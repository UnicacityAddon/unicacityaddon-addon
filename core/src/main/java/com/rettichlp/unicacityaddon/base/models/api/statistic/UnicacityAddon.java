package com.rettichlp.unicacityaddon.base.models.api.statistic;

/**
 * @author RettichLP
 */
public class UnicacityAddon {

    private final long tokenGenerateTime;
    private final Groups groups;
    private final boolean blacklisted;
    private final String version;

    public UnicacityAddon(long tokenGenerateTime, Groups groups, boolean blacklisted, String version) {
        this.tokenGenerateTime = tokenGenerateTime;
        this.groups = groups;
        this.blacklisted = blacklisted;
        this.version = version;
    }

    public long getTokenGenerateTime() {
        return tokenGenerateTime;
    }

    public Groups getGroups() {
        return groups;
    }

    public boolean isBlacklisted() {
        return blacklisted;
    }

    public String getVersion() {
        return version;
    }
}