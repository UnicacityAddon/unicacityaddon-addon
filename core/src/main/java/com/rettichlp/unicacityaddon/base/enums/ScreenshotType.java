package com.rettichlp.unicacityaddon.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public enum ScreenshotType {

    ANDERE("andere"),
    BLACKLIST("blacklist"),
    DROGENEINNAHME("drogeneinnahme"),
    EQUIP("equip"),
    GROSSEREIGNIS("großeinsatz"),
    KILLS("kills"),
    KORRUPTION("korruption"),
    NOTRUF("notruf"),
    REINFORCEMENT("reinforcement"),
    ROLEPLAY("roleplay"),
    TICKET("ticket"),
    VERHAFTUNG("verhaftung");

    private final String directoryName;
}