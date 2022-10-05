package com.rettichlp.UnicacityAddon.events.faction.badfaction;

import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.modules.PlantFertilizeTimerModule;
import com.rettichlp.UnicacityAddon.modules.PlantWaterTimerModule;
import net.labymod.utils.ModUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author Dimiikou
 */
@UCEvent
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
}