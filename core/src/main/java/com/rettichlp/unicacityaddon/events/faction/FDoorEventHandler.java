package com.rettichlp.unicacityaddon.events.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import net.labymod.api.util.math.vector.FloatVector3;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Fuzzlemann
 */
@UCEvent
public class FDoorEventHandler {

    private final List<FDoor> F_DOORS = Arrays.asList(
            new FDoor(new FloatVector3(-167, 69, 204), new FloatVector3(-167, 71, 205)), // Ballas HQ
            new FDoor(new FloatVector3(878, 62, -89), new FloatVector3(880, 64, -89)), // FBI HQ
            new FDoor(new FloatVector3(273, 69, -273), new FloatVector3(273, 72, -275)) // le Milieu Garage
    );
    private static long lastClick;

    private final UnicacityAddon unicacityAddon;

    public FDoorEventHandler(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    //    @Subscribe
//    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock e) {
//        FloatVector3 pos = e.getPos();
//
//        if (p.getPosition().distance(new FloatVector3(-167, 69, 204) > 15
//                && p.getPosition().distance(new FloatVector3(878, 62, -89) > 15
//                && p.getPosition().distance(new FloatVector3(273, 69, -273) > 15
//                || p.getPosition().distance(new FloatVector3(pos.getX(), pos.getY(), pos.getZ()) > 4)
//            return;
//
//        FDoor fDoor = checkForFDoor(e.getPos());
//        if (fDoor == null)
//            return;
//
//        if (System.currentTimeMillis() - lastClick < 1000)
//            return;
//        if (!fDoor.canBeToggled())
//            return;
//
//        lastClick = System.currentTimeMillis();
//        p.sendServerMessage("/fdoor");
//    }

    private FDoor checkForFDoor(FloatVector3 pos) {
        for (FDoor fDoor : F_DOORS) {
            for (FloatVector3 openPosition : fDoor.getOpenPositions()) {
                if (pos.equals(openPosition))
                    return fDoor;
            }

            for (FloatVector3 closePosition : fDoor.getClosePositions()) {
                if (pos.equals(closePosition))
                    return fDoor;
            }
        }
        return null;
    }

    private static class FDoor {

        private final Set<FloatVector3> openPositions = new HashSet<>();
        private final Set<FloatVector3> closePositions = new HashSet<>();

        private FDoor(FloatVector3 lowerCorner, FloatVector3 upperCorner) {
            float x1 = lowerCorner.getX();
            float x2 = upperCorner.getX();

            boolean xConstant = x1 == x2;
            float relevantCoordinate1 = xConstant ? lowerCorner.getZ() : x1;
            float relevantCoordinate2 = xConstant ? upperCorner.getZ() : x2;

            float constantCoordinate = xConstant ? x1 : lowerCorner.getZ(); // irrelevant which X or Z coordinate to pick as those are identical

            // relevantCoordinate1 should be less than relevantCoordinate2 in order to prevent an endless loop
            if (relevantCoordinate1 > relevantCoordinate2) {
                // swap variables
                float temp = relevantCoordinate1;

                relevantCoordinate1 = relevantCoordinate2;
                relevantCoordinate2 = temp;
            }

            for (float i = relevantCoordinate1; i <= relevantCoordinate2; i++) {
                FloatVector3 closePos = new FloatVector3(xConstant ? constantCoordinate : i, lowerCorner.getY() - 1, !xConstant ? constantCoordinate : i);
                closePositions.add(closePos);

                for (float y = lowerCorner.getY(); y <= upperCorner.getY(); y++) {
                    FloatVector3 openPos = new FloatVector3(xConstant ? constantCoordinate : i, y, !xConstant ? constantCoordinate : i);
                    openPositions.add(openPos);
                }
            }
        }

        public boolean canBeToggled() {
//            boolean set = false;
//            boolean open = false;
//            for (FloatVector3 openPosition : openPositions) {
//                IBlockState blockState = p.getWorld().getBlockState(openPosition);
//                boolean air = blockState.getBlock() instanceof BlockAir;
//                if (!set) {
//                    open = air;
//                    set = true;
//                } else {
//                    if (air != open)
//                        return false;
//                }
//            }
            return true;
        }

        public Set<FloatVector3> getOpenPositions() {
            return openPositions;
        }

        public Set<FloatVector3> getClosePositions() {
            return closePositions;
        }
    }
}