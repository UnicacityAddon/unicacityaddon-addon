package com.rettichlp.unicacityaddon.base.enums.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.config.DefaultUnicacityAddonConfiguration;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;

import java.util.function.Function;

public enum Equip {

    BASI("Baseballschläger", "Baseballschläger", (configuration) -> configuration.equipSetting().badFactionSetting().baseballBat().getOrDefault("0")),
    DONUT("Donut", "Donuts", (configuration) -> configuration.equipSetting().donut().getOrDefault("30")),
    KEVLAR("Leichte Kevlar", "Kevlar", (configuration) -> configuration.equipSetting().lkev().getOrDefault("1450")),
    HEAVYKEVLAR("Schwere Kevlar", "schwere Kevlar", (configuration) -> configuration.equipSetting().skev().getOrDefault("2200")),
    PISTOL("Pistole", "Pistole", (configuration) -> configuration.equipSetting().donut().getOrDefault("350")),
    MP5("MP5", "MP5", (configuration) -> configuration.equipSetting().donut().getOrDefault("550")),
    BANDAGES("Bandagen", "Bandagen", (configuration) -> configuration.equipSetting().medicSetting().bandage().getOrDefault("0")),
    PAINKILLER("Schmerzpillen", "Schmerzpillen", (configuration) -> configuration.equipSetting().medicSetting().pills().getOrDefault("0")),
    WATER("Wasser", "Wasser", (configuration) -> configuration.equipSetting().water().getOrDefault("0")),
    SYRINGE("Spritzen", "Spritzen", (configuration) -> configuration.equipSetting().medicSetting().syringe().getOrDefault("120")),
    BREAD("Brot", "Brote", (configuration) -> configuration.equipSetting().bread().getOrDefault("40")),
    PEPPERSPRAY("Pfefferspray", "Pfefferspray", (configuration) -> configuration.equipSetting().pepperSpray().getOrDefault("400")),
    FIREEXTINGUISHER("Feuerlöscher", "Feuerlöscher", (configuration) -> configuration.equipSetting().medicSetting().extinguisher().getOrDefault("0")),
    AXE("Feuerwehraxt", "Feuerwehraxt", (configuration) -> configuration.equipSetting().medicSetting().axe().getOrDefault("0")),
    HELMET("Helm", "Helm", (configuration) -> configuration.equipSetting().medicSetting().helmet().getOrDefault("0")),
    SWATSHIELD("SWAT-Schild", "Einsatzschild", (configuration) -> configuration.equipSetting().stateSetting().shield().getOrDefault("0")),
    MASK("Maske", "Maske", (configuration) -> configuration.equipSetting().mask().getOrDefault("800")),
    FLASHBANG("Blendgranate", "Blendgranate", (configuration) -> configuration.equipSetting().stateSetting().grenade().getOrDefault("250")),
    SMOKEGRENADE("Smoke", "Rauchgranate", (configuration) -> configuration.equipSetting().stateSetting().smoke().getOrDefault("300")),
    TAZER("Tazer", "Tazer", (configuration) -> configuration.equipSetting().stateSetting().taser().getOrDefault("0")),
    CUFFS("Handschellen", "Handschellen", (configuration) -> configuration.equipSetting().stateSetting().cuff().getOrDefault("0")),
    WINGSUIT("Elytra", "Fallschirm", (configuration) -> configuration.equipSetting().stateSetting().elytra().getOrDefault("450")),
    SNIPER("Sniper", "Sniper", (configuration) -> configuration.equipSetting().sniper().getOrDefault("2700")),
    DEFUSEKIT("Defuse Kit", "Defuse-Kit", (configuration) -> configuration.equipSetting().stateSetting().defuseKit().getOrDefault("500")),
    TRACKER("Peilsender", "Peilsender", (configuration) -> configuration.equipSetting().stateSetting().tracker().getOrDefault("0")),
    EXPLOSIVEBELT("Sprenggürtel", "Sprenggürtel", (configuration) -> configuration.equipSetting().terrorSetting().explosiveBelt().getOrDefault("0")),
    RPG7("RPG-7", "RPG-7", (configuration) -> configuration.equipSetting().terrorSetting().rpg7().getOrDefault("0")),
    NOTEPAD("Notizblock", "Notizblock", (configuration) -> configuration.equipSetting().newsSetting().notepad().getOrDefault("0")), // UnicaCity Bug (Nachricht gibt es noch nicht)
    GLASSCUTTER("Glasschneider", "Glasschneider", (configuration) -> configuration.equipSetting().hitmanSetting().glassCutter().getOrDefault("0")), // TODO: Equipname
    LOCKPICK("Dietrich", "Dietrich", (configuration) -> configuration.equipSetting().hitmanSetting().lockPick().getOrDefault("0")); // TODO: Equipname

    private final String equipName;
    private final String messageName;
    private final Function<? super DefaultUnicacityAddonConfiguration, ? extends String> priceFunction;

    Equip(String equipName, String messageName, Function<? super DefaultUnicacityAddonConfiguration, ? extends String> function) {
        this.equipName = equipName;
        this.messageName = messageName;
        this.priceFunction = function;
    }

    public String getName() {
        return equipName;
    }

    public String getMessageName() {
        return messageName;
    }

    public int getPrice() {
        String priceString = priceFunction.apply(UnicacityAddon.ADDON.configuration());
        return MathUtils.isInteger(priceString) ? Integer.parseInt(priceString) : 0;
    }
}
