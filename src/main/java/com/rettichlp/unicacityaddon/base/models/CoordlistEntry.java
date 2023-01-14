package com.rettichlp.unicacityaddon.base.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CoordlistEntry {

    private String name;
    private int x;
    private int y;
    private int z;
}