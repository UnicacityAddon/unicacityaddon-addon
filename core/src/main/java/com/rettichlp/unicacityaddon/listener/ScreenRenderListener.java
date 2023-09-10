package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.Weapon;
import com.rettichlp.unicacityaddon.base.events.HotkeyEvent;
import com.rettichlp.unicacityaddon.base.events.UnicacityAddonTickEvent;
import com.rettichlp.unicacityaddon.base.gangzones.AbstractAttackableGangzone;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.commands.GetGunPatternCommand;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.ScreenRenderEvent;
import net.labymod.api.event.client.render.world.RenderWorldEvent;
import net.labymod.api.event.client.world.ItemStackTooltipEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author RettichLP
 */
@UCEvent
public class ScreenRenderListener {

    public static int lastHoveredSlotNumber = -1;
    public static List<Integer> settingPath = new ArrayList<>();
    private boolean showGangzones = false;

    private final UnicacityAddon unicacityAddon;

    public ScreenRenderListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onScreenRender(ScreenRenderEvent e) {
        this.unicacityAddon.transportController().carInteract();
        this.unicacityAddon.transportController().processBusRouting(this.unicacityAddon);
        this.unicacityAddon.guiController().updateSetting(true);

        if (GetGunPatternCommand.armament != null && System.currentTimeMillis() - GetGunPatternCommand.startTime < TimeUnit.SECONDS.toMillis(5)) {
            Weapon weapon = GetGunPatternCommand.armament.getWeapon();
            int weaponSlotNumber = this.unicacityAddon.guiController().getSlotNumberByDisplayName(weapon.getName());

            this.unicacityAddon.guiController().inventoryClick(weaponSlotNumber);
            this.unicacityAddon.player().sendServerMessage("/getammo " + weapon.getName() + " " + GetGunPatternCommand.armament.getAmount());
            GetGunPatternCommand.armament = null;
        }

        if (this.unicacityAddon.utilService().command().isActiveDrugInventoryLoading()) {
            this.unicacityAddon.guiController().updateDrugInventoryMap(this.unicacityAddon);
        }
    }

    /**
     * Quote: "Ohhhh ein Slime Daddy!" - Liiluu, 04.09.2023
     */
    @Subscribe
    public void onRenderWorld(RenderWorldEvent e) {
        if (this.unicacityAddon.utilService().isUnicacity() && this.showGangzones) {
            this.unicacityAddon.registry().gangzones().forEach(gangzone -> {
                gangzone.renderGangzoneArea();

                if (gangzone instanceof AbstractAttackableGangzone attackableGangzone) {
                    attackableGangzone.renderGangwarArea();
                }
            });
        }
    }

    @Subscribe
    public void onItemStackTooltip(ItemStackTooltipEvent e) {
        ItemStack itemStack = e.itemStack();
        lastHoveredSlotNumber = this.unicacityAddon.guiController().getSlotNumberByDisplayName(this.unicacityAddon.utilService().text().plain(itemStack.getDisplayName()));
    }

    @Subscribe
    public void onUnicacityAddonTick(UnicacityAddonTickEvent e) {
        if (e.isUnicacity() && e.isPhase(UnicacityAddonTickEvent.Phase.SECOND) && this.unicacityAddon.configuration().nametag().corpse().get()) {
            this.unicacityAddon.deadBodyController().updateDisplayName(this.unicacityAddon);
        }
    }

    @Subscribe
    public void onHotkey(HotkeyEvent e) {
        Key key = e.getKey();

        if (key.equals(this.unicacityAddon.configuration().hotkey().showGangzones().get()) && e.isRealIngame()) {
            this.showGangzones = !this.showGangzones;
        }
    }
}