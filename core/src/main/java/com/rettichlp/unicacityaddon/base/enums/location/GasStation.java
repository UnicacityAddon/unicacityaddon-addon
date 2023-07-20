package com.rettichlp.unicacityaddon.base.enums.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.labymod.api.util.math.vector.FloatVector3;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public enum GasStation {

    MEXICAN(-171, 69, 4),
    CHURCH(232, 69, -145),
    FIRE_DEPARTMENT(-186, 69, -352),
    CHINATOWN(1100, 69, -205);

    private final int x;
    private final int y;
    private final int z;

    public String getNaviCommand() {
        return "/navi " + x + "/" + y + "/" + z;
    }

    public FloatVector3 getLocation() {
        return new FloatVector3(x, y, z);
    }
}