package com.rettichlp.unicacityaddon.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public enum DropLocation {

    GLAS(885, 67, 350),
    WASTE(906, 66, 361),
    METAL(899, 67, 394),
    SAWMILL1(429, 65, 417),
    SAWMILL2(429, 65, 433),
    WOOD(874, 68, 378);

    final int x;
    final int y;
    final int z;
}