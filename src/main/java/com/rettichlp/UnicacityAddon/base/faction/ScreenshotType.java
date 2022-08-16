package com.rettichlp.UnicacityAddon.base.faction;

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
    BLACKLIST("blacklist");

    private final String displayName;

    ScreenshotType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}