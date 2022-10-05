package com.rettichlp.UnicacityAddon.base.faction.badfaction;

public enum DrugTypes {

    COCAINE("Kokain", "Koks"), MARIJUANA("Marihuana", "Gras"), METH("Methamphetamin", "Meth"), LSD("LSD", "LSD");

    private final String drugName;
    private final String shortName;

    DrugTypes(String drugName, String shortName) {
        this.drugName = drugName;
        this.shortName = shortName;
    }

    public String getDrugName() {
        return drugName;
    }

    public String getShortName() {
        return shortName;
    }
}
