package com.rettichlp.UnicacityAddon.base.json;

import com.rettichlp.UnicacityAddon.base.faction.Equip;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dimiikou
 */
public class EquipLogEntry {

    private final Equip equip;
    private int amount;
    public static List<EquipLogEntry> equipEntry = new ArrayList<>();

    public EquipLogEntry(Equip equip, int amount) {
        this.equip = equip;
        this.amount = amount;
    }

    public Equip getEquip() {
        return equip;
    }

    public int getAmount() {
        return amount;
    }

    public int getPrice() {
        return equip.getPrice() * amount;
    }

    public void addEquip() {
        amount++;
    }
}
