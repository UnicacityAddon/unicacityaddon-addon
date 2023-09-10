package com.rettichlp.unicacityaddon.base.gangzones;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCGangzone;
import net.labymod.api.util.Pair;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.List;

@UCGangzone(owner = Faction.OBRIEN)
public class Obrien extends AbstractGangzone {

    public Obrien(UnicacityAddon unicacityAddon, UCGangzone ucGangzone) {
        super(unicacityAddon, ucGangzone);
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
