package com.rettichlp.UnicacityAddon.base.api.json;

import java.util.List;

// TODO: 20.09.2022  
public class Token {

    private List<TokenEntry> tokenEntryList;

    public Token() {
    }

    public List<TokenEntry> getTokenEntryList() {
        return tokenEntryList;
    }

    public void setTokenEntryList(List<TokenEntry> tokenEntryList) {
        this.tokenEntryList = tokenEntryList;
    }
}