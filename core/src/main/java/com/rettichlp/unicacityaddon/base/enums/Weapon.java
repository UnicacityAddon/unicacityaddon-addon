package com.rettichlp.unicacityaddon.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public enum Weapon {

    TS_19("TS19"), // M4
    SCATTER_3("Scatter3"), // MP5
    P_69("P69"), // Pistole
    EXTENSO_18("Extenso18"), // Jagdgewehr
    VIPER_9("Viper9"), // Sniper
    ALPHA_7("Alpha7"); // RPG

    private final String name;

    public static Weapon getWeaponByName(String name) {
        return Arrays.stream(Weapon.values())
                .filter(weapon -> name.equalsIgnoreCase(weapon.getName()))
                .findFirst()
                .orElse(null);
    }
}