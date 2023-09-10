package com.rettichlp.unicacityaddon.base.gangzones;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import net.labymod.api.util.Color;
import net.labymod.api.util.Pair;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.List;
import java.util.Optional;

public abstract class AbstractGangzone {

    private final UnicacityAddon unicacityAddon;

    public AbstractGangzone(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    public abstract Faction owner();

    public abstract List<Pair<FloatVector3, FloatVector3>> gangzoneFacades();

    public void renderGangzoneArea() {
        Color color = Optional.ofNullable(this.owner()).map(faction -> faction.getColor().getLabymodColor()).orElse(ColorCode.GRAY.getLabymodColor());
        this.gangzoneFacades().forEach(posPair -> this.unicacityAddon.renderController().drawFacade(posPair.getFirst(), posPair.getSecond(), color));
    }

//    @Override
//    public void renderInWorld(@NotNull MinecraftCamera cam, @NotNull Stack stack, float x, float y, float z, float delta, boolean darker) {
//        stack.push();
//
//        RenderPipeline render = Laby.references().renderPipeline();
//
//        render.rectangleRenderer()
//                .renderRectangle(stack, Rectangle.absolute(1, 1, 1, 5), Color.RED);
//
//        stack.pop();
//    }
}
