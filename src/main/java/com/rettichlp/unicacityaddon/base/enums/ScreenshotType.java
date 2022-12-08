package com.rettichlp.unicacityaddon.base.enums;

/**
 * @author RettichLP
 */
public enum ScreenshotType {

    KILLS("kills"),
    GROSSEREIGNIS("großeinsatz"),
    DROGENEINNAHME("drogeneinnahme"),
    EQUIP("equip"),
    REINFORCEMENT("reinforcement"),
    ROLEPLAY("roleplay"),
    VERHAFTUNG("verhaftung"),
    KORRUPTION("korruption"),
    TICKET("ticket"),
    BLACKLIST("blacklist"),
    NOTRUF("notruf"),
    ANDERE("andere");

    private final String displayName;

    ScreenshotType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}