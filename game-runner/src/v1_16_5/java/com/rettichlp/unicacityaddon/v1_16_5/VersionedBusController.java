package com.rettichlp.unicacityaddon.v1_16_5;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.enums.location.Bus;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.utils.ForgeUtils;
import com.rettichlp.unicacityaddon.commands.BusCommand;
import com.rettichlp.unicacityaddon.controller.BusController;
import net.labymod.api.models.Implements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.HopperScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.HopperMenu;
import net.minecraft.world.inventory.Slot;

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
        Screen screen = Minecraft.getInstance().screen;
        System.out.println("screen");
        if (screen instanceof HopperScreen && BusCommand.active) {
            HopperScreen hopperScreen = (HopperScreen) screen;
            HopperMenu hopperMenu = hopperScreen.getMenu();
            System.out.println("hoper + active");
            if (hopperMenu.containerId != BusCommand.lastWindowId) {
                AddonPlayer p = UnicacityAddon.PLAYER;
                BusCommand.lastWindowId = hopperMenu.containerId;
                System.out.println("id");
                Map<Bus, Slot> busSlotMap = hopperMenu.slots.stream()
                        .filter(slot -> slot.getItem().getDisplayName().getString().startsWith(ColorCode.GOLD.getCode()))
                        .filter(slot -> Bus.getBus(ForgeUtils.stripColor(slot.getItem().getDisplayName().getString())) != null)
                        .collect(Collectors.toMap(slot -> Bus.getBus(ForgeUtils.stripColor(slot.getItem().getDisplayName().getString())), slot -> slot));

                Bus nearestBusToDestination = BusCommand.getNearestBusToDestination(busSlotMap.keySet());
                if (nearestBusToDestination == null) {
                    p.sendErrorMessage("Es konnte keine Route gefunden werden.");
                    BusCommand.active = false;
                    return;
                }

                System.out.println("NBUS: " + nearestBusToDestination.getName());
                Slot slot = busSlotMap.get(nearestBusToDestination);
                LocalPlayer localPlayer = Minecraft.getInstance().player;

                System.out.println("slot " + slot.toString());
                if (nearestBusToDestination.equals(BusCommand.destination)) {
                    ServerboundContainerClickPacket serverboundContainerClickPacket = new ServerboundContainerClickPacket(hopperMenu.containerId, slot.index, 1, ClickType.PICKUP, localPlayer.inventory.getSelected(), (short) 0);
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
}
