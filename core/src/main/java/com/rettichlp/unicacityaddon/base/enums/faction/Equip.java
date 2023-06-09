package com.rettichlp.unicacityaddon.base.enums.faction;

import com.rettichlp.unicacityaddon.base.config.UnicacityAddonConfiguration;
import com.rettichlp.unicacityaddon.base.enums.Weapon;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public enum Equip {

    BASI("Baseballschläger", "Baseballschläger", (configuration) -> configuration.equipSetting().badFactionSetting().baseballBat().getOrDefault("0")),
    DONUT("Donut", "Donuts", (configuration) -> configuration.equipSetting().donut().getOrDefault("30")),
    KEVLAR("Leichte Kevlar", "Kevlar", (configuration) -> configuration.equipSetting().lkev().getOrDefault("1450")),
    HEAVY_KEVLAR("Schwere Kevlar", "schwere Kevlar", (configuration) -> configuration.equipSetting().skev().getOrDefault("2200")),
    PISTOL(Weapon.P_69.getName(), Weapon.P_69.getName(), (configuration) -> configuration.equipSetting().pistol().getOrDefault("350")),
    MP5(Weapon.SCATTER_3.getName(), Weapon.SCATTER_3.getName(), (configuration) -> configuration.equipSetting().mp5().getOrDefault("550")),
    BANDAGES("Bandagen", "Bandagen", (configuration) -> configuration.equipSetting().medicSetting().bandage().getOrDefault("0")),
    PAINKILLER("Schmerzpillen", "Schmerzpillen", (configuration) -> configuration.equipSetting().medicSetting().pills().getOrDefault("0")),
    WATER("Wasser", "Wasser", (configuration) -> configuration.equipSetting().water().getOrDefault("0")),
    SYRINGE("Spritzen", "Spritzen", (configuration) -> configuration.equipSetting().medicSetting().syringe().getOrDefault("120")),
    BREAD("Brot", "Brote", (configuration) -> configuration.equipSetting().bread().getOrDefault("40")),
    PEPPER_SPRAY("Pfefferspray", "Pfefferspray", (configuration) -> configuration.equipSetting().pepperSpray().getOrDefault("400")),
    FIREEXTINGUISHER("Feuerlöscher", "Feuerlöscher", (configuration) -> configuration.equipSetting().medicSetting().extinguisher().getOrDefault("0")),
    AXE("Feuerwehraxt", "Feuerwehraxt", (configuration) -> configuration.equipSetting().medicSetting().axe().getOrDefault("0")),
    HELMET("Helm", "Helm", (configuration) -> configuration.equipSetting().medicSetting().helmet().getOrDefault("0")),
    SWAT_SHIELD("SWAT-Schild", "Einsatzschild", (configuration) -> configuration.equipSetting().stateSetting().shield().getOrDefault("0")),
    MASK("Maske", "Maske", (configuration) -> configuration.equipSetting().mask().getOrDefault("800")),
    FLASHBANG("Blendgranate", "Blendgranate", (configuration) -> configuration.equipSetting().stateSetting().grenade().getOrDefault("250")),
    SMOKEGRENADE("Smoke", "Rauchgranate", (configuration) -> configuration.equipSetting().stateSetting().smoke().getOrDefault("300")),
    TAZER("Tazer", "Tazer", (configuration) -> configuration.equipSetting().stateSetting().taser().getOrDefault("0")),
    CUFFS("Handschellen", "Handschellen", (configuration) -> configuration.equipSetting().stateSetting().cuff().getOrDefault("0")),
    WINGSUIT("Elytra", "Fallschirm", (configuration) -> configuration.equipSetting().stateSetting().elytra().getOrDefault("450")),
    SNIPER(Weapon.VIPER_9.getName(), Weapon.VIPER_9.getName(), (configuration) -> configuration.equipSetting().sniper().getOrDefault("2700")),
    DEFUSE_KIT("Defuse Kit", "Defuse-Kit", (configuration) -> configuration.equipSetting().stateSetting().defuseKit().getOrDefault("500")),
    TRACKER("Peilsender", "Peilsender", (configuration) -> configuration.equipSetting().stateSetting().tracker().getOrDefault("0")),
    EXPLOSIVEBELT("Sprenggürtel", "Sprenggürtel", (configuration) -> configuration.equipSetting().terrorSetting().explosiveBelt().getOrDefault("0")),
    RPG7(Weapon.ALPHA_7.getName(), Weapon.ALPHA_7.getName(), (configuration) -> configuration.equipSetting().terrorSetting().rpg7().getOrDefault("0")),
    NOTEPAD("Notizblock", "Notizblock", (configuration) -> configuration.equipSetting().newsSetting().notepad().getOrDefault("0")), // UnicaCity Bug (Nachricht gibt es noch nicht)
    GLASSCUTTER("Glasschneider", "Glasschneider", (configuration) -> configuration.equipSetting().hitmanSetting().glassCutter().getOrDefault("0")),
    LOCKPICK("Dietrich", "Dietrich", (configuration) -> configuration.equipSetting().hitmanSetting().lockPick().getOrDefault("0"));

    private final String equipName;
    private final String messageName;
    private final Function<? super UnicacityAddonConfiguration, ? extends String> priceFunction;

    public int getPrice(UnicacityAddonConfiguration unicacityAddonConfiguration) {
        String priceString = priceFunction.apply(unicacityAddonConfiguration);
        return MathUtils.isInteger(priceString) ? Integer.parseInt(priceString) : 0;
    }
}
