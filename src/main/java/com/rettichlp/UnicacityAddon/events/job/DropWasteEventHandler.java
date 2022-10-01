package com.rettichlp.UnicacityAddon.events.job;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Dimiikou
 */
@UCEvent
public class DropWasteEventHandler {

    private static boolean isDropState = false;
    private static long lastUse = -1;

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (!(e instanceof PlayerInteractEvent.RightClickBlock) || !UnicacityAddon.isUnicacity()) return;
        if (!isDropState) return;

        World world = e.getWorld();
        BlockPos blockPos = e.getPos();

        if (System.currentTimeMillis() - lastUse < 1000) return;
        lastUse = System.currentTimeMillis();

        if ((blockPos.getDistance(DropPositions.GLAS_DROP_POSITION.x, DropPositions.GLAS_DROP_POSITION.y, DropPositions.GLAS_DROP_POSITION.z) < 1.5)
                || (blockPos.getDistance(DropPositions.WASTE_DROP_POSITION.x, DropPositions.WASTE_DROP_POSITION.y, DropPositions.WASTE_DROP_POSITION.z) < 1.5)
                || (blockPos.getDistance(DropPositions.METAL_DROP_POSITION.x, DropPositions.METAL_DROP_POSITION.y, DropPositions.METAL_DROP_POSITION.z) < 1.5)
                || (blockPos.getDistance(DropPositions.WOOD_DROP_POSITION.x, DropPositions.WOOD_DROP_POSITION.y, DropPositions.WOOD_DROP_POSITION.z) < 1.5))
            dropWaste();
    }

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        Matcher wasteJobEndMatcher = PatternHandler.WASTE_JOB_END_PATTERN.matcher(msg);
        if (wasteJobEndMatcher.find()) {
            isDropState = true;
            return;
        }

        if (msg.equals("[Müllmann] Du hast den Job beendet.")) isDropState = false;
    }

    private void dropWaste() {
        if (isDropState) AbstractionLayer.getPlayer().sendChatMessage("/dropwaste");
    }

    public enum DropPositions {
        GLAS_DROP_POSITION(885, 67, 350),
        WASTE_DROP_POSITION(906, 66, 361),
        METAL_DROP_POSITION(899, 67, 394),
        WOOD_DROP_POSITION(874, 68, 378);

        int x;
        int y;
        int z;

        DropPositions(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }
    }
}
