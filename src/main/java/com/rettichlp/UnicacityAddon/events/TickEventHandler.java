package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.api.Syncer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.utils.MathUtils;
import com.rettichlp.UnicacityAddon.modules.BombTimerModule;
import com.rettichlp.UnicacityAddon.modules.ExplosiveBeltTimerModule;
import com.rettichlp.UnicacityAddon.modules.FBIHackModule;
import com.rettichlp.UnicacityAddon.modules.PayDayModule;
import com.rettichlp.UnicacityAddon.modules.PlantFertilizeTimerModule;
import com.rettichlp.UnicacityAddon.modules.PlantWaterTimerModule;
import net.labymod.utils.ModUtils;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemSkull;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

@UCEvent
public class TickEventHandler {

    private int currentTick = 0;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        currentTick++;

        // 1 SECOND
        if (currentTick % 20 == 0) {
            handleNameTag();
            handleBombTimer();
            handleExplosiveBeltTimer();
            handleFBIHack();
            handlePlantTimer();
        }

        // 60 SECONDS
        if (currentTick % 1200 == 0) handlePayDay();

        // CUSTOM SECONDS
        String intervalString = ConfigElements.getRefreshDisplayNamesInterval();
        int interval = 5 * 20; // every 5 seconds
        if (MathUtils.isInteger(intervalString)) interval = Integer.parseInt(intervalString) * 20;
        if (currentTick % interval == 0) handleNameTagSyncDisplayName();
    }

    private void handleNameTag() {
        if (UnicacityAddon.MINECRAFT.world == null) return;

        List<EntityItem> items = UnicacityAddon.MINECRAFT.world.getEntities(EntityItem.class, (ent) -> ent != null && ent.hasCustomName() && ent.getItem().getItem() instanceof ItemSkull);
        items.forEach(entityItem -> {
            String name = entityItem.getCustomNameTag();
            String playerName = name.substring(3);

            if (!Syncer.getPlayerFactionMap().containsKey(name.substring(3))) return;
            if (name.contains("◤")) return; // already edited

            String prefix = NameTagEventHandler.getPrefix(playerName, true);
            String factionInfo = NameTagEventHandler.getFactionInfo(playerName);

            if (name.startsWith(ColorCode.DARK_GRAY.getCode())) { // non-revivable
                entityItem.setCustomNameTag(ColorCode.DARK_GRAY.getCode() + "✟" + playerName + factionInfo);
                return;
            }

            entityItem.setCustomNameTag(prefix + "✟" + playerName + factionInfo);
        });
    }

    private void handleNameTagSyncDisplayName() {
        if (UnicacityAddon.MINECRAFT.world == null) return;
        NameTagEventHandler.refreshAllDisplayNames();
    }

    private void handleBombTimer() {
        if (!BombTimerModule.isBomb) return;
        if (BombTimerModule.currentCount++ >= 780)
            BombTimerModule.timer = ColorCode.RED.getCode() + ModUtils.parseTimer(BombTimerModule.currentCount);
        else BombTimerModule.timer = ModUtils.parseTimer(BombTimerModule.currentCount);
        if (BombTimerModule.currentCount > 1200) BombTimerModule.stopBombTimer();
    }

    private void handleExplosiveBeltTimer() {
        if (!ExplosiveBeltTimerModule.explosiveBeltStarted) return;
        ExplosiveBeltTimerModule.currentCount--;
        ExplosiveBeltTimerModule.timer = String.valueOf(ExplosiveBeltTimerModule.currentCount);
        if (ExplosiveBeltTimerModule.currentCount <= 0) ExplosiveBeltTimerModule.stopBombTimer();
    }

    private void handleFBIHack() {
        if (!FBIHackModule.fbiHackStarted) return;
        if (FBIHackModule.currentCount-- <= 30)
            FBIHackModule.timer = ColorCode.RED.getCode() + ModUtils.parseTimer(FBIHackModule.currentCount);
        else FBIHackModule.timer = ModUtils.parseTimer(FBIHackModule.currentCount);
        if (FBIHackModule.currentCount <= 0) FBIHackModule.stopCountdown();
    }

    private void handlePlantTimer() {
        if (!PlantFertilizeTimerModule.plantRunning) return;

        if (!PlantFertilizeTimerModule.timer.isEmpty() && MathUtils.isInteger(PlantFertilizeTimerModule.timer.replace(":", "")))
            PlantFertilizeTimerModule.timer = PlantFertilizeTimerModule.calcTimer(--PlantFertilizeTimerModule.currentCount);

        if (!PlantWaterTimerModule.timer.isEmpty() && MathUtils.isInteger(PlantWaterTimerModule.timer.replace(":", "")))
            PlantWaterTimerModule.timer = ModUtils.parseTimer(--PlantWaterTimerModule.currentCount);

        if (PlantFertilizeTimerModule.currentCount <= 0) PlantFertilizeTimerModule.timer = ColorCode.RED.getCode() + "Jetzt";
        if (PlantWaterTimerModule.currentCount <= 0) PlantWaterTimerModule.timer = ColorCode.RED.getCode() + "Jetzt";
    }

    private void handlePayDay() {
        if (PayDayEventHandler.isAfk || !UnicacityAddon.isUnicacity()) return;
        PayDayModule.addTime(1);
    }
}