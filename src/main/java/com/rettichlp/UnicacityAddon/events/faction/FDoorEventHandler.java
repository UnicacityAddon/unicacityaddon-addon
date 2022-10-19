package com.rettichlp.UnicacityAddon.events.faction;

import com.google.common.collect.ImmutableSet;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashSet;
import java.util.Set;

import static com.rettichlp.UnicacityAddon.base.utils.DebugUtils.Debug;

/**
 * @author Fuzzlemann
 */
@UCEvent
public class FDoorEventHandler {

    private final Set<FDoor> F_DOORS = ImmutableSet.of(
            new FDoor(new BlockPos(-167, 69, 204), new BlockPos(-167, 71, 205)), // Ballas HQ
            new FDoor(new BlockPos(878, 62, -89), new BlockPos(880, 64, -89)), // FBI HQ
            new FDoor(new BlockPos(273, 69, -273), new BlockPos(273, 72, -275)) // le Milieu Garage
    );
    private static long lastClick;

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock e) {
        UPlayer p = AbstractionLayer.getPlayer();
        BlockPos pos = e.getPos();

        if (p.getPosition().getDistance(-167, 69, 204) > 15
                && p.getPosition().getDistance(878, 62, -89) > 15
                && p.getPosition().getDistance(273, 69, -273) > 15
                || p.getPosition().getDistance(pos.getX(), pos.getY(), pos.getZ()) > 4) return;

        FDoor fDoor = checkForFDoor(e.getPos());
        if (fDoor == null) return;

        if (System.currentTimeMillis() - lastClick < 1000) return;
        if (!fDoor.canBeToggled()) return;

        lastClick = System.currentTimeMillis();
        p.sendChatMessage("/fdoor");
    }

    private FDoor checkForFDoor(BlockPos pos) {
        for (FDoor fDoor : F_DOORS) {
            for (BlockPos openPosition : fDoor.getOpenPositions()) {
                if (pos.equals(openPosition)) return fDoor;
            }

            for (BlockPos closePosition : fDoor.getClosePositions()) {
                if (pos.equals(closePosition)) return fDoor;
            }
        }
        Debug(FDoorEventHandler.class, "Faction door not found: " + pos.getX() + "/" + pos.getY() + "/" + pos.getZ());
        return null;
    }

    private static class FDoor {
        private final Set<BlockPos> openPositions = new HashSet<>();
        private final Set<BlockPos> closePositions = new HashSet<>();

        private FDoor(BlockPos lowerCorner, BlockPos upperCorner) {
            int x1 = lowerCorner.getX();
            int x2 = upperCorner.getX();

            boolean xConstant = x1 == x2;
            int relevantCoordinate1 = xConstant ? lowerCorner.getZ() : x1;
            int relevantCoordinate2 = xConstant ? upperCorner.getZ() : x2;

            int constantCoordinate = xConstant ? x1 : lowerCorner.getZ(); // irrelevant which X or Z coordinate to pick as those are identical

            // relevantCoordinate1 should be less than relevantCoordinate2 in order to prevent an endless loop
            if (relevantCoordinate1 > relevantCoordinate2) {
                // swap variables
                int temp = relevantCoordinate1;

                relevantCoordinate1 = relevantCoordinate2;
                relevantCoordinate2 = temp;
            }

            for (int i = relevantCoordinate1; i <= relevantCoordinate2; i++) {
                BlockPos closePos = new BlockPos(xConstant ? constantCoordinate : i, lowerCorner.getY() - 1, !xConstant ? constantCoordinate : i);
                closePositions.add(closePos);

                for (int y = lowerCorner.getY(); y <= upperCorner.getY(); y++) {
                    BlockPos openPos = new BlockPos(xConstant ? constantCoordinate : i, y, !xConstant ? constantCoordinate : i);
                    openPositions.add(openPos);
                }
            }
        }

        public boolean canBeToggled() {
            boolean set = false;
            boolean open = false;
            for (BlockPos openPosition : openPositions) {
                IBlockState blockState = AbstractionLayer.getPlayer().getWorld().getBlockState(openPosition);
                boolean air = blockState.getBlock() instanceof BlockAir;
                if (!set) {
                    open = air;
                    set = true;
                } else {
                    if (air != open) return false;
                }
            }
            return true;
        }

        public Set<BlockPos> getOpenPositions() {
            return openPositions;
        }

        public Set<BlockPos> getClosePositions() {
            return closePositions;
        }
    }
}