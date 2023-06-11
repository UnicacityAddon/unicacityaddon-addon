package com.rettichlp.unicacityaddon.v1_12_2;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.enums.location.Bus;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.commands.BusCommand;
import com.rettichlp.unicacityaddon.controller.TransportController;
import net.labymod.api.models.Implements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.inject.Singleton;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@Singleton
@Implements(TransportController.class)
public class VersionedTransportController extends TransportController {

    @Override
    public void processBusRouting(UnicacityAddon unicacityAddon) {
        AddonPlayer p = unicacityAddon.player();
        GuiScreen guiScreen = Minecraft.getMinecraft().currentScreen;
        if (guiScreen instanceof GuiHopper guiHopper && BusCommand.active) {
            Container container = guiHopper.inventorySlots;
            if (container.windowId != BusCommand.lastWindowId && container instanceof ContainerHopper) {
                BusCommand.lastWindowId = container.windowId;

                Map<Bus, Slot> busSlotMap = container.inventorySlots.stream()
                        .filter(slot -> slot.getStack().getDisplayName().startsWith(ColorCode.GOLD.getCode()))
                        .filter(slot -> Bus.getBus(unicacityAddon.services().util().textUtils().stripColor(slot.getStack().getDisplayName())) != null)
                        .collect(Collectors.toMap(slot -> Bus.getBus(unicacityAddon.services().util().textUtils().stripColor(slot.getStack().getDisplayName())), slot -> slot));

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

    @Override
    public void carInteract() {
        GuiScreen guiScreen = Minecraft.getMinecraft().currentScreen;
        if (guiScreen instanceof GuiContainer && ((GuiContainer) guiScreen).inventorySlots instanceof ContainerChest containerChest) {
            if (containerChest.getLowerChestInventory().getDisplayName().getUnformattedText().equals("§6CarControl")) {
                int numberOfCars = (int) containerChest.getInventory().stream()
                        .filter(itemStack -> !itemStack.isEmpty() && itemStack.getDisplayName().startsWith(ColorCode.GOLD.getCode()))
                        .map(ItemStack::getItem)
                        .filter(item -> item.equals(Items.MINECART) || item.equals(Items.EMERALD) || item.equals(Items.REDSTONE))
                        .count();

                if (numberOfCars == 1) {
                    Minecraft.getMinecraft().playerController.windowClick(containerChest.windowId, 0, 0, ClickType.PICKUP, Minecraft.getMinecraft().player);
                }
            }
        }
    }
}