package com.rettichlp.unicacityaddon.gangzone;

import net.labymod.api.client.world.object.WorldObject;
import net.labymod.api.util.Pair;
import net.labymod.api.util.math.vector.FloatVector3;

import java.awt.Color;
import java.util.List;

public interface Gangzone extends WorldObject {

    Color color();

    List<Pair<FloatVector3, FloatVector3>> facades();
}
