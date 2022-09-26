package com.rettichlp.UnicacityAddon.base.api;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;

public enum BaseUrl {

    BLACKLISTREASON("/blacklistreason/" + AbstractionLayer.getPlayer().getFaction()),
    HOUSEBAN("/houseban"),
    HOUSEBAN_REASON("/housebanreason"),
    NAVIPOINT("/navipoint"),
    WANTED_REASON("/wantedreason");

    private final String path;

    BaseUrl(String path) {
        this.path = path;
    }

    public String getPath() {
        ConfigElements.setAPIToken("aaaaaaaaaabbbbbbbbbbaaaaaaaaaabbbbbbbbbb"); // TODO: 26.09.2022  
        return "http://rettichlp.de:8888/unicacityaddon/v1/" + ConfigElements.getAPIToken() + path;
    }

    public String getAddPath() {
        return getPath() + "/add";
    }

    public String getRemovePath() {
        return getPath() + "/remove";
    }
}