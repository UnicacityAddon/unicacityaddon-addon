package com.rettichlp.unicacityaddon.base.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BlackMarketLocation {

    private final String name;
    private final int x;
    private final int y;
    private final int z;
}