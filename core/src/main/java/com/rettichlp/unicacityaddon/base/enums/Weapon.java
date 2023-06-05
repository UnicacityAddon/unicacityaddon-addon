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
    EXTENSO_18("Extenso-18"); // Jagdgewehr

    private final String name;

    public String getDisplayName() {
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
                .filter(weapon -> displayName.equalsIgnoreCase(weapon.getDisplayName()))
                .findFirst()
                .orElse(null);
    }

    private Matcher getAmmunitionNBTTagMatcher(UnicacityAddon unicacityAddon) {
        ClientPlayer clientPlayer = unicacityAddon.player().getPlayer();
        if (clientPlayer != null) {
            NBTTagCompound nbtTagCompound = clientPlayer.getMainHandItemStack().getNBTTag();
            if (nbtTagCompound != null) {
                NBTTag<?> nbtTag = nbtTagCompound.get("display");
                if (nbtTag != null && nbtTag.value() != null) {
                    Matcher matcher = Pattern.compile("(\\d+)/(\\d+)").matcher(nbtTag.value().toString());
                    if (matcher.find()) {
                        return matcher;
                    }
                }
            }
        }
        return null;
    }
}