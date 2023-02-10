package com.rettichlp.unicacityaddon.base.enums;

/**
 * @author RettichLP
 */
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

    ScreenshotType(String directoryName) {
        this.directoryName = directoryName;
    }

    public String getDirectoryName() {
        return this.directoryName;
    }
}