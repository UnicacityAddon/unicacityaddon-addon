package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.location.Bus;
import com.rettichlp.unicacityaddon.base.models.NaviPoint;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.utils.ForgeUtils;
import com.rettichlp.unicacityaddon.base.utils.NavigationUtils;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.Slot;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand
public class BusCommand implements IClientCommand {

    public static Bus START;
    public static Bus DESTINATION;

    private static boolean active = false;
    private static int limiter = 0;
    private static int lastWindowId = 0;

    @Override
    @Nonnull
    public String getName() {
        return "bus";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/bus [Navipunkt]";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return TabCompletionBuilder.getBuilder(args)
                .addAtIndex(1, Syncer.getNaviPointEntryList().stream().map(NaviPoint::getTabName).collect(Collectors.toList()))
                .build();
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (args.length < 1) {
            p.sendChatMessage("/bus");
            return;
        }

        NaviPoint naviPoint = NaviPoint.getNaviPointEntryByTabName(args[0]);
        if (naviPoint == null) {
            p.sendErrorMessage("Navipunkt nicht gefunden.");
            return;
        }

        START = NavigationUtils.getNearestBus().getValue();
        DESTINATION = NavigationUtils.getNearestBus(naviPoint.getBlockPos()).getValue();

        limiter = 0;
        p.sendChatMessage("/bus");
        active = true;
    }

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public int compareTo(@Nonnull ICommand o) {
        return 0;
    }

    public static void process() {
        GuiScreen guiScreen = UnicacityAddon.MINECRAFT.currentScreen;
        if (guiScreen instanceof GuiHopper && active) {
            GuiHopper guiHopper = (GuiHopper) guiScreen;

            Container container = guiHopper.inventorySlots;
            if (container.windowId != lastWindowId && container instanceof ContainerHopper) {
                UPlayer p = AbstractionLayer.getPlayer();
                lastWindowId = container.windowId;

                Map<Bus, Slot> busSlotMap = container.inventorySlots.stream()
                        .filter(slot -> slot.getStack().getDisplayName().startsWith(ColorCode.GOLD.getCode()))
                        .filter(slot -> Bus.getBus(ForgeUtils.stripColor(slot.getStack().getDisplayName())) != null)
                        .collect(Collectors.toMap(slot -> Bus.getBus(ForgeUtils.stripColor(slot.getStack().getDisplayName())), slot -> slot));

                Bus nearestBusToDestination = getNearestBusToDestination(busSlotMap.keySet());
                if (nearestBusToDestination == null) {
                    p.sendErrorMessage("Es konnte keine Route gefunden werden.");
                    active = false;
                    return;
                }

                Slot slot = busSlotMap.get(nearestBusToDestination);

                if (nearestBusToDestination.equals(DESTINATION)) {
                    UnicacityAddon.MINECRAFT.playerController.windowClick(container.windowId, slot.slotNumber, 0, ClickType.PICKUP, UnicacityAddon.MINECRAFT.player);
                    active = false;
                } else if (limiter < 15) {
                    UnicacityAddon.MINECRAFT.playerController.windowClick(container.windowId, slot.slotNumber, 1, ClickType.PICKUP, UnicacityAddon.MINECRAFT.player);
                    limiter++;
                } else {
                    p.sendErrorMessage("Es konnte keine Route gefunden werden.");
                    active = false;
                }
            }
        }
    }

    private static Bus getNearestBusToDestination(Collection<Bus> busList) {
        return busList.stream()
                .collect(Collectors.toMap(bus -> bus, bus -> bus.getBlockPos().getDistance(DESTINATION.getX(), DESTINATION.getY(), DESTINATION.getZ())))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }
}
