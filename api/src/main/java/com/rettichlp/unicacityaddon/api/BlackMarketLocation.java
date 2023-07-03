package com.rettichlp.unicacityaddon.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public class BlackMarketLocation {

    private final String name;
    private final int x;
    private final int y;
    private final int z;
}