package com.rettichlp.unicacityaddon.base.gangzones;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCGangzone;
import net.labymod.api.util.Color;
import net.labymod.api.util.Pair;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.List;

public abstract class AbstractGangzone {

    private final UCGangzone ucGangzone;

    private final UnicacityAddon unicacityAddon;

    public AbstractGangzone(UnicacityAddon unicacityAddon, UCGangzone ucGangzone) {
        this.unicacityAddon = unicacityAddon;
        this.ucGangzone = ucGangzone;
    }

    public abstract List<Pair<FloatVector3, FloatVector3>> gangzoneFacades();

    public void renderGangzoneFacades() {
        Color color = this.ucGangzone.owner().getColor().getLabymodColor();
        this.gangzoneFacades().forEach(posPair -> this.unicacityAddon.renderController().drawFacade(posPair.getFirst(), posPair.getSecond(), color));
    }

    public void renderGangzoneOutline(Color color) {
        this.gangzoneFacades().forEach(posPair -> this.unicacityAddon.renderController().drawOutline(posPair.getFirst(), posPair.getSecond(), color));
    }
}
