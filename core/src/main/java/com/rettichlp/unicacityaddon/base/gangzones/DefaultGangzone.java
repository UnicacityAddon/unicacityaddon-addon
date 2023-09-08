package com.rettichlp.unicacityaddon.base.gangzones;

import com.rettichlp.unicacityaddon.gangzone.Gangzone;
import net.labymod.api.Laby;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.client.world.object.AbstractWorldObject;
import net.labymod.api.util.Pair;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.math.vector.FloatVector3;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;

public class DefaultGangzone extends AbstractWorldObject implements Gangzone {

    private final Color color;
    private final List<Pair<FloatVector3, FloatVector3>> facades;

    public DefaultGangzone(Color color, List<Pair<FloatVector3, FloatVector3>> facades) {
//        super();
        this.color = color;
        this.facades = facades;
    }

    @Override
    public Color color() {
        return this.color;
    }

    @Override
    public List<Pair<FloatVector3, FloatVector3>> facades() {
        return this.facades;
    }

    @Override
    public boolean isSeeThrough() {
        return super.isSeeThrough();
    }

    @Override
    public void renderInWorld(@NotNull MinecraftCamera cam, @NotNull Stack stack, float x, float y, float z, float delta, boolean darker) {
        stack.push();

        RenderPipeline render = Laby.references().renderPipeline();

        render.rectangleRenderer()
                .renderRectangle(stack, Rectangle.absolute(1, 1, 1, 5), Color.RED);

        stack.pop();
    }
}
