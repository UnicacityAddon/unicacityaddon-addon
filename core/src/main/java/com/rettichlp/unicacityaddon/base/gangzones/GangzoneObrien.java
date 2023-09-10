package com.rettichlp.unicacityaddon.base.gangzones;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import net.labymod.api.util.Pair;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.List;

public class GangzoneObrien extends AbstractGangzone {

    public GangzoneObrien(UnicacityAddon unicacityAddon) {
        super(unicacityAddon);
    }

    @Override
    public Faction owner() {
        return Faction.OBRIEN;
    }

    @Override
    public List<Pair<FloatVector3, FloatVector3>> gangzoneFacades() {
        return List.of(
                Pair.of(new FloatVector3(687, 69, 490), new FloatVector3(687, 69, 593)),
                Pair.of(new FloatVector3(687, 69, 593), new FloatVector3(761, 69, 593)),
                Pair.of(new FloatVector3(761, 69, 593), new FloatVector3(761, 69, 490)),
                Pair.of(new FloatVector3(761, 69, 490), new FloatVector3(687, 69, 490))
        );
    }
}
