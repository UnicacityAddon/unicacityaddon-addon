package com.rettichlp.unicacityaddon.base.gangzones;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import net.labymod.api.util.Pair;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.List;

public class GangzoneLeMilieu extends AbstractGangzone {

    public GangzoneLeMilieu(UnicacityAddon unicacityAddon) {
        super(unicacityAddon);
    }

    @Override
    public Faction owner() {
        return Faction.LEMILIEU;
    }

    @Override
    public List<Pair<FloatVector3, FloatVector3>> gangzoneFacades() {
        return List.of(
                Pair.of(new FloatVector3(262, 69, -313), new FloatVector3(262, 69, -219)),
                Pair.of(new FloatVector3(262, 69, -219), new FloatVector3(337, 69, -219)),
                Pair.of(new FloatVector3(337, 69, -219), new FloatVector3(337, 69, -313)),
                Pair.of(new FloatVector3(337, 69, -313), new FloatVector3(262, 69, -313))
        );
    }
}
