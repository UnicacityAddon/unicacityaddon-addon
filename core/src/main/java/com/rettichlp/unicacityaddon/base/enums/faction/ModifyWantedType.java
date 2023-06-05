package com.rettichlp.unicacityaddon.base.enums.faction;

import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public enum ModifyWantedType {

    SURRENDER("s", " + Stellung", "x-5"),
    GOOD_CONDUCT("gf", " + Gute Führung", "x-5"),
    BAD_CONDUCT("sf", " + Schlechte Führung", "x+10"),
    VERY_BAD_CONDUCT("ssf", " + Sehr schlechte Führung", "x+15"),
    DRUG_REMOVAL_5("da5", " + Drogenabnahme", "x-5"),
    DRUG_REMOVAL_10("da10", " + Drogenabnahme", "x-10"),
    DRUG_REMOVAL_15("da15", " + Drogenabnahme", "x-15"),
    DRIVERS_LICENSE_WITHDRAWAL("fsa", " + Führerscheinabnahme", "x"),
    WEAPONS_LICENSE_WITHDRAWAL("wsa", " + Waffenscheinabnahme", "x"),
    RESISTANCE_TO_ENFORCEMENT_OFFICERS("wgv", " + Widerstand gegen Vollstreckungsbeamte", "x+5");

    private final String flagArgument;
    private final String reason;
    private final String wantedModification;

    public String modifyReason(String oldReason) {
        return oldReason + this.reason;
    }

    public int modifyWanteds(int wanteds) {
        return (int) new MathUtils(wantedModification.replace("x", String.valueOf(wanteds))).evaluate();
    }

    public static ModifyWantedType getModifyWantedType(String string) {
        for (ModifyWantedType type : ModifyWantedType.values()) {
            if (type.flagArgument.equalsIgnoreCase(string))
                return type;
        }

        return null;
    }
}