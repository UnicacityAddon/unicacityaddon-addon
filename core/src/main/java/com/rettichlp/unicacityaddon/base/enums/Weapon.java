package com.rettichlp.unicacityaddon.base.enums;

import java.util.Arrays;

public enum Weapon {
    M4("M4"),
    MP5("MP5"),
    PISTOLE("Pistole"),
    HUNTING_RIFLE("Jagdflinte");

    private final String name;

    Weapon(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getItemName() {
        return "&8" + name;
    }

    public static Weapon getWeaponByItemName(String displayName) {
        return Arrays.stream(Weapon.values())
                .filter(weapon -> displayName.equalsIgnoreCase(weapon.getItemName()))
                .findFirst()
                .orElse(null);
    }
}