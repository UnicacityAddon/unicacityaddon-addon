package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.enums.Weapon;
import com.rettichlp.unicacityaddon.base.events.WeaponShotEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.entity.player.ClientPlayerInteractEvent;

/**
 * @author RettichLP
 */
@UCEvent
public class WeaponListener {

    private final UnicacityAddon unicacityAddon;

    public WeaponListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onClientPlayerInteract(ClientPlayerInteractEvent e) {
        Weapon weapon = this.unicacityAddon.player().getWeaponInMainHand();
        if (weapon != null) {
            this.unicacityAddon.labyAPI().eventBus().fire(new WeaponShotEvent(this.unicacityAddon, weapon));
        }
    }
}