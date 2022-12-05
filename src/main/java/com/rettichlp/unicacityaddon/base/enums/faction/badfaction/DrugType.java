package com.rettichlp.unicacityaddon.base.enums.faction.badfaction;

public enum DrugType {

    COCAINE("Kokain", "Koks"), MARIJUANA("Marihuana", "Gras"), METH("Methamphetamin", "Meth"), LSD("LSD", "LSD");

    private final String drugName;
    private final String shortName;

    DrugType(String drugName, String shortName) {
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
