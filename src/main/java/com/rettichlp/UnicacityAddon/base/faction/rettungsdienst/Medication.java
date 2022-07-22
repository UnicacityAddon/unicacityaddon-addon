package com.rettichlp.UnicacityAddon.base.faction.rettungsdienst;

public enum Medication {

    ANTIBIOTIKA,
    HUSTENSAFT,
    SCHMERZMITTEL;

    public static Medication getMedication(String s) {
        for (Medication m : Medication.values()) {
            if (s.equalsIgnoreCase(m.name())) return m;
        }
        return null;
    }
}