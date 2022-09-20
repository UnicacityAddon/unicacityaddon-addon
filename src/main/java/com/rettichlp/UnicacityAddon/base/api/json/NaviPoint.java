package com.rettichlp.UnicacityAddon.base.api.json;

import java.util.List;

// TODO: 20.09.2022  
public class NaviPoint {

    private List<NaviPointEntry> naviPointEntryList;

    public NaviPoint() {
    }

    public List<NaviPointEntry> getNaviPointEntryList() {
        return naviPointEntryList;
    }

    public void setNaviPointEntryList(List<NaviPointEntry> naviPointEntryList) {
        this.naviPointEntryList = naviPointEntryList;
    }
}