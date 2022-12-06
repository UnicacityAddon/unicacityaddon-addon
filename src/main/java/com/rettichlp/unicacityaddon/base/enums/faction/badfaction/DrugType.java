package com.rettichlp.unicacityaddon.base.enums.faction.badfaction;

import java.util.Arrays;

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

    public static DrugType getDrugTypeByNameOrShortName(String drugName) {
        return Arrays.stream(DrugType.values())
                .filter(drugType -> drugType.drugName.equalsIgnoreCase(drugName) || drugType.shortName.equalsIgnoreCase(drugName))
                .findFirst().orElseThrow(() -> new RuntimeException("DrugType does not exist"));
    }
}