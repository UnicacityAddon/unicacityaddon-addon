package com.rettichlp.unicacityaddon.v1_12_2;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.enums.location.Bus;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.utils.ForgeUtils;
import com.rettichlp.unicacityaddon.commands.BusCommand;
import com.rettichlp.unicacityaddon.controller.BusController;
import net.labymod.api.models.Implements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.Slot;

import javax.inject.Singleton;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@Singleton
@Implements(BusController.class)
public class VersionedBusController extends BusController {

    @Override
    public void processBusRouting() {
        GuiScreen guiScreen = Minecraft.getMinecraft().currentScreen;
        if (guiScreen instanceof GuiHopper && BusCommand.active) {
            GuiHopper guiHopper = (GuiHopper) guiScreen;

            Container container = guiHopper.inventorySlots;
            if (container.windowId != BusCommand.lastWindowId && container instanceof ContainerHopper) {
                AddonPlayer p = UnicacityAddon.PLAYER;
                BusCommand.lastWindowId = container.windowId;

                Map<Bus, Slot> busSlotMap = container.inventorySlots.stream()
                        .filter(slot -> slot.getStack().getDisplayName().startsWith(ColorCode.GOLD.getCode()))
                        .filter(slot -> Bus.getBus(ForgeUtils.stripColor(slot.getStack().getDisplayName())) != null)
                        .collect(Collectors.toMap(slot -> Bus.getBus(ForgeUtils.stripColor(slot.getStack().getDisplayName())), slot -> slot));

                Bus nearestBusToDestination = BusCommand.getNearestBusToDestination(busSlotMap.keySet());
                if (nearestBusToDestination == null) {
                    p.sendErrorMessage("Es konnte keine Route gefunden werden.");
                    BusCommand.active = false;
                    return;
                }

                Slot slot = busSlotMap.get(nearestBusToDestination);

                if (nearestBusToDestination.equals(BusCommand.destination)) {
                    Minecraft.getMinecraft().playerController.windowClick(container.windowId, slot.slotNumber, 0, ClickType.PICKUP, Minecraft.getMinecraft().player);
                    BusCommand.active = false;
                } else if (BusCommand.limiter < 15) {
                    Minecraft.getMinecraft().playerController.windowClick(container.windowId, slot.slotNumber, 1, ClickType.PICKUP, Minecraft.getMinecraft().player);
                    BusCommand.limiter++;
                } else {
                    p.sendErrorMessage("Es konnte keine Route gefunden werden.");
                    BusCommand.active = false;
                }
            }
        }
    }
}
