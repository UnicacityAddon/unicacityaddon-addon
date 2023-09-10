package com.rettichlp.unicacityaddon.base.gangzones;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCGangzone;
import net.labymod.api.util.Pair;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.List;

@UCGangzone(owner = Faction.KERZAKOV)
public class GangzoneKerzakov extends AbstractGangzone {

    public GangzoneKerzakov(UnicacityAddon unicacityAddon, UCGangzone ucGangzone) {
        super(unicacityAddon, ucGangzone);
    }

    @Override
    public List<Pair<FloatVector3, FloatVector3>> gangzoneFacades() {
        return List.of(
                Pair.of(new FloatVector3(863, 69, 160), new FloatVector3(863, 69, 239)),
                Pair.of(new FloatVector3(863, 69, 239), new FloatVector3(956, 69, 239)),
                Pair.of(new FloatVector3(956, 69, 239), new FloatVector3(956, 69, 160)),
                Pair.of(new FloatVector3(956, 69, 160), new FloatVector3(863, 69, 160))
        );
    }
}
