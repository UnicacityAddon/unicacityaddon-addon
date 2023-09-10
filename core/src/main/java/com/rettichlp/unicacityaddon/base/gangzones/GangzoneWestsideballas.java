package com.rettichlp.unicacityaddon.base.gangzones;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import net.labymod.api.util.Pair;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.List;

public class GangzoneWestsideballas extends AbstractGangzone {

    public GangzoneWestsideballas(UnicacityAddon unicacityAddon) {
        super(unicacityAddon);
    }

    @Override
    public Faction owner() {
        return Faction.WESTSIDEBALLAS;
    }

    @Override
    public List<Pair<FloatVector3, FloatVector3>> gangzoneFacades() {
        return List.of(
                Pair.of(new FloatVector3(-199, 69, 142), new FloatVector3(-199, 69, 272)),
                Pair.of(new FloatVector3(-199, 69, 272), new FloatVector3(-121, 69, 272)),
                Pair.of(new FloatVector3(-121, 69, 272), new FloatVector3(-121, 69, 142)),
                Pair.of(new FloatVector3(-121, 69, 142), new FloatVector3(-199, 69, 142))
        );
    }
}
