package com.rettichlp.unicacityaddon.base.events;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.Weapon;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import lombok.Getter;
import net.labymod.api.event.Event;

/**
 * @author RettichLP
 */
@Getter
public class WeaponShotEvent implements Event {

    private final Weapon weapon;
    private final int loaded;
    private final int backup;

    public WeaponShotEvent(UnicacityAddon unicacityAddon, Weapon weapon) {
        this.weapon = weapon;
        this.loaded = weapon.getLoadedAmmunition(unicacityAddon);
        this.backup = weapon.getBackupAmmunition(unicacityAddon);
    }

    public String getWeaponAmmunitionText() {
        return Message.getBuilder()
                .of(String.valueOf(Math.max(loaded, 0))).color(loaded == 0 ? ColorCode.RED : ColorCode.GOLD).advance()
                .of("/").color(ColorCode.DARK_GRAY).advance()
                .of(String.valueOf(backup)).color(ColorCode.GOLD).advance()
                .create();
    }
}