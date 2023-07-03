package com.rettichlp.unicacityaddon.base.enums.faction;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public enum DrugType {

    COCAINE("Pulver", "Koks", false),
    MARIJUANA("Kräuter", "Gras", false),
    METH("Kristalle", "Meth", false),
    LSD("Wundertüte", "LSD", false),
    IRON("Eisen", null, false),
    MASK("Maske", null, false),
    GUNPOWDER("Schwarzpulver", null, false),
    ANTIBIOTIKA("Antibiotika", null, true),
    HUSTENSAFT("Hustensaft", null, true),
    SCHMERZMITTEL("Schmerzmittel", "Schmerzpillen", true);

    private final String drugName;
    private final String shortName;
    private final boolean legal;

    public static DrugType getDrugType(String s) {
        return Arrays.stream(DrugType.values())
                .filter(drugType -> s.contains(drugType.drugName) || (drugType.shortName != null && s.contains(drugType.shortName)))
                .findFirst()
                .orElse(null);
    }
}