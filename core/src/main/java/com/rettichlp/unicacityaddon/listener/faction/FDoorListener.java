package com.rettichlp.unicacityaddon.listener.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.entity.player.ClientPlayerInteractEvent;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.Arrays;
import java.util.List;

/**
 * @author Fuzzlemann
 */
@UCEvent
public class FDoorListener {

    private static final List<FDoor> F_DOORS = Arrays.asList(
            new FDoor(new FloatVector3(-167, 69, 204), new FloatVector3(-167, 71, 205)), // Ballas HQ
            new FDoor(new FloatVector3(878, 62, -89), new FloatVector3(880, 64, -89)), // FBI HQ
            new FDoor(new FloatVector3(273, 69, -273), new FloatVector3(273, 72, -275)) // le Milieu Garage
    );
    private static long lastClick;

    private final UnicacityAddon unicacityAddon;

    public FDoorListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onClientPlayerInteract(ClientPlayerInteractEvent e) {
        FloatVector3 location = this.unicacityAddon.worldInteractionController().getClickedBlockLocation();

        if (location != null) {
            FDoor fDoor = F_DOORS.stream().filter(fd -> fd.getDistance(location) <= 3).findFirst().orElse(null);
            if (fDoor != null && System.currentTimeMillis() - lastClick > 1000) {
                lastClick = System.currentTimeMillis();
                this.unicacityAddon.player().sendServerMessage("/fdoor");
            }
        }
    }

    private static class FDoor {

        private final AxisAlignedBoundingBox axisAlignedBoundingBox;

        public FDoor(FloatVector3 lowerCorner, FloatVector3 upperCorner) {
            lowerCorner = lowerCorner.add(0, -1, 0);
            this.axisAlignedBoundingBox = new AxisAlignedBoundingBox(lowerCorner.add(0, -1, 0), upperCorner);
        }

        public float getDistance(FloatVector3 location) {
            float midX = this.axisAlignedBoundingBox.getMinX() + this.axisAlignedBoundingBox.getXSize() / 2;
            float midZ = this.axisAlignedBoundingBox.getMinZ() + this.axisAlignedBoundingBox.getZSize() / 2;

            FloatVector3 fDoorLocation = new FloatVector3(midX, this.axisAlignedBoundingBox.getMinY(), midZ);
            return fDoorLocation.distance(location);
        }
    }
}