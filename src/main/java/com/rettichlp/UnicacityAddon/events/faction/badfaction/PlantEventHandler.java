package com.rettichlp.UnicacityAddon.events.faction.badfaction;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.modules.PlantFertilizeTimerModule;
import com.rettichlp.UnicacityAddon.modules.PlantWaterTimerModule;
import net.labymod.utils.ModUtils;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author RettichLP
 * @author Dimiikou
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

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        if (PatternHandler.PLANT_HARVEST_PATTERN.matcher(msg).find()) {
            PlantFertilizeTimerModule.stopPlant();
            PlantWaterTimerModule.stopPlant();
            return;
        }

        Matcher plantUseMatcher = PatternHandler.PLANT_USE_PATTERN.matcher(msg);
        if (plantUseMatcher.find()) {
            if (!PlantFertilizeTimerModule.plantRunning) PlantFertilizeTimerModule.plantRunning = true;

            if (plantUseMatcher.group(2).equals("gewässert")) {
                if (PlantWaterTimerModule.currentCount < (PlantWaterTimerModule.timeNeeded - 600)) {
                    PlantWaterTimerModule.currentCount = PlantWaterTimerModule.timeNeeded;
                    PlantWaterTimerModule.timer = ModUtils.parseTimer(PlantWaterTimerModule.currentCount);
                }
                return;
            }

            if (PlantFertilizeTimerModule.currentCount < (PlantFertilizeTimerModule.timeNeeded - 600)) {
                PlantFertilizeTimerModule.currentCount = PlantFertilizeTimerModule.timeNeeded;
                PlantFertilizeTimerModule.timer = PlantFertilizeTimerModule.calcTimer(PlantFertilizeTimerModule.currentCount);
            }
        }
    }
}