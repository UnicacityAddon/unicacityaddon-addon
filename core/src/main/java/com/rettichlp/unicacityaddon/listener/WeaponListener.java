package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.enums.Weapon;
import com.rettichlp.unicacityaddon.base.events.HotkeyEvent;
import com.rettichlp.unicacityaddon.base.events.WeaponUpdateEvent;
import net.labymod.api.client.gui.screen.key.Key;
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
            runDelayed(weapon);
        }
    }

    @Subscribe
    public void onHotkey(HotkeyEvent e) {
        Key key = e.getKey();

        Weapon weapon = this.unicacityAddon.player().getWeaponInMainHand();
        if (key.equals(Key.Q) && weapon != null) {
            runDelayed(weapon);
        }
    }

    private void runDelayed(Weapon weapon) {
        new Thread(() -> {
            try {
                Thread.sleep(220);
                this.unicacityAddon.labyAPI().eventBus().fire(new WeaponUpdateEvent(this.unicacityAddon, weapon));
            } catch (InterruptedException e) {
                this.unicacityAddon.logger().warn("Delayed execution of weapon update failed");
            }
        }).start();
    }
}