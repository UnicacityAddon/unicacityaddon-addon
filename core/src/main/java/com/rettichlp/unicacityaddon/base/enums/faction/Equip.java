package com.rettichlp.unicacityaddon.base.enums.faction;

import com.rettichlp.unicacityaddon.base.config.UnicacityAddonConfiguration;
import com.rettichlp.unicacityaddon.base.enums.Weapon;
import com.rettichlp.unicacityaddon.base.services.utils.MathUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public enum Equip {

    BASI("Baseballschläger", "Baseballschläger", (configuration) -> configuration.equip().badFaction().baseballBat().getOrDefault("0")),
    DONUT("Donut", "Donuts", (configuration) -> configuration.equip().donut().getOrDefault("30")),
    KEVLAR("Leichte Kevlar", "Kevlar", (configuration) -> configuration.equip().lkev().getOrDefault("1450")),
    HEAVY_KEVLAR("Schwere Kevlar", "schwere Kevlar", (configuration) -> configuration.equip().skev().getOrDefault("2200")),
    HEAVY_KEVLAR_SWAT("Schwere Kevlar", "schweren Kevlar", (configuration) -> configuration.equip().skev().getOrDefault("2200")),
    PISTOL(Weapon.P_69.getName(), Weapon.P_69.getName(), (configuration) -> configuration.equip().pistol().getOrDefault("350")),
    MP5(Weapon.SCATTER_3.getName(), Weapon.SCATTER_3.getName(), (configuration) -> configuration.equip().mp5().getOrDefault("550")),
    BANDAGES("Bandagen", "Bandagen", (configuration) -> configuration.equip().medic().bandage().getOrDefault("0")),
    PAINKILLER("Schmerzpillen", "Schmerzpillen", (configuration) -> configuration.equip().medic().pills().getOrDefault("0")),
    WATER("Wasser", "Wasser", (configuration) -> configuration.equip().water().getOrDefault("0")),
    SYRINGE("Spritzen", "Spritzen", (configuration) -> configuration.equip().medic().syringe().getOrDefault("120")),
    BREAD("Brot", "Brote", (configuration) -> configuration.equip().bread().getOrDefault("40")),
    PEPPER_SPRAY("Pfefferspray", "Pfefferspray", (configuration) -> configuration.equip().pepperSpray().getOrDefault("400")),
    FIREEXTINGUISHER("Feuerlöscher", "Feuerlöscher", (configuration) -> configuration.equip().medic().extinguisher().getOrDefault("0")),
    AXE("Feuerwehraxt", "Feuerwehraxt", (configuration) -> configuration.equip().medic().axe().getOrDefault("0")),
    HELMET("Helm", "Helm", (configuration) -> configuration.equip().medic().helmet().getOrDefault("0")),
    SWAT_SHIELD("SWAT-Schild", "Einsatzschild", (configuration) -> configuration.equip().state().shield().getOrDefault("0")),
    MASK("Maske", "Maske", (configuration) -> configuration.equip().mask().getOrDefault("800")),
    FLASHBANG("Blendgranate", "Blendgranaten", (configuration) -> configuration.equip().state().grenade().getOrDefault("250")),
    SMOKEGRENADE("Smoke", "Rauchgranate", (configuration) -> configuration.equip().state().smoke().getOrDefault("300")),
    TAZER("Tazer", "Tazer", (configuration) -> configuration.equip().state().taser().getOrDefault("0")),
    CUFFS("Handschellen", "Handschellen", (configuration) -> configuration.equip().state().cuff().getOrDefault("0")),
    WINGSUIT("Elytra", "Fallschirm", (configuration) -> configuration.equip().state().elytra().getOrDefault("450")),
    SNIPER(Weapon.VIPER_9.getName(), Weapon.VIPER_9.getName(), (configuration) -> configuration.equip().sniper().getOrDefault("2700")),
    DEFUSE_KIT("Defuse Kit", "Defuse-Kit", (configuration) -> configuration.equip().state().defuseKit().getOrDefault("500")),
    TRACKER("Peilsender", "Peilsender", (configuration) -> configuration.equip().state().tracker().getOrDefault("0")),
    EXPLOSIVEBELT("Sprenggürtel", "Sprenggürtel", (configuration) -> configuration.equip().terrorist().explosiveBelt().getOrDefault("0")),
    RPG7(Weapon.ALPHA_7.getName(), Weapon.ALPHA_7.getName(), (configuration) -> configuration.equip().terrorist().rpg7().getOrDefault("0")),
    NOTEPAD("Notizblock", "Notizblock", (configuration) -> configuration.equip().news().notepad().getOrDefault("0")), // UnicaCity Bug (Nachricht gibt es noch nicht)
    GLASSCUTTER("Glasschneider", "Glasschneider", (configuration) -> configuration.equip().hitman().glassCutter().getOrDefault("0")),
    LOCKPICK("Dietrich", "Dietrich", (configuration) -> configuration.equip().hitman().lockPick().getOrDefault("0")),
    SOUP("Suppe", "Suppe", (configuration) -> configuration.equip().church().soup().getOrDefault("2")),
    COFFEE("Kaffee", "Kaffee", (configuration) -> configuration.equip().news().coffee().getOrDefault("2"));

    private final String equipName;
    private final String messageName;
    private final Function<? super UnicacityAddonConfiguration, ? extends String> priceFunction;

    public int getPrice(UnicacityAddonConfiguration unicacityAddonConfiguration) {
        String priceString = priceFunction.apply(unicacityAddonConfiguration);
        return MathUtils.isInteger(priceString) ? Integer.parseInt(priceString) : 0;
    }
}
