package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.location.Bus;
import com.rettichlp.unicacityaddon.base.models.NaviPoint;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.utils.NavigationUtils;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand
public class BusCommand extends Command {

    public static Bus START;
    public static Bus DESTINATION;

    private static boolean active = false;
    private static int limiter = 0;
    private static int lastWindowId = 0;

    public BusCommand() {
        super("bus");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (arguments.length < 1) {
            p.sendChatMessage("/bus");
            return true;
        }

        NaviPoint naviPoint = NaviPoint.getNaviPointEntryByTabName(arguments[0]);
        if (naviPoint == null) {
            p.sendErrorMessage("Navipunkt nicht gefunden.");
            return true;
        }

        START = NavigationUtils.getNearestBus().getValue();
        DESTINATION = NavigationUtils.getNearestBus(naviPoint.getBlockPos()).getValue();

        limiter = 0;
        p.sendChatMessage("/bus");
        return active = true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments)
                .addAtIndex(1, Syncer.getNaviPointEntryList().stream().map(NaviPoint::getTabName).collect(Collectors.toList()))
                .build();
    }

    public static void process() {
//        GuiScreen guiScreen = UnicacityAddon.MINECRAFT.currentScreen;
//        if (guiScreen instanceof GuiHopper && active) {
//            GuiHopper guiHopper = (GuiHopper) guiScreen;
//
//            Container container = guiHopper.inventorySlots;
//            if (container.windowId != lastWindowId && container instanceof ContainerHopper) {
//                UPlayer p = AbstractionLayer.getPlayer();
//                lastWindowId = container.windowId;
//
//                Map<Bus, Slot> busSlotMap = container.inventorySlots.stream()
//                        .filter(slot -> slot.getStack().getDisplayName().startsWith(ColorCode.GOLD.getCode()))
//                        .filter(slot -> Bus.getBus(ForgeUtils.stripColor(slot.getStack().getDisplayName())) != null)
//                        .collect(Collectors.toMap(slot -> Bus.getBus(ForgeUtils.stripColor(slot.getStack().getDisplayName())), slot -> slot));
//
//                Bus nearestBusToDestination = getNearestBusToDestination(busSlotMap.keySet());
//                if (nearestBusToDestination == null) {
//                    p.sendErrorMessage("Es konnte keine Route gefunden werden.");
//                    active = false;
//                    return;
//                }
//
//                Slot slot = busSlotMap.get(nearestBusToDestination);
//
//                if (nearestBusToDestination.equals(DESTINATION)) {
//                    UnicacityAddon.MINECRAFT.playerController.windowClick(container.windowId, slot.slotNumber, 0, ClickType.PICKUP, UnicacityAddon.MINECRAFT.player);
//                    active = false;
//                } else if (limiter < 15) {
//                    UnicacityAddon.MINECRAFT.playerController.windowClick(container.windowId, slot.slotNumber, 1, ClickType.PICKUP, UnicacityAddon.MINECRAFT.player);
//                    limiter++;
//                } else {
//                    p.sendErrorMessage("Es konnte keine Route gefunden werden.");
//                    active = false;
//                }
//            }
//        }
    }

    private static Bus getNearestBusToDestination(Collection<Bus> busList) {
        return busList.stream()
                .collect(Collectors.toMap(bus -> bus, bus -> bus.getBlockPos().distance(new FloatVector3(DESTINATION.getX(), DESTINATION.getY(), DESTINATION.getZ()))))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }
}