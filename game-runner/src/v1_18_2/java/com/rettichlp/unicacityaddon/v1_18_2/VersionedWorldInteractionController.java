package com.rettichlp.unicacityaddon.v1_18_2;

import com.rettichlp.unicacityaddon.controller.WorldInteractionController;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.vector.FloatVector3;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.WallBannerBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.inject.Singleton;
import java.util.regex.Pattern;

/**
 * @author RettichLP
 */
@Singleton
@Implements(WorldInteractionController.class)
public class VersionedWorldInteractionController extends WorldInteractionController {

    @Override
    public FloatVector3 getClickedBlockLocation() {
        HitResult hitResult = Minecraft.getInstance().cameraEntity.pick(20, 0, false);
        Vec3 vec3 = hitResult.getLocation();

        return new FloatVector3((float) vec3.x(), (float) vec3.y(), (float) vec3.z());
    }

    @Override
    public boolean isHouseNumberSign(FloatVector3 pos) {
        ClientLevel clientLevel = Minecraft.getInstance().level;
        BlockPos blockPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());

        assert clientLevel != null;
        if (clientLevel.getBlockState(blockPos).getBlock() instanceof SignBlock) {
            SignBlockEntity signBlockEntity = (SignBlockEntity) clientLevel.getBlockEntity(blockPos);

            if (signBlockEntity != null) {
                String text = signBlockEntity.getMessage(1, false).getString();
                return Pattern.compile("^== (\\d+) ==$").matcher(text).find();
            }
        }
        return false;
    }

    @Override
    public boolean isBanner(FloatVector3 pos) {
        ClientLevel clientLevel = Minecraft.getInstance().level;
        BlockPos blockPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());

        assert clientLevel != null;
        return clientLevel.getBlockState(blockPos).getBlock() instanceof WallBannerBlock;
    }
}
