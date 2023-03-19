package com.rettichlp.unicacityaddon.v1_12_2;

import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.controller.CarController;
import net.labymod.api.models.Implements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;

import javax.inject.Singleton;

/**
 * @author RettichLP
 */
@Singleton
@Implements(CarController.class)
public class VersionedCarController extends CarController {

    @Override
    public void interact() {
        GuiScreen guiScreen = Minecraft.getMinecraft().currentScreen;
        if (guiScreen instanceof GuiContainer && ((GuiContainer) guiScreen).inventorySlots instanceof ContainerChest) {
            ContainerChest containerChest = (ContainerChest) ((GuiContainer) guiScreen).inventorySlots;

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