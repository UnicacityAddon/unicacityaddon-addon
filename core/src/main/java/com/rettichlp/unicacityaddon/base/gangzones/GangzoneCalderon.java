package com.rettichlp.unicacityaddon.base.gangzones;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCGangzone;
import net.labymod.api.util.Pair;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.List;

@UCGangzone(owner = Faction.CALDERON)
public class GangzoneCalderon extends AbstractGangzone {

    public GangzoneCalderon(UnicacityAddon unicacityAddon, UCGangzone ucGangzone) {
        super(unicacityAddon, ucGangzone);
    }

    @Override
    public List<Pair<FloatVector3, FloatVector3>> gangzoneFacades() {
        return List.of(
                Pair.of(new FloatVector3(-343, 69, -149), new FloatVector3(-343, 69, -84)),
                Pair.of(new FloatVector3(-343, 69, -84), new FloatVector3(-251, 69, -84)),
                Pair.of(new FloatVector3(-251, 69, -84), new FloatVector3(-251, 69, -149)),
                Pair.of(new FloatVector3(-251, 69, -149), new FloatVector3(-343, 69, -149))
        );
    }
}
