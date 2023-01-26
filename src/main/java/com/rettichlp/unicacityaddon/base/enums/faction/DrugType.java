package com.rettichlp.unicacityaddon.base.enums.faction;

import java.util.Arrays;

public enum DrugType {

    COCAINE("Kokain", "Koks", false),
    MARIJUANA("Marihuana", "Gras", false),
    METH("Methamphetamin", "Meth", false),
    LSD("LSD", "LSD", false),
    ANTIBIOTIKA("Antibiotika", null, true),
    HUSTENSAFT("Hustensaft", null, true),
    SCHMERZMITTEL("Schmerzmittel", "Schmerzpillen", true);

    private final String drugName;
    private final String shortName;
    private final boolean legal;

    DrugType(String drugName, String shortName, boolean legal) {
        this.drugName = drugName;
        this.shortName = shortName;
        this.legal = legal;
    }

    public String getDrugName() {
        return drugName;
    }

    public String getShortName() {
        return shortName;
    }

    public boolean isLegal() {
        return legal;
    }

    public static DrugType getDrugType(String s) {
        return Arrays.stream(DrugType.values())
                .filter(drugType -> s.equalsIgnoreCase(drugType.drugName) || s.equalsIgnoreCase(drugType.shortName))
                .findFirst().orElseThrow(() -> new RuntimeException("DrugType does not exist"));
    }
}