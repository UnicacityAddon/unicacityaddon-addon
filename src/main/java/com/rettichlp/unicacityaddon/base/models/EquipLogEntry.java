package com.rettichlp.unicacityaddon.base.models;

import com.rettichlp.unicacityaddon.base.enums.faction.Equip;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Dimiikou
 */
@AllArgsConstructor
@Getter
public class EquipLogEntry {

    private final Equip type;
    private int amount;

    public int getPrice() {
        return type.getPrice() * amount;
    }

    public void addEquip() {
        amount++;
    }
}