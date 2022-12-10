package com.rettichlp.unicacityaddon.events.faction.badfaction;

import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import com.rettichlp.unicacityaddon.modules.PlantFertilizeTimerModule;
import com.rettichlp.unicacityaddon.modules.PlantWaterTimerModule;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 * @author Dimiikou
 */
@UCEvent
public class PlantEventHandler {

//    @Subscribe
//    public void onPlayerInteract(PlayerInteractEvent e) {
//        if (!(e instanceof PlayerInteractEvent.RightClickBlock) || e.getHand().equals(EnumHand.OFF_HAND) || !UnicacityAddon.isUnicacity()) return;
//
//        World world = e.getWorld();
//        BlockPos clickedBlockPos = e.getPos();
//        BlockPos bottomBlockPos = e.getPos().down();
//
//        boolean clickedBlockIsFern = world.getBlockState(clickedBlockPos).equals(Blocks.TALLGRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.FERN));
//        boolean bottomBlockIsPodzol = world.getBlockState(bottomBlockPos).equals(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL));
//
//        if (!clickedBlockIsFern || !bottomBlockIsPodzol) return;
//
//        AbstractionLayer.getPlayer().sendChatMessage("/plant");
//    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();

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
                    PlantWaterTimerModule.timer = TextUtils.parseTime(TimeUnit.SECONDS, PlantWaterTimerModule.currentCount);
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