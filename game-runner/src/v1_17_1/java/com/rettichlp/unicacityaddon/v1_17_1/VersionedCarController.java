package com.rettichlp.unicacityaddon.v1_17_1;

import com.rettichlp.unicacityaddon.controller.CarController;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import net.labymod.api.models.Implements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.Items;

import javax.inject.Singleton;

/**
 * @author RettichLP
 */
@Singleton
@Implements(CarController.class)
public class VersionedCarController extends CarController {

    @Override
    public void interact() {
        Screen screen = Minecraft.getInstance().screen;
        if (screen instanceof ContainerScreen containerScreen) {

            if (containerScreen.getTitle().getString().contains("CarControl")) {
                ChestMenu chestMenu = containerScreen.getMenu();
                int numberOfCars = (int) chestMenu.slots.stream()
                    .map(slot -> slot.getItem().getItem())
                    .filter(item -> item.equals(Items.MINECART) || item.equals(Items.EMERALD) || item.equals(Items.REDSTONE))
                    .count();

                if (numberOfCars == 1) {
                    LocalPlayer localPlayer = Minecraft.getInstance().player;
                    assert localPlayer != null;

                    ServerboundContainerClickPacket serverboundContainerClickPacket = new ServerboundContainerClickPacket(chestMenu.containerId, 0, 0, 0, ClickType.PICKUP, localPlayer.containerMenu.getCarried(), Int2ObjectMaps.emptyMap());
                    localPlayer.connection.send(serverboundContainerClickPacket);
                }
            }
        }
    }
}