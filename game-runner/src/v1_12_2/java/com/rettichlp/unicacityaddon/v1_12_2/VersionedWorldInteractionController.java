package com.rettichlp.unicacityaddon.v1_12_2;

import com.rettichlp.unicacityaddon.controller.WorldInteractionController;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.vector.FloatVector3;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.inject.Singleton;
import java.util.Collection;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author RettichLP
 */
@Singleton
@Implements(WorldInteractionController.class)
public class VersionedWorldInteractionController extends WorldInteractionController {

    @Override
    public FloatVector3 getClickedBlockLocation() {
        BlockPos blockPos = Minecraft.getMinecraft().objectMouseOver.getBlockPos();
        return blockPos != null ? new FloatVector3(blockPos.getX(), blockPos.getY(), blockPos.getZ()) : null;
    }

    @Override
    public boolean isHouseNumberSign(FloatVector3 location) {
        World world = Minecraft.getMinecraft().world;

        TileEntity tileEntity = world.getTileEntity(new BlockPos(location.getX(), location.getY(), location.getZ()));

        if (tileEntity instanceof TileEntitySign) {
            ITextComponent[] lines = ((TileEntitySign) tileEntity).signText;
            return Pattern.compile("^== (\\d+) ==$").matcher(lines[1].getUnformattedText()).find();
        }

        return false;
    }

    @Override
    public boolean isBanner(FloatVector3 location) {
        World world = Minecraft.getMinecraft().world;
        TileEntity tileEntity = world.getTileEntity(new BlockPos(location.getX(), location.getY(), location.getZ()));
        return tileEntity instanceof TileEntityBanner;
    }

    @Override
    public boolean isPlant(FloatVector3 location) {
        World world = Minecraft.getMinecraft().world;

        boolean isFern = world.getBlockState(new BlockPos(location.getX(), location.getY(), location.getZ())).equals(Blocks.TALLGRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.FERN));
        boolean isPodzol = world.getBlockState(new BlockPos(location.getX(), location.getY() - 1, location.getZ())).equals(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL));

        return isFern && isPodzol;
    }

    @Override
    public Collection<FloatVector3> getFireBlocksInBox(FloatVector3 one, FloatVector3 two) {
        BlockPos oneBlockPos = new BlockPos(one.getX(), one.getY(), one.getZ());
        BlockPos twoBlockPos = new BlockPos(two.getX(), two.getY(), two.getZ());

        return StreamSupport.stream(BlockPos.getAllInBox(oneBlockPos, twoBlockPos).spliterator(), false)
                .filter(blockPos -> Minecraft.getMinecraft().world.getBlockState(blockPos).getBlock().equals(Blocks.FIRE))
                .map(blockPos -> new FloatVector3(blockPos.getX(), blockPos.getY(), blockPos.getZ()))
                .collect(Collectors.toList());
    }
}
