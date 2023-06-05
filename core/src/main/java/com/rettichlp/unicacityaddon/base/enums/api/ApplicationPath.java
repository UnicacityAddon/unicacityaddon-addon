package com.rettichlp.unicacityaddon.base.enums.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
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
}