package com.rettichlp.unicacityaddon.base.gangzones;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCGangzone;
import net.labymod.api.util.Color;
import net.labymod.api.util.Pair;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.List;

public abstract class AbstractAttackableGangzone extends AbstractGangzone {

    private final UnicacityAddon unicacityAddon;

    public AbstractAttackableGangzone(UnicacityAddon unicacityAddon, UCGangzone ucGangzone) {
        super(unicacityAddon, ucGangzone);
        this.unicacityAddon = unicacityAddon;
    }

    public abstract List<Pair<FloatVector3, FloatVector3>> gangwarFacades();

    public void renderGangwarFacades() {
        Color color = Color.ORANGE;
        this.gangwarFacades().forEach(posPair -> this.unicacityAddon.renderController().drawFacade(posPair.getFirst(), posPair.getSecond(), color));
    }

    public void renderGangwarOutline() {
        Color color = Color.BLACK;
        this.gangwarFacades().forEach(posPair -> this.unicacityAddon.renderController().drawOutline(posPair.getFirst(), posPair.getSecond(), color));
    }
}
