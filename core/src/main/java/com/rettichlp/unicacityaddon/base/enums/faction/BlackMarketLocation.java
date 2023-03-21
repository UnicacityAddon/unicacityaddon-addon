package com.rettichlp.unicacityaddon.base.enums.faction;

import net.labymod.api.util.math.vector.FloatVector3;

/**
 * @author RettichLP
 */
public enum BlackMarketLocation {
    PSYCHIATRIE("Psychiatrie", new FloatVector3(1689, 66, -390)),
    HAFEN_CHINA("Hafen (Chinatown)", new FloatVector3(1172, 69, -464)),
    HAUS_472("Haus 472 (Chinatown)", new FloatVector3(1205, 69, -118)),
    UBAHN_MEX("Mex U-Bahn", new FloatVector3(-92, 52, -33)),
    KINO("Kino (Ruine)", new FloatVector3(743, 69, 315)),
    FUSSBALLPLATZ_BALLAS("Fußballplatz (Gang)", new FloatVector3(-468, 69, 425)),
    SH_PARK("SH Park (Höhle)", new FloatVector3(64, 67, 347)),
    ALCATRAZ("Alcatraz", new FloatVector3(1154, 83, 695)),
    FLUGHAFEN_LU("Flughafen Las Unicas", new FloatVector3(1694, 69, 557)),
    SHISHABAR("Shishabar", new FloatVector3(-136, 74, -74)),
    FREIBAD("Freibad", new FloatVector3(-269, 69, -521));

    private final String displayName;
    private final FloatVector3 floatVector3;

    BlackMarketLocation(String displayName, FloatVector3 floatVector3) {
        this.displayName = displayName;
        this.floatVector3 = floatVector3;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getNaviCommand() {
        return "/navi " + floatVector3.getX() + "/" + floatVector3.getY() + "/" + floatVector3.getZ();
    }
}