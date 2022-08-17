package com.rettichlp.UnicacityAddon.base.json;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
public class Blacklist {

    private List<BlacklistEntry> blacklistEntryList;

    public Blacklist() {
    }

    public List<String> getBlacklistReasons() {
        return blacklistEntryList.isEmpty() ? Collections.emptyList() : blacklistEntryList.stream().map(BlacklistEntry::getReason).collect(Collectors.toList());
    }

    public BlacklistEntry getBlackListEntryByReason(String reason) {
        return blacklistEntryList.stream().filter(blacklistEntry -> blacklistEntry.getReason().equals(reason)).findAny().orElse(null);
    }
}