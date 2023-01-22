package com.rettichlp.unicacityaddon.events.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.commands.BusCommand;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerHopper;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/faction/CheckActiveMembersCommand.java">UCUtils by paulzhng</a>
 */
@UCEvent
public class BusEventHandler {

    @SubscribeEvent
    public static void onGuiOpen(GuiContainerEvent.DrawForeground e) {
        System.out.println("GUI: fired");


        if (!(e.getGuiContainer().inventorySlots instanceof ContainerHopper))
            return;
        ContainerHopper containerHopper = (ContainerHopper) e.getGuiContainer().inventorySlots;

        AtomicInteger slotIndex = new AtomicInteger(-1);
        containerHopper.inventorySlots.forEach(slot -> {
            System.out.println("SLOT: " + slot.getStack().getDisplayName());
            if (slot.getStack().getDisplayName().equals(BusCommand.busRoute.get(1).getName())) {
                BusCommand.busRoute.remove(1);
                slotIndex.set(slot.slotNumber);
            }
        });

        System.out.println("SLOTINDEX: " + slotIndex.get());

        if (slotIndex.get() > -1) {
            UnicacityAddon.MINECRAFT.playerController.windowClick(containerHopper.windowId, slotIndex.get(), 1, ClickType.PICKUP, UnicacityAddon.MINECRAFT.player);
        }
    }
}