package com.rettichlp.UnicacityAddon.base.api.request;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.api.TokenManager;

public enum BaseUrl {

    BLACKLISTREASON("/blacklistreason"),
    BROADCAST("/broadcast"),
    HOUSEBAN("/houseban"),
    HOUSEBAN_REASON("/housebanreason"),
    NAVIPOINT("/navipoint"),
    PLAYER("/player"),
    STATISTIC("/statistic"),
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

    public String getPath(String suffix) {
        return getPath() + "/" + suffix;
    }

    public String getAddPath() {
        return getPath("add");
    }

    public String getRemovePath() {
        return getPath("remove");
    }

    public String getFactionPath() {
        return getPath(AbstractionLayer.getPlayer().getFaction().toString());
    }

    public String getFactionAddPath() {
        return getFactionPath() + "/add";
    }

    public String getFactionRemovePath() {
        return getFactionPath() + "/remove";
    }
}