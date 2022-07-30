package com.rettichlp.UnicacityAddon.events.faction.badfaction;

import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.base.utils.MathUtils;
import com.rettichlp.UnicacityAddon.modules.PlantFertilizeTimerModule;
import com.rettichlp.UnicacityAddon.modules.PlantWaterTimerModule;
import net.labymod.utils.ModUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
public class PlantTimerEventHandler {

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        Matcher plantHarvestMatcher = PatternHandler.PLANT_HARVEST_PATTERN.matcher(msg);

        if (plantHarvestMatcher.find()) {
            PlantFertilizeTimerModule.stopPlant();
            PlantWaterTimerModule.stopPlant();
            return false;
        }

        Matcher plantUseMatcher = PatternHandler.PLANT_USE_PATTERN.matcher(msg);

        if (plantUseMatcher.find()) {
            if (!PlantFertilizeTimerModule.plantRunning) PlantFertilizeTimerModule.plantRunning = true;

            if (plantUseMatcher.group(2).equals("gewässert")) {
                if (PlantWaterTimerModule.currentCount < (PlantWaterTimerModule.timeNeeded - 600)) {
                    PlantWaterTimerModule.currentCount = PlantWaterTimerModule.timeNeeded;
                    PlantWaterTimerModule.timer = ModUtils.parseTimer(PlantWaterTimerModule.currentCount);
                }
                return false;
            }

            if (PlantFertilizeTimerModule.currentCount < (PlantFertilizeTimerModule.timeNeeded - 600)) {
                PlantFertilizeTimerModule.currentCount = PlantFertilizeTimerModule.timeNeeded;
                PlantFertilizeTimerModule.timer = PlantFertilizeTimerModule.calcTimer(PlantFertilizeTimerModule.currentCount);
            }

        }

        return false;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        if (!PlantFertilizeTimerModule.plantRunning || ++PlantFertilizeTimerModule.currentTick != 20) return;
        PlantFertilizeTimerModule.currentTick = 0;

        if (!PlantFertilizeTimerModule.timer.isEmpty() && MathUtils.isInteger(PlantFertilizeTimerModule.timer.replace(":", "")))
            PlantFertilizeTimerModule.timer = PlantFertilizeTimerModule.calcTimer(--PlantFertilizeTimerModule.currentCount);

        if (!PlantWaterTimerModule.timer.isEmpty() && MathUtils.isInteger(PlantWaterTimerModule.timer.replace(":", "")))
            PlantWaterTimerModule.timer = ModUtils.parseTimer(--PlantWaterTimerModule.currentCount);

        if (PlantFertilizeTimerModule.currentCount <= 0) PlantFertilizeTimerModule.timer = ColorCode.RED.getCode() + "Jetzt";
        if (PlantWaterTimerModule.currentCount <= 0) PlantFertilizeTimerModule.timer = ColorCode.RED.getCode() + "Jetzt";
    }
}
