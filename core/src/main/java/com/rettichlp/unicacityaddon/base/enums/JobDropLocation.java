package com.rettichlp.unicacityaddon.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public enum JobDropLocation {

    LUMBERJACK_SAWMILL_1(429, 65, 417),
    LUMBERJACK_SAWMILL_2(429, 65, 433),
    WASTE_GLAS(885, 67, 350),
    WASTE_WASTE(906, 66, 361),
    WASTE_METAL(899, 67, 394),
    WASTE_WOOD(874, 68, 378);

    final int x;
    final int y;
    final int z;
}