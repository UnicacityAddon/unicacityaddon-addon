package com.rettichlp.unicacityaddon.v1_16_5;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.enums.location.Bus;
import com.rettichlp.unicacityaddon.commands.BusCommand;
import com.rettichlp.unicacityaddon.controller.TransportController;
import net.labymod.api.models.Implements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.gui.screens.inventory.HopperScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.HopperMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Items;

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
    public void processBusRouting(AddonPlayer p) {
        Screen screen = Minecraft.getInstance().screen;
        if (screen instanceof HopperScreen && BusCommand.active) {
            HopperScreen hopperScreen = (HopperScreen) screen;

            HopperMenu hopperMenu = hopperScreen.getMenu();
            if (hopperMenu.containerId != BusCommand.lastWindowId) {
                BusCommand.lastWindowId = hopperMenu.containerId;

                Map<Bus, Slot> busSlotMap = hopperMenu.slots.stream()
                        .filter(slot -> slot.getItem().getItem().equals(Items.OAK_SIGN))
                        .filter(slot -> Bus.getBus(slot.getItem().getDisplayName().getString().replace("[", "").replace("]", "")) != null)
                        .collect(Collectors.toMap(slot -> Bus.getBus(slot.getItem().getDisplayName().getString().replace("[", "").replace("]", "")), slot -> slot));

                Bus nearestBusToDestination = BusCommand.getNearestBusToDestination(busSlotMap.keySet());
                if (nearestBusToDestination == null) {
                    p.sendErrorMessage("Es konnte keine Route gefunden werden.");
                    BusCommand.active = false;
                    return;
                }

                Slot slot = busSlotMap.get(nearestBusToDestination);
                LocalPlayer localPlayer = Minecraft.getInstance().player;
                assert localPlayer != null;

                if (nearestBusToDestination.equals(BusCommand.destination)) {
                    ServerboundContainerClickPacket serverboundContainerClickPacket = new ServerboundContainerClickPacket(hopperMenu.containerId, slot.index, 0, ClickType.PICKUP, localPlayer.inventory.getSelected(), (short) 0);
                    localPlayer.connection.send(serverboundContainerClickPacket);
                    BusCommand.active = false;
                } else if (BusCommand.limiter < 15) {
                    ServerboundContainerClickPacket serverboundContainerClickPacket = new ServerboundContainerClickPacket(hopperMenu.containerId, slot.index, 1, ClickType.PICKUP, localPlayer.inventory.getSelected(), (short) 0);
                    localPlayer.connection.send(serverboundContainerClickPacket);
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
        Screen screen = Minecraft.getInstance().screen;
        if (screen instanceof ContainerScreen) {
            ContainerScreen containerScreen = (ContainerScreen) screen;

            if (containerScreen.getTitle().getString().contains("CarControl")) {
                ChestMenu chestMenu = containerScreen.getMenu();
                int numberOfCars = (int) chestMenu.slots.stream()
                    .map(slot -> slot.getItem().getItem())
                    .filter(item -> item.equals(Items.MINECART) || item.equals(Items.EMERALD) || item.equals(Items.REDSTONE))
                    .count();

                if (numberOfCars == 1) {
                    LocalPlayer localPlayer = Minecraft.getInstance().player;
                    assert localPlayer != null;

                    ServerboundContainerClickPacket serverboundContainerClickPacket = new ServerboundContainerClickPacket(chestMenu.containerId, 0, 0, ClickType.PICKUP, localPlayer.inventory.getSelected(), (short) 0);
                    localPlayer.connection.send(serverboundContainerClickPacket);
                }
            }
        }
    }
}