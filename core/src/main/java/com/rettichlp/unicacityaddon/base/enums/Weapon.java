package com.rettichlp.unicacityaddon.base.enums;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import net.labymod.api.nbt.NBTTag;
import net.labymod.api.nbt.tags.NBTTagCompound;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author RettichLP
 */
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
        return "§8" + name;
    }

    public int getLoadedAmmunition(UnicacityAddon unicacityAddon) {
        Matcher matcher = getAmmunitionNBTTagMatcher(unicacityAddon);
        return matcher != null ? Integer.parseInt(matcher.group(1)) : 0;
    }

    public int getBackupAmmunition(UnicacityAddon unicacityAddon) {
        Matcher matcher = getAmmunitionNBTTagMatcher(unicacityAddon);
        return matcher != null ? Integer.parseInt(matcher.group(2)) : 0;
    }

    public static Weapon getWeaponByName(String name) {
        return Arrays.stream(Weapon.values())
                .filter(weapon -> name.equalsIgnoreCase(weapon.getName()))
                .findFirst()
                .orElse(null);
    }

    public static Weapon getWeaponByItemName(String displayName) {
        return Arrays.stream(Weapon.values())
                .filter(weapon -> displayName.equalsIgnoreCase(weapon.getItemName()))
                .findFirst()
                .orElse(null);
    }

    private Matcher getAmmunitionNBTTagMatcher(UnicacityAddon unicacityAddon) {
        NBTTagCompound nbtTagCompound = unicacityAddon.player().getPlayer().getMainHandItemStack().getNBTTag();
        if (nbtTagCompound != null) {
            NBTTag<?> nbtTag = nbtTagCompound.get("display");
            if (nbtTag != null) {
                Matcher matcher = Pattern.compile("(\\d+)/(\\d+)").matcher(nbtTag.value().toString());
                if (matcher.find()) {
                    return matcher;
                }
            }
        }
        return null;
    }
}