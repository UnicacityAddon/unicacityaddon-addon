package com.rettichlp.UnicacityAddon.base.api.json;

import java.util.List;
import java.util.concurrent.TimeUnit;

// TODO: 20.09.2022  
public class HouseBanEntry {

    private final String uuid;
    private List<HouseBanReason> houseBanReasonList;
    private final long startTime;

    public HouseBanEntry(String uuid, List<HouseBanReason> houseBanReasonList, long startTime) {
        this.uuid = uuid;
        this.houseBanReasonList = houseBanReasonList;
        this.startTime = startTime;
    }

    public String getUUID() {
        return uuid;
    }

    public List<HouseBanReason> getHouseBanReasonList() {
        return houseBanReasonList;
    }

    public void setHouseBanReasonList(List<HouseBanReason> houseBanReasonList) {
        this.houseBanReasonList = houseBanReasonList;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getDuration() {
        int days = houseBanReasonList.stream().map(HouseBanReason::getDays).reduce(0, Integer::sum);
        return TimeUnit.DAYS.toMillis(days);
    }

    public Object getExpirationTime() {
        return startTime + getDuration();
    }
}