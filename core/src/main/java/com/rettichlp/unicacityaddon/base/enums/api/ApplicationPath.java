package com.rettichlp.unicacityaddon.base.enums.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public enum ApplicationPath {

    ACTIVITY_CHECK("/activitycheck"),
    BANNER("/banner"),
    BLACKLISTREASON("/blacklistreason"),
    BLACKMARKETLOCATION("/blackmarket"),
    EVENT("/event"),
    HOUSEBAN("/houseban"),
    HOUSEBANREASON("/housebanreason"),
    MANAGEMENT("/mgmt"),
    NAVIPOINT("/navipoint"),
    PLAYER("/player"),
    REVIVE("/revive"),
    ROLEPLAY("/roleplay"),
    STATISTIC("/statistic"),
    TOKEN(""),
    WANTEDREASON("/wantedreason"),
    YASIN("/yasin");

    private final String applicationPath;
}