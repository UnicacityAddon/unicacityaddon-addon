package com.rettichlp.unicacityaddon.base.enums.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;

public enum Equip {

    BASI("Baseballschläger", "Baseballschläger", UnicacityAddon.configuration.equipSetting().badFactionSetting().baseballBat().getOrDefault("0")),
    DONUT("Donut", "Donuts", UnicacityAddon.configuration.equipSetting().donut().getOrDefault("30")),
    KEVLAR("Leichte Kevlar", "Kevlar", UnicacityAddon.configuration.equipSetting().lkev().getOrDefault("1450")),
    HEAVYKEVLAR("Schwere Kevlar", "schwere Kevlar", UnicacityAddon.configuration.equipSetting().skev().getOrDefault("2200")),
    PISTOL("Pistole", "Pistole", UnicacityAddon.configuration.equipSetting().donut().getOrDefault("350")),
    MP5("MP5", "MP5", UnicacityAddon.configuration.equipSetting().donut().getOrDefault("550")),
    BANDAGES("Bandagen", "Bandagen", UnicacityAddon.configuration.equipSetting().medicSetting().bandage().getOrDefault("0")),
    PAINKILLER("Schmerzpillen", "Schmerzpillen", UnicacityAddon.configuration.equipSetting().medicSetting().pills().getOrDefault("0")),
    WATER("Wasser", "Wasser", UnicacityAddon.configuration.equipSetting().water().getOrDefault("0")),
    SYRINGE("Spritzen", "Spritzen", UnicacityAddon.configuration.equipSetting().medicSetting().syringe().getOrDefault("120")),
    BREAD("Brot", "Brote", UnicacityAddon.configuration.equipSetting().bread().getOrDefault("40")),
    PEPPERSPRAY("Pfefferspray", "Pfefferspray", UnicacityAddon.configuration.equipSetting().pepperSpray().getOrDefault("400")),
    FIREEXTINGUISHER("Feuerlöscher", "Feuerlöscher", UnicacityAddon.configuration.equipSetting().medicSetting().extinguisher().getOrDefault("0")),
    AXE("Feuerwehraxt", "Feuerwehraxt", UnicacityAddon.configuration.equipSetting().medicSetting().axe().getOrDefault("0")),
    HELMET("Helm", "Helm", UnicacityAddon.configuration.equipSetting().medicSetting().helmet().getOrDefault("0")),
    SWATSHIELD("SWAT-Schild", "Einsatzschild", UnicacityAddon.configuration.equipSetting().stateSetting().shield().getOrDefault("0")),
    MASK("Maske", "Maske", UnicacityAddon.configuration.equipSetting().mask().getOrDefault("800")),
    FLASHBANG("Blendgranate", "Blendgranate", UnicacityAddon.configuration.equipSetting().stateSetting().grenade().getOrDefault("250")),
    SMOKEGRENADE("Smoke", "Rauchgranate", UnicacityAddon.configuration.equipSetting().stateSetting().smoke().getOrDefault("300")),
    TAZER("Tazer", "Tazer", UnicacityAddon.configuration.equipSetting().stateSetting().taser().getOrDefault("0")),
    CUFFS("Handschellen", "Handschellen", UnicacityAddon.configuration.equipSetting().stateSetting().cuff().getOrDefault("0")),
    WINGSUIT("Elytra", "Fallschirm", UnicacityAddon.configuration.equipSetting().stateSetting().elytra().getOrDefault("450")),
    SNIPER("Sniper", "Sniper", UnicacityAddon.configuration.equipSetting().sniper().getOrDefault("2700")),
    DEFUSEKIT("Defuse Kit", "Defuse-Kit", UnicacityAddon.configuration.equipSetting().stateSetting().defuseKit().getOrDefault("500")),
    TRACKER("Peilsender", "Peilsender", UnicacityAddon.configuration.equipSetting().stateSetting().tracker().getOrDefault("0")),
    EXPLOSIVEBELT("Sprenggürtel", "Sprenggürtel", UnicacityAddon.configuration.equipSetting().terrorSetting().explosiveBelt().getOrDefault("0")),
    RPG7("RPG-7", "RPG-7", UnicacityAddon.configuration.equipSetting().terrorSetting().rpg7().getOrDefault("0")),
    NOTEPAD("Notizblock", "Notizblock", UnicacityAddon.configuration.equipSetting().newsSetting().notepad().getOrDefault("0")), // UnicaCity Bug (Nachricht gibt es noch nicht)
    GLASSCUTTER("Glasschneider", "Glasschneider", UnicacityAddon.configuration.equipSetting().hitmanSetting().glassCutter().getOrDefault("0")), // TODO: Equipname
    LOCKPICK("Dietrich", "Dietrich", UnicacityAddon.configuration.equipSetting().hitmanSetting().lockPick().getOrDefault("0")); // TODO: Equipname

    private final String equipName;
    private final String messageName;
    private final String price;

    Equip(String equipName, String messageName, String price) {
        this.equipName = equipName;
        this.messageName = messageName;
        this.price = price;
    }

    public String getName() {
        return equipName;
    }

    public String getMessageName() {
        return messageName;
    }

    public int getPrice() {
        return MathUtils.isInteger(price) ? Integer.parseInt(price) : 0;
    }
}
