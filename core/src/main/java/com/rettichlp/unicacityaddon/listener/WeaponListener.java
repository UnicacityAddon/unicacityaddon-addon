package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.Weapon;
import com.rettichlp.unicacityaddon.base.events.WeaponShotEvent;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import com.rettichlp.unicacityaddon.commands.GetGunPatternCommand;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.entity.player.ClientPlayerInteractEvent;
import net.labymod.api.event.client.render.ScreenRenderEvent;

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

    @Subscribe
    public void onScreenRender(ScreenRenderEvent e) {
        if (GetGunPatternCommand.armament != null) {
            Weapon weapon = GetGunPatternCommand.armament.getWeapon();
            String unformattedWeaponDisplayName = TextUtils.stripColor(weapon.getDisplayName());
            int weaponSlotNumber = this.unicacityAddon.guiController().getSlotNumberByDisplayName(unformattedWeaponDisplayName);

            this.unicacityAddon.guiController().inventoryClick(this.unicacityAddon, weaponSlotNumber);
            this.unicacityAddon.player().sendServerMessage("/getammo " + weapon.getName() + " " + GetGunPatternCommand.armament.getAmount());
            GetGunPatternCommand.armament = null;
        }
    }
}