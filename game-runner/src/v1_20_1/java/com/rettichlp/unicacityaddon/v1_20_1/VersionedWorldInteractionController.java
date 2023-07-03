package com.rettichlp.unicacityaddon.v1_20_1;

import com.rettichlp.unicacityaddon.controller.WorldInteractionController;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.vector.FloatVector3;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.WallBannerBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

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
        assert Minecraft.getInstance().cameraEntity != null;
        HitResult hitResult = Minecraft.getInstance().cameraEntity.pick(20, 0, false);
        Vec3 vec3 = hitResult.getLocation();

        return new FloatVector3((float) vec3.x(), (float) vec3.y(), (float) vec3.z());
    }

    @Override
    public boolean isHouseNumberSign(FloatVector3 location) {
        ClientLevel clientLevel = Minecraft.getInstance().level;
        BlockPos blockPos = new BlockPos((int) location.getX(), (int) location.getY(), (int) location.getZ());

        assert clientLevel != null;
        if (clientLevel.getBlockState(blockPos).getBlock() instanceof SignBlock) {
            SignBlockEntity signBlockEntity = (SignBlockEntity) clientLevel.getBlockEntity(blockPos);

            if (signBlockEntity != null) {
                String text = signBlockEntity.getFrontText().getMessage(1, false).getString();
                return Pattern.compile("^== (\\d+) ==$").matcher(text).find();
            }
        }
        return false;
    }

    @Override
    public boolean isBanner(FloatVector3 location) {
        ClientLevel clientLevel = Minecraft.getInstance().level;
        BlockPos blockPos = new BlockPos((int) location.getX(), (int) location.getY(), (int) location.getZ());

        assert clientLevel != null;
        return clientLevel.getBlockState(blockPos).getBlock() instanceof WallBannerBlock;
    }

    @Override
    public boolean isPlant(FloatVector3 location) {
        ClientLevel clientLevel = Minecraft.getInstance().level;

        assert clientLevel != null;
        boolean isFern = clientLevel.getBlockState(new BlockPos((int) location.getX(), (int) location.getY(), (int) location.getZ())).getBlock().equals(Blocks.FERN);
        boolean isPodzol = clientLevel.getBlockState(new BlockPos((int) location.getX(), ((int) location.getY()) - 1, (int) location.getZ())).getBlock().equals(Blocks.PODZOL);

        return isFern && isPodzol;
    }

    @Override
    public Collection<FloatVector3> getFireBlocksInBox(FloatVector3 one, FloatVector3 two) {
        BlockPos oneBlockPos = new BlockPos((int) one.getX(), (int) one.getY(), (int) one.getZ());
        BlockPos twoBlockPos = new BlockPos((int) two.getX(), (int) two.getY(), (int) two.getZ());

        return StreamSupport.stream(BlockPos.betweenClosed(oneBlockPos, twoBlockPos).spliterator(), false)
                .filter(blockPos -> {
                    assert Minecraft.getInstance().level != null;
                    return Minecraft.getInstance().level.getBlockState(blockPos).getBlock().equals(Blocks.FIRE);
                })
                .map(blockPos -> new FloatVector3(blockPos.getX(), blockPos.getY(), blockPos.getZ()))
                .collect(Collectors.toList());
    }
}
