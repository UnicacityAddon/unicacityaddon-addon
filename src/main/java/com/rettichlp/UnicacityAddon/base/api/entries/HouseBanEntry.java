package com.rettichlp.UnicacityAddon.base.api.entries;

import java.util.List;

public class HouseBanEntry {

    private final long duration;
    private final List<HouseBanReasonEntry> houseBanReasonList;
    private final long expirationTime;
    private final String name;
    private final long startTime;
    private final String uuid;

    public HouseBanEntry(long duration, List<HouseBanReasonEntry> houseBanReasonList, long expirationTime, String name, long startTime, String uuid) {
        this.duration = duration;
        this.houseBanReasonList = houseBanReasonList;
        this.expirationTime = expirationTime;
        this.name = name;
        this.startTime = startTime;
        this.uuid = uuid;
    }

    public long getDuration() {
        return duration;
    }

    public List<HouseBanReasonEntry> getHouseBanReasonList() {
        return houseBanReasonList;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public String getName() { // TODO: 01.10.2022 get housebans by name
        return name;
    }

    public long getStartTime() {
        return startTime;
    }

    public String getUuid() { // TODO: 01.10.2022
        return uuid;
    }
}