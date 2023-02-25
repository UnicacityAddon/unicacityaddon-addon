package com.rettichlp.unicacityaddon.base.events;

import com.rettichlp.unicacityaddon.base.enums.Weapon;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.event.Event;

public class WeaponShotEvent implements Event {

    private final Weapon weapon;
    private final int loaded;
    private final int backup;

    public WeaponShotEvent(Weapon weapon, int loaded, int backup) {
        this.weapon = weapon;
        this.loaded = loaded;
        this.backup = backup;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public int getLoadedAmmunition() {
        return loaded;
    }

    public int getBackupAmmunition() {
        return backup;
    }

    public String getWeaponAmmunitionText() {
        return Message.getBuilder()
                .of(String.valueOf(Math.max(loaded, 0))).color(loaded == 0 ? ColorCode.RED : ColorCode.GOLD).advance()
                .of("/").color(ColorCode.DARK_GRAY).advance()
                .of(String.valueOf(backup)).color(ColorCode.GOLD).advance()
                .create();
    }
}