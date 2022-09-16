package com.rettichlp.UnicacityAddon.base.aktien;

public enum Aktie {

    HANKYS("Hanky's Elektroladen", "Hankys-Elektroladen"),
    CFK("CFK", "CFK"),
    AUTOHAENDLER("Autohändler", "Autohändler"),
    AUTOHAENDLER_LUXUS("Luxus-Autohändler", "Luxus-Autohändler"),
    SUPERMARKT("Supermarkt", "Supermarkt"),
    DISCO("Disco", "Disco"),
    SHISHABAR("Shishabar", "Shishabar"),
    BANK("Bank", "Bank"),
    HAUSADDON_SHOP("Hausaddon-Shop", "Hausaddon-Shop");

    private final String itemName;
    private final String tabName;

    Aktie(String itemName, String tabName) {
        this.itemName = itemName;
        this.tabName = tabName;
    }

    public String getItemName() {
        return itemName;
    }

    public String getTabName() {
        return tabName;
    }
}