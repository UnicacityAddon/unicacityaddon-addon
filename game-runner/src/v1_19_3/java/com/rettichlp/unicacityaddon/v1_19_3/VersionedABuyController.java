package com.rettichlp.unicacityaddon.v1_19_3;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.controller.ABuyController;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import net.labymod.api.models.Implements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.NonNullList;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;

import javax.inject.Singleton;
import java.util.Optional;

/**
 * @author RettichLP
 */
@Singleton
@Implements(ABuyController.class)
public class VersionedABuyController extends ABuyController {

    @Override
    public int getHoveredSlotNumber(String displayName) {
        int slotNumber = -1;

        Screen screen = Minecraft.getInstance().screen;
        if (screen instanceof ContainerScreen && ((ContainerScreen) screen).getMenu() instanceof ChestMenu) {
            ChestMenu chestMenu = ((ContainerScreen) screen).getMenu();

            NonNullList<ItemStack> itemStacks = chestMenu.getItems();
            Optional<ItemStack> hoveredItemStackOptional = itemStacks.stream()
                    .filter(itemStack -> itemStack.getDisplayName().getString().contains(displayName))
                    .findFirst();

            if (hoveredItemStackOptional.isPresent()) {
                slotNumber = itemStacks.indexOf(hoveredItemStackOptional.get());
            }
        }

        return slotNumber;
    }

    @Override
    public void buy(UnicacityAddon unicacityAddon, int slotIndex) {
        Screen screen = Minecraft.getInstance().screen;
        if (screen instanceof ContainerScreen && ((ContainerScreen) screen).getMenu() instanceof ChestMenu) {
            ChestMenu chestMenu = ((ContainerScreen) screen).getMenu();

            LocalPlayer localPlayer = Minecraft.getInstance().player;
            assert localPlayer != null;

            ServerboundContainerClickPacket serverboundContainerClickPacket = new ServerboundContainerClickPacket(chestMenu.containerId, 0, slotIndex, 1, ClickType.PICKUP, localPlayer.containerMenu.getCarried(), Int2ObjectMaps.emptyMap());
            localPlayer.connection.send(serverboundContainerClickPacket);
        }
    }
}