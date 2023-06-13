package com.rettichlp.unicacityaddon.base.enums.faction;

import com.rettichlp.unicacityaddon.base.config.ConfigElements;

public enum Equip {

    BASI("Baseballschläger", "Baseballschläger", ConfigElements.getBaseballBatPrice()),
    DONUT("Donut", "Donuts", ConfigElements.getDonutPrice()),
    KEVLAR("Leichte Kevlar", "Kevlar", ConfigElements.getLightKevlarPrice()),
    HEAVY_KEVLAR("Schwere Kevlar", "schwere Kevlar", ConfigElements.getHeavyKevlarPrice()),
    PISTOL("Pistole", "Pistole", ConfigElements.getPistolPrice()),
    MP5("MP5", "MP5", ConfigElements.getMP5Price()),
    BANDAGES("Bandagen", "Bandagen", ConfigElements.getBandagePrice()),
    PAINKILLER("Schmerzpillen", "Schmerzpillen", ConfigElements.getPainKillerPrice()),
    WATER("Wasser", "Wasser", ConfigElements.getWaterPrice()),
    SYRINGE("Spritzen", "Spritzen", ConfigElements.getSyringePrice()),
    BREAD("Brot", "Brote", ConfigElements.getBreadPrice()),
    PEPPER_SPRAY("Pfefferspray", "Pfefferspray", ConfigElements.getPeppersprayPrice()),
    FIREEXTINGUISHER("Feuerlöscher", "Feuerlöscher", ConfigElements.getFireExtinguisherPrice()),
    AXE("Feuerwehraxt", "Feuerwehraxt", ConfigElements.getAxePrice()),
    HELMET("Helm", "Helm", ConfigElements.getHelmetPrice()),
    SWAT_SHIELD("SWAT-Schild", "Einsatzschild", ConfigElements.getSwatShieldPrice()),
    MASK("Maske", "Maske", ConfigElements.getMaskPrice()),
    FLASHBANG("Blendgranate", "Blendgranate", ConfigElements.getFlashBangPrice()),
    SMOKEGRENADE("Smoke", "Rauchgranate", ConfigElements.getSmokeGrenadePrice()),
    TAZER("Tazer", "Tazer", ConfigElements.getTazerPrice()),
    CUFFS("Handschellen", "Handschellen", ConfigElements.getHandCuffPrice()),
    WINGSUIT("Elytra", "Fallschirm", ConfigElements.getWingsuitPrice()),
    SNIPER("Sniper", "Sniper", ConfigElements.getSniperPrice()),
    DEFUSE_KIT("Defuse Kit", "Defuse-Kit", ConfigElements.getDefuseKitPrice()),
    TRACKER("Peilsender", "Peilsender", ConfigElements.getTrackerPrice()),
    EXPLOSIVEBELT("Sprenggürtel", "Sprenggürtel", ConfigElements.getExplosiveBeltPrice()),
    RPG7("RPG-7", "RPG-7", ConfigElements.getRPG7Price()),
    NOTEPAD("Notizblock", "Notizblock", ConfigElements.getNotePadPrice()), // UnicaCity Bug (Nachricht gibt es noch nicht)
    GLASSCUTTER("Glasschneider", "Glasschneider", ConfigElements.getGlassCutterPrice()), // TODO: Equipname
    LOCKPICK("Dietrich", "Dietrich", ConfigElements.getLockPickPrice()), // TODO: Equipname
    WINE("Wein", "Wein", ConfigElements.getWinePrice()),
    COFFEE("Kaffee", "Kaffee", ConfigElements.getCoffeePrice()); // TODO: Equipname

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
        return Integer.parseInt(price);
    }
}
