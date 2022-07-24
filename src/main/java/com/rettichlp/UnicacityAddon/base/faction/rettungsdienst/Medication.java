package com.rettichlp.UnicacityAddon.base.faction.rettungsdienst;

public enum Medication {

    ANTIBIOTIKA("Antibiotika"),
    HUSTENSAFT("Hustensaft"),
    SCHMERZMITTEL("Schmerzmittel");

    private final String displayName;

    Medication(String displayName) {
        this.displayName = displayName;
    }

    public static Medication getMedication(String s) {
        for (Medication m : Medication.values()) {
            if (s.equalsIgnoreCase(m.name())) return m;
        }
        return null;
    }

    public String getDisplayName() {
        return displayName;
    }
}