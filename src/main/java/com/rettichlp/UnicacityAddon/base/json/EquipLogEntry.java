package com.rettichlp.UnicacityAddon.base.json;

import com.rettichlp.UnicacityAddon.base.faction.Equip;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dimiikou
 */
public class EquipLogEntry {

    private final String type;
    private int amount;
    public static List<EquipLogEntry> equipEntry = new ArrayList<>();

    public EquipLogEntry(Equip equip, int amount) {
        this.type = equip.toString();
        this.amount = amount;
    }

    public Equip getEquip() {
        return Equip.valueOf(type);
    }

    public int getAmount() {
        return amount;
    }

    public int getPrice() {
        return getEquip().getPrice() * amount;
    }

    public void addEquip() {
        amount++;
    }
}
