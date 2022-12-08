package com.rettichlp.unicacityaddon.base.enums.api;

public enum ApplicationPath {

    BLACKLISTREASON("/blacklistreason"),
    BROADCAST("/broadcast"),
    HOUSEBAN("/houseban"),
    HOUSEBANREASON("/housebanreason"),
    NAVIPOINT("/navipoint"),
    PLAYER("/player"),
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