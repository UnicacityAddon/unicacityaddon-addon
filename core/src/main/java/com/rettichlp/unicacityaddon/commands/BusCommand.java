package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.api.request.APIConverter;
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

    public static Bus start;
    public static Bus destination;
    public static boolean active = false;
    public static int lastWindowId = 0;
    public static int limiter = 0;

    public BusCommand() {
        super("bus");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = UnicacityAddon.PLAYER;

        if (arguments.length < 1) {
            return false;
        }

        NaviPoint naviPoint = NaviPoint.getNaviPointByTabName(arguments[0]);
        if (naviPoint == null) {
            p.sendErrorMessage("Navipunkt nicht gefunden.");
            return false;
        }

        start = NavigationUtils.getNearestBus().getValue();
        destination = NavigationUtils.getNearestBus(naviPoint.getBlockPos()).getValue();

        limiter = 0;
        p.sendServerMessage("/bus");
        return active = true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments)
                .addAtIndex(1, APIConverter.NAVIPOINTLIST.stream().map(NaviPoint::getTabName).collect(Collectors.toList()))
                .build();
    }

    public static Bus getNearestBusToDestination(Collection<Bus> busList) {
        return busList.stream()
                .collect(Collectors.toMap(bus -> bus, bus -> bus.getBlockPos().distance(new FloatVector3(destination.getX(), destination.getY(), destination.getZ()))))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }
}