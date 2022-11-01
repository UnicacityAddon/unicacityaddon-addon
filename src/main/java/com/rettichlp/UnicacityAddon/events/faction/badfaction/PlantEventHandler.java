package com.rettichlp.UnicacityAddon.events.faction.badfaction;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author RettichLP
 */
@UCEvent
public class PlantEventHandler {

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (!(e instanceof PlayerInteractEvent.RightClickBlock) || e.getHand().equals(EnumHand.OFF_HAND) || !UnicacityAddon.isUnicacity()) return;

        World world = e.getWorld();
        BlockPos clickedBlockPos = e.getPos();
        BlockPos bottomBlockPos = e.getPos().down();

        boolean clickedBlockIsFern = world.getBlockState(clickedBlockPos).equals(Blocks.TALLGRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.FERN));
        boolean bottomBlockIsPodzol = world.getBlockState(bottomBlockPos).equals(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL));

        if (!clickedBlockIsFern || !bottomBlockIsPodzol) return;

        AbstractionLayer.getPlayer().sendChatMessage("/plant");
    }
}