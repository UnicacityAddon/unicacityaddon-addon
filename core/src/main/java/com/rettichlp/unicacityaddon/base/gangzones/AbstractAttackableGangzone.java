package com.rettichlp.unicacityaddon.base.gangzones;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCGangzone;
import net.labymod.api.util.Color;
import net.labymod.api.util.Pair;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.List;

public abstract class AbstractAttackableGangzone extends AbstractGangzone {

    private final UCGangzone ucGangzone;

    private final UnicacityAddon unicacityAddon;

    public AbstractAttackableGangzone(UnicacityAddon unicacityAddon, UCGangzone ucGangzone) {
        super(unicacityAddon, ucGangzone);
        this.unicacityAddon = unicacityAddon;
        this.ucGangzone = ucGangzone;
    }

    public abstract List<Pair<FloatVector3, FloatVector3>> gangwarFacades();

    public void renderGangwarArea() {
        Color color = ucGangzone.owner().getColor().getLabymodColor();
        this.gangwarFacades().forEach(posPair -> this.unicacityAddon.renderController().drawFacade(posPair.getFirst(), posPair.getSecond(), color));
    }
}
