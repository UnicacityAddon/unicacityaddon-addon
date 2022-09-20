package com.rettichlp.UnicacityAddon.base.api.json;

// TODO: 20.09.2022  
public class TokenEntry {

    private final String uuid;
    private final String token;

    public TokenEntry(String uuid, String token) {
        this.uuid = uuid;
        this.token = token;
    }

    public String getUUID() {
        return uuid;
    }

    public String getToken() {
        return token;
    }
}