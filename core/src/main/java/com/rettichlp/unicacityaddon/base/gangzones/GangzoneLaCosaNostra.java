package com.rettichlp.unicacityaddon.base.gangzones;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import net.labymod.api.util.Pair;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.List;

public class GangzoneLaCosaNostra extends AbstractGangzone {

    public GangzoneLaCosaNostra(UnicacityAddon unicacityAddon) {
        super(unicacityAddon);
    }

    @Override
    public Faction owner() {
        return Faction.LACOSANOSTRA;
    }

    @Override
    public List<Pair<FloatVector3, FloatVector3>> gangzoneFacades() {
        return List.of(
                Pair.of(new FloatVector3(-56, 69, -478), new FloatVector3(-56, 69, -427)),
                Pair.of(new FloatVector3(-56, 69, -427), new FloatVector3(47, 69, -427)),
                Pair.of(new FloatVector3(47, 69, -427), new FloatVector3(47, 69, -509)),
                Pair.of(new FloatVector3(47, 69, -509), new FloatVector3(-35, 69, -509)),
                Pair.of(new FloatVector3(-35, 69, -509), new FloatVector3(-35, 69, -478)),
                Pair.of(new FloatVector3(-35, 69, -478), new FloatVector3(-56, 69, -478))
        );
    }
}
