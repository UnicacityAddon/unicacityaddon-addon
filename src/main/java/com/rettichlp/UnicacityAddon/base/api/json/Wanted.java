package com.rettichlp.UnicacityAddon.base.api.json;

import java.util.List;

// TODO: 20.09.2022  
public class Wanted {

    private List<WantedEntry> wantedEntryList;

    public Wanted() {
    }

    public List<WantedEntry> getWantedEntryList() {
        return wantedEntryList;
    }

    public void setWantedEntryList(List<WantedEntry> wantedEntryList) {
        this.wantedEntryList = wantedEntryList;
    }
}