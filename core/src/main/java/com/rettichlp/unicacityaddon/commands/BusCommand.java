package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.NaviPoint;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.location.Bus;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.rettichlp.unicacityaddon.base.io.api.API.find;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "bus")
public class BusCommand extends UnicacityCommand {

    public static Bus destination;
    public static boolean active = false;
    public static int lastWindowId = 0;
    public static int limiter = 0;

    private final UnicacityAddon unicacityAddon;

    public BusCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length < 1) {
            return false;
        }

        NaviPoint naviPoint = find(this.unicacityAddon.api().getNaviPointList(), n -> n.getName().equalsIgnoreCase(arguments[0]));
        if (naviPoint == null) {
            p.sendErrorMessage("Navipunkt nicht gefunden.");
            return false;
        }

        destination = this.unicacityAddon.navigationService().getNearestBus(naviPoint.getLocation()).getValue();

        limiter = 0;
        p.sendServerMessage("/bus");
        return active = true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, this.unicacityAddon.api().getNaviPointList().stream().map(NaviPoint::getName).collect(Collectors.toList()))
                .build();
    }

    public static Bus getNearestBusToDestination(Collection<Bus> busList) {
        return busList.stream()
                .collect(Collectors.toMap(bus -> bus, bus -> bus.getLocation().distance(new FloatVector3(destination.getX(), destination.getY(), destination.getZ()))))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }
}