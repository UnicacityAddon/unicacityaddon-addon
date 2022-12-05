package com.rettichlp.unicacityaddon.base.enums.faction.badfaction;

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
}
