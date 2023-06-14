package com.rettichlp.unicacityaddon.base.enums;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.nbt.NBTTag;
import net.labymod.api.nbt.tags.NBTTagCompound;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public enum Weapon {

    TS_19("TS-19"), // M4
    SCATTER_3("Scatter-3"), // MP5
    P_69("P-69"), // Pistole
    EXTENSO_18("Extenso-18"), // Jagdgewehr
    VIPER_9("Viper-9"), // Sniper
    ALPHA_7("Alpha-7"); // RPG

    private final String name;

    public static Weapon getWeaponByName(String name) {
        return Arrays.stream(Weapon.values())
                .filter(weapon -> name.equalsIgnoreCase(weapon.getName()))
                .findFirst()
                .orElse(null);
    }
}