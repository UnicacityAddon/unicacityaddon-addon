package com.rettichlp.UnicacityAddon.base.api.request;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.api.TokenManager;

public enum BaseUrl {

    BLACKLISTREASON("/blacklistreason/" + AbstractionLayer.getPlayer().getFaction()),
    BROADCAST_QUEUE("/broadcast/queue"),
    BROADCAST_SEND("/broadcast/send"),
    HOUSEBAN("/houseban"),
    HOUSEBAN_REASON("/housebanreason"),
    NAVIPOINT("/navipoint"),
    PLAYER("/player"),
    PLAYER_GROUP("/player/groups"),
    STATISTIC("/statistic"),
    STATISTIC_KILL("/statistic/addKill"),
    STATISTIC_DEATH("/statistic/addDeath"),
    STATISTIC_REVIVE("/statistic/addRevive"),
    STATISTIC_SERVICE("/statistic/addService"),
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
}