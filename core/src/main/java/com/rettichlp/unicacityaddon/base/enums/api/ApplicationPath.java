package com.rettichlp.unicacityaddon.base.enums.api;

public enum ApplicationPath {

    BANNER("/banner"),
    BLACKLISTREASON("/blacklistreason"),
    BROADCAST("/broadcast"),
    EVENT("/event"),
    HOUSEBAN("/houseban"),
    HOUSEBANREASON("/housebanreason"),
    MANAGEMENT("/mgmt"),
    NAVIPOINT("/navipoint"),
    PLAYER("/player"),
    REVIVE("/revive"),
    STATISTIC("/statistic"),
    TOKEN(""),
    WANTEDREASON("/wantedreason"),
    YASIN("/yasin");

    private final String applicationPath;

    ApplicationPath(String applicationPath) {
        this.applicationPath = applicationPath;
    }

    public String getApplicationPath() {
        return this.applicationPath;
    }
}