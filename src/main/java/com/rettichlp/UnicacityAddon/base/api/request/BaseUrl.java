package com.rettichlp.UnicacityAddon.base.api.request;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.api.TokenManager;

public enum BaseUrl {

    BLACKLISTREASON("/blacklistreason"),
    BROADCAST_QUEUE("/broadcast/queue"),
    BROADCAST_SEND("/broadcast/send"),
    HOUSEBAN("/houseban"),
    HOUSEBAN_REASON("/housebanreason"),
    NAVIPOINT("/navipoint"),
    PLAYER("/player"),
    PLAYER_GROUP("/player/groups"),
    STATISTIC("/statistic/" + AbstractionLayer.getPlayer().getName()),
    TOKEN_CREATE("/create"),
    TOKEN_REVOKE("/revoke"),
    WANTED_REASON("/wantedreason");

    private final String path;

    BaseUrl(String path) {
        this.path = path;
    }

    public String getPath() {
        return "http://rettichlp.de:8888/unicacityaddon/v1/" + TokenManager.API_TOKEN + path;
    }

    public String getAddPath() {
        return getPath() + "/add";
    }

    public String getRemovePath() {
        return getPath() + "/remove";
    }

    public String getFactionPath() {
        return getPath() + "/" + AbstractionLayer.getPlayer().getFaction();
    }

    public String getFactionAddPath() {
        return getFactionPath() + "/add";
    }

    public String getFactionRemovePath() {
        return getFactionPath() + "/remove";
    }
}