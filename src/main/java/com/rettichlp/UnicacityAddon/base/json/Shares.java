package com.rettichlp.UnicacityAddon.base.json;

import java.util.List;

/**
 * @author RettichLP
 */
public class Shares {

    private List<SharesEntry> sharesEntryList;

    public Shares() {
    }

    public List<SharesEntry> getSharesEntryList() {
        return sharesEntryList;
    }

    public void setSharesEntryList(List<SharesEntry> sharesEntryList) {
        this.sharesEntryList = sharesEntryList;
    }
}