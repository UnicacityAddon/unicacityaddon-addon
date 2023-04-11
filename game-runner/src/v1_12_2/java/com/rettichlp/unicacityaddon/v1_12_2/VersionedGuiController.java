package com.rettichlp.unicacityaddon.v1_12_2;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.controller.GuiController;
import net.labymod.api.models.Implements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.inject.Singleton;
import java.util.Optional;

/**
 * @author RettichLP
 */
@Singleton
@Implements(GuiController.class)
public class VersionedGuiController extends GuiController {

    @Override
    public int getSlotNumberByDisplayName(String displayName) {
        int slotNumber = -1;

        GuiScreen guiScreen = Minecraft.getMinecraft().currentScreen;
        if (guiScreen instanceof GuiContainer && ((GuiContainer) guiScreen).inventorySlots instanceof ContainerChest) {
            ContainerChest containerChest = (ContainerChest) ((GuiContainer) guiScreen).inventorySlots;

            NonNullList<ItemStack> itemStacks = containerChest.getInventory();
            Optional<ItemStack> hoveredItemStackOptional = itemStacks.stream()
                    .filter(ItemStack::hasDisplayName)
                    .filter(itemStack -> itemStack.getDisplayName().contains(displayName))
                    .findFirst();

            if (hoveredItemStackOptional.isPresent()) {
                slotNumber = itemStacks.indexOf(hoveredItemStackOptional.get());
            }
        }

        return slotNumber;
    }

    @Override
    public void inventoryClick(UnicacityAddon unicacityAddon, int slotNumber) {
        GuiScreen guiScreen = Minecraft.getMinecraft().currentScreen;
        if (guiScreen instanceof GuiContainer && ((GuiContainer) guiScreen).inventorySlots instanceof ContainerChest) {
            ContainerChest containerChest = (ContainerChest) ((GuiContainer) guiScreen).inventorySlots;
            Minecraft.getMinecraft().playerController.windowClick(containerChest.windowId, slotNumber, 0, ClickType.PICKUP, Minecraft.getMinecraft().player);
        }
    }
}