package com.rettichlp.UnicacityAddon.base.api.json;

import java.util.List;

// TODO: 20.09.2022
public class HouseBan {

    private List<HouseBanEntry> houseBanEntryList;
    private List<HouseBanReason> houseBanReasonList;

    public HouseBan() {
    }

    public List<HouseBanEntry> getHouseBanEntryList() {
        return houseBanEntryList;
    }

    public void setHouseBanEntryList(List<HouseBanEntry> houseBanEntryList) {
        this.houseBanEntryList = houseBanEntryList;
    }

    public List<HouseBanReason> getHouseBanReasonList() {
        return houseBanReasonList;
    }

    public void setHouseBanReasonList(List<HouseBanReason> houseBanReasonList) {
        this.houseBanReasonList = houseBanReasonList;
    }
}