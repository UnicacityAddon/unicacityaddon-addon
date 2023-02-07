package com.rettichlp.unicacityaddon.events;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import lombok.NoArgsConstructor;

@UCEvent
@NoArgsConstructor
public class TickEventHandler {

    public static int currentTick = 0;

    public TickEventHandler(UnicacityAddon unicacityAddon) {
    }

    //    @Subscribe
//    public void onTick(TickEvent.ClientTickEvent event) {
//        if (event.phase != TickEvent.Phase.END) return;
//
//        currentTick++;
//
//        // EVERY TICK
//        handleReinforcementScreenshot();
//
//        // 1 SECOND
//        if (currentTick % 20 == 0) {
//            handleNameTag();
//            handleBombTimer();
//            handleExplosiveBeltTimer();
//            handleFBIHack();
//            handlePlantTimer();
//        }
//
//        // 5 SECONDS
//        if (currentTick % 100 == 0) {
//            handleScoreboardCheck();
//        }
//
//        // 60 SECONDS
//        if (currentTick % 1200 == 0) {
//            handlePayDay();
//        }
//
//        // CUSTOM SECONDS
//        String intervalString = ConfigElements.getRefreshDisplayNamesInterval();
//        int interval = 5 * 20; // every 5 seconds
//        if (MathUtils.isInteger(intervalString)) interval = Integer.parseInt(intervalString) * 20;
//        if (currentTick % interval == 0) handleNameTagSyncDisplayName();
//    }

//    private void handleReinforcementScreenshot() {
//        if (ReinforcementEventHandler.activeReinforcement < 0 || ReinforcementEventHandler.activeReinforcement + 15 != currentTick) return;
//
//        try {
//            File file = FileManager.getNewActivityImageFile("reinforcement");
//            HotkeyEventHandler.handleScreenshot(file);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    private void handleNameTag() {
//        if (UnicacityAddon.MINECRAFT.world == null) return;
//
//        List<EntityItem> items = UnicacityAddon.MINECRAFT.world.getEntities(EntityItem.class, (ent) -> ent != null && ent.hasCustomName() && ent.getItem().getItem() instanceof ItemSkull);
//        items.forEach(entityItem -> {
//            String name = entityItem.getCustomNameTag();
//            String playerName = name.substring(3);
//
//            if (!Syncer.PLAYERFACTIONMAP.containsKey(name.substring(3))) return;
//            if (name.contains("◤")) return; // already edited
//
//            String prefix = NameTagEventHandler.getPrefix(playerName, true);
//            String factionInfo = NameTagEventHandler.getFactionInfo(playerName);
//
//            if (name.startsWith(ColorCode.DARK_GRAY.getCode())) { // non-revivable
//                entityItem.setCustomNameTag(ColorCode.DARK_GRAY.getCode() + "✟" + playerName + factionInfo);
//                return;
//            }
//
//            entityItem.setCustomNameTag(prefix + "✟" + playerName + factionInfo);
//        });
//    }

    private void handleNameTagSyncDisplayName() {
        if (UnicacityAddon.MINECRAFT.clientWorld() == null) return;
        NameTagEventHandler.refreshAllDisplayNames();
    }

//    private void handleBombTimer() {
//        if (!BombTimerModule.isBomb) return;
//        if (BombTimerModule.currentCount++ >= 780)
//            BombTimerModule.timer = ColorCode.RED.getCode() + TextUtils.parseTime(TimeUnit.SECONDS, BombTimerModule.currentCount);
//        else BombTimerModule.timer = TextUtils.parseTime(TimeUnit.SECONDS, BombTimerModule.currentCount);
//        if (BombTimerModule.currentCount > 1200) BombTimerModule.stopBombTimer();
//    }

//    private void handleExplosiveBeltTimer() {
//        if (ExplosiveBeltTimerHudWidget.timer > 0)
//            ExplosiveBeltTimerHudWidget.timer--;
//    }

//    private void handleFBIHack() {
//        if (!FBIHackModule.fbiHackStarted) return;
//        if (FBIHackModule.currentCount-- <= 30)
//            FBIHackModule.timer = ColorCode.RED.getCode() + TextUtils.parseTime(TimeUnit.SECONDS, FBIHackModule.currentCount);
//        else FBIHackModule.timer = TextUtils.parseTime(TimeUnit.SECONDS, FBIHackModule.currentCount);
//        if (FBIHackModule.currentCount <= 0) FBIHackModule.stopCountdown();
//    }

//    private void handlePlantTimer() {
//        if (!PlantFertilizeTimerModule.plantRunning) return;
//
//        if (!PlantFertilizeTimerModule.timer.isEmpty() && MathUtils.isInteger(PlantFertilizeTimerModule.timer.replace(":", "")))
//            PlantFertilizeTimerModule.timer = PlantFertilizeTimerModule.calcTimer(--PlantFertilizeTimerModule.currentCount);
//
//        if (!PlantWaterTimerModule.timer.isEmpty() && MathUtils.isInteger(PlantWaterTimerModule.timer.replace(":", "")))
//            PlantWaterTimerModule.timer = TextUtils.parseTime(TimeUnit.SECONDS, --PlantWaterTimerModule.currentCount);
//
//        if (PlantFertilizeTimerModule.currentCount <= 0) PlantFertilizeTimerModule.timer = ColorCode.RED.getCode() + "Jetzt";
//        if (PlantWaterTimerModule.currentCount <= 0) PlantWaterTimerModule.timer = ColorCode.RED.getCode() + "Jetzt";
//    }


//    private void handleScoreboardCheck() {
//        if (!UnicacityAddon.isUnicacity()) return;
//        CarEventHandler.checkTank();
//    }

//    private void handlePayDay() {
//        if (AccountEventHandler.isAfk || !UnicacityAddon.isUnicacity()) return;
//        PayDayModule.addTime(1);
//    }
}