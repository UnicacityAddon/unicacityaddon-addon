package com.rettichlp.unicacityaddon.base.enums.faction;

import com.rettichlp.unicacityaddon.base.services.utils.MathUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public enum DrugPurity {

    BEST("Höchste", 0), GOOD("Gute", 1), MEDIUM("Mittlere", 2), BAD("Schlechte", 3);

    private final String purityString;
    private final int purity;

    public static DrugPurity getDrugPurity(String drugPurity) {
        return MathUtils.isInteger(drugPurity) ? getDrugPurityByInteger(Integer.parseInt(drugPurity)) : getDrugPurityByString(drugPurity);
    }

    private static DrugPurity getDrugPurityByString(String s) {
        return Arrays.stream(DrugPurity.values())
                .filter(drugPurity -> s.contains(drugPurity.getPurityString()))
                .findFirst().orElseThrow(() -> new RuntimeException("DrugPurity does not exist"));
    }

    public static DrugPurity getDrugPurityByInteger(int drugPurity) {
        return drugPurity > 4 ? DrugPurity.BEST : values()[drugPurity];
    }
}
