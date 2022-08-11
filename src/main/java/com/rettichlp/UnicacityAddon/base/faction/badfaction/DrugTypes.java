package com.rettichlp.UnicacityAddon.base.faction.badfaction;

public enum DrugTypes {

    COCAINE("Kokain"), MARIJUANA("Marihuana"), METH("Methamphetamin"), LSD("LSD");

    private final String drugName;

    DrugTypes(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugName() {
        return drugName;
    }
}
