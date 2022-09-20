package com.rettichlp.UnicacityAddon.base.api.json;

import java.util.List;

// TODO: 20.09.2022
public class Blacklist {

    private List<BlacklistEntry> calderonBlacklistEntryList;
    private List<BlacklistEntry> kerzakovBlacklistEntryList;
    private List<BlacklistEntry> lacosanostraBlacklistEntryList;
    private List<BlacklistEntry> obrienBlacklistEntryList;
    private List<BlacklistEntry> triadenBlacklistEntryList;
    private List<BlacklistEntry> westsideballasBlacklistEntryList;

    public Blacklist() {
    }

    public List<BlacklistEntry> getCalderonBlacklistEntryList() {
        return calderonBlacklistEntryList;
    }

    public void setCalderonBlacklistEntryList(List<BlacklistEntry> calderonBlacklistEntryList) {
        this.calderonBlacklistEntryList = calderonBlacklistEntryList;
    }

    public List<BlacklistEntry> getKerzakovBlacklistEntryList() {
        return kerzakovBlacklistEntryList;
    }

    public void setKerzakovBlacklistEntryList(List<BlacklistEntry> kerzakovBlacklistEntryList) {
        this.kerzakovBlacklistEntryList = kerzakovBlacklistEntryList;
    }

    public List<BlacklistEntry> getLacosanostraBlacklistEntryList() {
        return lacosanostraBlacklistEntryList;
    }

    public void setLacosanostraBlacklistEntryList(List<BlacklistEntry> lacosanostraBlacklistEntryList) {
        this.lacosanostraBlacklistEntryList = lacosanostraBlacklistEntryList;
    }

    public List<BlacklistEntry> getObrienBlacklistEntryList() {
        return obrienBlacklistEntryList;
    }

    public void setObrienBlacklistEntryList(List<BlacklistEntry> obrienBlacklistEntryList) {
        this.obrienBlacklistEntryList = obrienBlacklistEntryList;
    }

    public List<BlacklistEntry> getTriadenBlacklistEntryList() {
        return triadenBlacklistEntryList;
    }

    public void setTriadenBlacklistEntryList(List<BlacklistEntry> triadenBlacklistEntryList) {
        this.triadenBlacklistEntryList = triadenBlacklistEntryList;
    }

    public List<BlacklistEntry> getWestsideballasBlacklistEntryList() {
        return westsideballasBlacklistEntryList;
    }

    public void setWestsideballasBlacklistEntryList(List<BlacklistEntry> westsideballasBlacklistEntryList) {
        this.westsideballasBlacklistEntryList = westsideballasBlacklistEntryList;
    }
}