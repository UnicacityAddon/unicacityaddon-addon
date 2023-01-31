package com.rettichlp.unicacityaddon.events.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 * @author Dimiikou
 */
@UCEvent
public class PlantEventHandler {

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (!(e instanceof PlayerInteractEvent.RightClickBlock) || e.getHand().equals(EnumHand.OFF_HAND) || !UnicacityAddon.isUnicacity())
            return;

        World world = e.getWorld();
        BlockPos clickedBlockPos = e.getPos();
        BlockPos bottomBlockPos = e.getPos().down();

        boolean clickedBlockIsFern = world.getBlockState(clickedBlockPos).equals(Blocks.TALLGRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.FERN));
        boolean bottomBlockIsPodzol = world.getBlockState(bottomBlockPos).equals(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL));

        if (!clickedBlockIsFern || !bottomBlockIsPodzol)
            return;

        AbstractionLayer.getPlayer().sendChatMessage("/plant");
    }

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        if (PatternHandler.PLANT_HARVEST_PATTERN.matcher(msg).find()) {
            FileManager.DATA.setPlantFertilizeTime(0);
            FileManager.DATA.setPlantWaterTime(0);
            return;
        }

        Matcher plantUseMatcher = PatternHandler.PLANT_USE_PATTERN.matcher(msg);
        if (plantUseMatcher.find()) {
            if (msg.contains("gewässert") && System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(10) > FileManager.DATA.getPlantWaterTime()) {
                FileManager.DATA.setPlantWaterTime(System.currentTimeMillis());
            } else if (msg.contains("gedüngt") && System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(10) > FileManager.DATA.getPlantFertilizeTime()) {
                FileManager.DATA.setPlantFertilizeTime(System.currentTimeMillis());
            }
        }
    }
}