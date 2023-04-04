package com.rettichlp.unicacityaddon.v1_12_2;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.controller.ABuyController;
import com.rettichlp.unicacityaddon.listener.ABuyListener;
import net.labymod.api.models.Implements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerChest;

import javax.inject.Singleton;

/**
 * @author RettichLP
 */
@Singleton
@Implements(ABuyController.class)
public class VersionedABuyController extends ABuyController {

    @Override
    public void startBuy(UnicacityAddon unicacityAddon, int slotIndex) {
        GuiScreen guiScreen = Minecraft.getMinecraft().currentScreen;
        if (guiScreen instanceof GuiContainer && ((GuiContainer) guiScreen).inventorySlots instanceof ContainerChest) {
            ContainerChest containerChest = (ContainerChest) ((GuiContainer) guiScreen).inventorySlots;

            new Thread(() -> {
                while (ABuyListener.amountLeft > 0) {
                    unicacityAddon.player().sendServerMessage("/buy");

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    Minecraft.getMinecraft().playerController.windowClick(containerChest.windowId, slotIndex, 0, ClickType.PICKUP, Minecraft.getMinecraft().player);
                    ABuyListener.amountLeft--;
                }
            }).start();
        }
    }
}