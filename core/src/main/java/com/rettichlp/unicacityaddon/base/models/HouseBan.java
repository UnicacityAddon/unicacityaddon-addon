package com.rettichlp.unicacityaddon.base.models;

import java.util.List;

public class HouseBan {

    private final long duration;
    private final List<HouseBanReason> houseBanReasonList;
    private final long expirationTime;
    private final String name;
    private final long startTime;
    private final String uuid;

    public HouseBan(long duration, List<HouseBanReason> houseBanReasonList, long expirationTime, String name, long startTime, String uuid) {
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

    public List<HouseBanReason> getHouseBanReasonList() {
        return houseBanReasonList;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public String getName() {
        return name;
    }

    public long getStartTime() {
        return startTime;
    }

    public String getUuid() {
        return uuid;
    }
}