package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.location.Bus;
import com.rettichlp.unicacityaddon.base.models.NaviPoint;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.utils.NavigationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand
public class BusCommand implements IClientCommand {

    public static List<Bus> busRoute;

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

        Bus start = NavigationUtils.getNearestBus().getValue();
        Bus destination = NavigationUtils.getNearestBus(naviPoint.getBlockPos()).getValue();

        busRoute = getBusRoute(start, destination);
        p.sendChatMessage("/bus");

        System.out.println("INGAMEGUI: " + Minecraft.getMinecraft().ingameGUI);
    }

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public int compareTo(@Nonnull ICommand o) {
        return 0;
    }

    private static List<Bus> getBusRoute(Bus start, Bus destination) {
        List<Bus> busRoute = new ArrayList<>();
        busRoute.add(start);

        System.out.println("START: " + start);
        System.out.println("ZIEL: " + destination);

        while (!busRoute.get(busRoute.size() - 1).equals(destination)) {
            busRoute.add(getNearestBusToDestination(destination, busRoute.get(busRoute.size() - 1).getNeighbors()).get(0));
            System.out.println(busRoute.get(busRoute.size() - 1) + " - " + getNearestBusToDestination(destination, busRoute.get(busRoute.size() - 1).getNeighbors()));
        }

        return busRoute;
    }

    private static List<Bus> getNearestBusToDestination(Bus destination, List<Bus> busList) {
        return busList.stream()
                .collect(Collectors.toMap(bus -> bus, bus -> bus.getBlockPos().getDistance(destination.getX(), destination.getY(), destination.getZ())))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
