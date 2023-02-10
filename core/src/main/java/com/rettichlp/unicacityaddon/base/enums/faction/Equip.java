package com.rettichlp.unicacityaddon.base.enums.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;

public enum Equip {

    BASI("Baseballschläger", "Baseballschläger", UnicacityAddon.ADDON.configuration().equipSetting().badFactionSetting().baseballBat().getOrDefault("0")),
    DONUT("Donut", "Donuts", UnicacityAddon.ADDON.configuration().equipSetting().donut().getOrDefault("30")),
    KEVLAR("Leichte Kevlar", "Kevlar", UnicacityAddon.ADDON.configuration().equipSetting().lkev().getOrDefault("1450")),
    HEAVYKEVLAR("Schwere Kevlar", "schwere Kevlar", UnicacityAddon.ADDON.configuration().equipSetting().skev().getOrDefault("2200")),
    PISTOL("Pistole", "Pistole", UnicacityAddon.ADDON.configuration().equipSetting().donut().getOrDefault("350")),
    MP5("MP5", "MP5", UnicacityAddon.ADDON.configuration().equipSetting().donut().getOrDefault("550")),
    BANDAGES("Bandagen", "Bandagen", UnicacityAddon.ADDON.configuration().equipSetting().medicSetting().bandage().getOrDefault("0")),
    PAINKILLER("Schmerzpillen", "Schmerzpillen", UnicacityAddon.ADDON.configuration().equipSetting().medicSetting().pills().getOrDefault("0")),
    WATER("Wasser", "Wasser", UnicacityAddon.ADDON.configuration().equipSetting().water().getOrDefault("0")),
    SYRINGE("Spritzen", "Spritzen", UnicacityAddon.ADDON.configuration().equipSetting().medicSetting().syringe().getOrDefault("120")),
    BREAD("Brot", "Brote", UnicacityAddon.ADDON.configuration().equipSetting().bread().getOrDefault("40")),
    PEPPERSPRAY("Pfefferspray", "Pfefferspray", UnicacityAddon.ADDON.configuration().equipSetting().pepperSpray().getOrDefault("400")),
    FIREEXTINGUISHER("Feuerlöscher", "Feuerlöscher", UnicacityAddon.ADDON.configuration().equipSetting().medicSetting().extinguisher().getOrDefault("0")),
    AXE("Feuerwehraxt", "Feuerwehraxt", UnicacityAddon.ADDON.configuration().equipSetting().medicSetting().axe().getOrDefault("0")),
    HELMET("Helm", "Helm", UnicacityAddon.ADDON.configuration().equipSetting().medicSetting().helmet().getOrDefault("0")),
    SWATSHIELD("SWAT-Schild", "Einsatzschild", UnicacityAddon.ADDON.configuration().equipSetting().stateSetting().shield().getOrDefault("0")),
    MASK("Maske", "Maske", UnicacityAddon.ADDON.configuration().equipSetting().mask().getOrDefault("800")),
    FLASHBANG("Blendgranate", "Blendgranate", UnicacityAddon.ADDON.configuration().equipSetting().stateSetting().grenade().getOrDefault("250")),
    SMOKEGRENADE("Smoke", "Rauchgranate", UnicacityAddon.ADDON.configuration().equipSetting().stateSetting().smoke().getOrDefault("300")),
    TAZER("Tazer", "Tazer", UnicacityAddon.ADDON.configuration().equipSetting().stateSetting().taser().getOrDefault("0")),
    CUFFS("Handschellen", "Handschellen", UnicacityAddon.ADDON.configuration().equipSetting().stateSetting().cuff().getOrDefault("0")),
    WINGSUIT("Elytra", "Fallschirm", UnicacityAddon.ADDON.configuration().equipSetting().stateSetting().elytra().getOrDefault("450")),
    SNIPER("Sniper", "Sniper", UnicacityAddon.ADDON.configuration().equipSetting().sniper().getOrDefault("2700")),
    DEFUSEKIT("Defuse Kit", "Defuse-Kit", UnicacityAddon.ADDON.configuration().equipSetting().stateSetting().defuseKit().getOrDefault("500")),
    TRACKER("Peilsender", "Peilsender", UnicacityAddon.ADDON.configuration().equipSetting().stateSetting().tracker().getOrDefault("0")),
    EXPLOSIVEBELT("Sprenggürtel", "Sprenggürtel", UnicacityAddon.ADDON.configuration().equipSetting().terrorSetting().explosiveBelt().getOrDefault("0")),
    RPG7("RPG-7", "RPG-7", UnicacityAddon.ADDON.configuration().equipSetting().terrorSetting().rpg7().getOrDefault("0")),
    NOTEPAD("Notizblock", "Notizblock", UnicacityAddon.ADDON.configuration().equipSetting().newsSetting().notepad().getOrDefault("0")), // UnicaCity Bug (Nachricht gibt es noch nicht)
    GLASSCUTTER("Glasschneider", "Glasschneider", UnicacityAddon.ADDON.configuration().equipSetting().hitmanSetting().glassCutter().getOrDefault("0")), // TODO: Equipname
    LOCKPICK("Dietrich", "Dietrich", UnicacityAddon.ADDON.configuration().equipSetting().hitmanSetting().lockPick().getOrDefault("0")); // TODO: Equipname

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
