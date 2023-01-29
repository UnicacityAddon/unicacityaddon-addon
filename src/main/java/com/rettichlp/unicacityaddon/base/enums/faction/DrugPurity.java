package com.rettichlp.unicacityaddon.base.enums.faction;

import com.rettichlp.unicacityaddon.base.utils.MathUtils;

import java.util.Arrays;

public enum DrugPurity {

    BEST("Höchste", 0), GOOD("Gute", 1), MEDIUM("Mittlere", 2), BAD("Schlechte", 3);

    private final String purityString;
    private final int purity;

    DrugPurity(String purityString, int purity) {
        this.purityString = purityString;
        this.purity = purity;
    }

    public String getPurityString() {
        return purityString;
    }

    public int getPurity() {
        return purity;
    }

    public static DrugPurity getDrugPurity(String drugPurity) {
        return MathUtils.isInteger(drugPurity) ? getDrugPurityByInteger(Integer.parseInt(drugPurity)) : getDrugPurityByString(drugPurity);
    }

    private static DrugPurity getDrugPurityByString(String drugPurity) {
        return Arrays.stream(DrugPurity.values())
                .filter(drugPurity1 -> drugPurity.equalsIgnoreCase(drugPurity1.getPurityString()))
                .findFirst().orElseThrow(() -> new RuntimeException("DrugPurity does not exist"));
    }

    public static DrugPurity getDrugPurityByInteger(int drugPurity) {
        return drugPurity > 4 ? DrugPurity.BEST : values()[drugPurity];
    }
}
