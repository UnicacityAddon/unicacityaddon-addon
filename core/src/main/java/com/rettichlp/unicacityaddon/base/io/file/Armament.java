package com.rettichlp.unicacityaddon.base.io.file;

import com.rettichlp.unicacityaddon.base.enums.Weapon;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public class Armament {

    private final String name;
    private final Weapon weapon;
    private final int amount;
}