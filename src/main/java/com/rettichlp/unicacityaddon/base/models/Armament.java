package com.rettichlp.unicacityaddon.base.models;

import com.rettichlp.unicacityaddon.base.enums.Weapon;

public class Armament {

    private final String name;
    private final Weapon weapon;
    private final int amount;

    public Armament(String name, Weapon weapon, int amount) {
        this.name = name;
        this.weapon = weapon;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public int getAmount() {
        return amount;
    }

}



