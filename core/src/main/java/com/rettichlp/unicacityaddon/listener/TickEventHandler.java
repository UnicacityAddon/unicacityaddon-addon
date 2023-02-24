package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.commands.BusCommand;
import com.rettichlp.unicacityaddon.listener.faction.ReinforcementEventHandler;
import com.rettichlp.unicacityaddon.listener.house.HouseInteractionEventHandler;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import org.spongepowered.include.com.google.common.collect.Maps;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@UCEvent
public class TickEventHandler {

    public static int currentTick = -1;

    public static Map.Entry<Long, Float> lastTickDamage = Maps.immutableEntry(0L, 0F);

    private final UnicacityAddon unicacityAddon;

    public TickEventHandler(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onGameTick(GameTickEvent e) {
        UnicacityAddon.ADDON.labyAPI().eventBus().fire(new UnicacityAddonTickEvent(UnicacityAddonTickEvent.Phase.TICK));

        // TODO: 24.02.2023 transform to UnicacityAddonTickEvent
        if (currentTick >= 0) {
            currentTick++;

            // EVERY TICK
            handleReinforcementScreenshot();
            handleDamageTracker();
            handleBusTracker();

            // 1 SECOND
            if (currentTick % 20 == 0) {
                UnicacityAddon.ADDON.labyAPI().eventBus().fire(new UnicacityAddonTickEvent(UnicacityAddonTickEvent.Phase.SECOND));
                handleNameTag();
                handleBombTimer();
                handleTimer();
            }

            // 3 SECONDS
            if (currentTick % 60 == 0) {
                HouseInteractionEventHandler.increaseProgress(1);
            }

            // 5 SECONDS
            if (currentTick % 100 == 0) {
                handleScoreboardCheck();
                HouseInteractionEventHandler.increaseProgress(0);
            }

            // 60 SECONDS
            if (currentTick % 1200 == 0) {
                handlePayDay();
            }
        }
    }

    private void handleReinforcementScreenshot() {
        if (ReinforcementEventHandler.activeReinforcement >= 0 && ReinforcementEventHandler.activeReinforcement + 15 == currentTick) {
            try {
                File file = FileManager.getNewActivityImageFile("reinforcement");
                //HotkeyEventHandler.handleScreenshot(file);
            } catch (IOException e) {
                UnicacityAddon.LOGGER.warn(e.getMessage());
            }
        }
    }

    private void handleDamageTracker() {
        float currentHeal = UnicacityAddon.PLAYER.getPlayer().getHealth();
        if (lastTickDamage.getValue() > currentHeal) {
            lastTickDamage = Maps.immutableEntry(System.currentTimeMillis(), currentHeal);
        } else if (lastTickDamage.getValue() < currentHeal) {
            lastTickDamage = Maps.immutableEntry(System.currentTimeMillis(), currentHeal);
        }
    }

    private void handleBusTracker() {
        BusCommand.process();
    }

    private void handleNameTag() {
//        List<EntityItem> items = UnicacityAddon.MINECRAFT.world.getEntities(EntityItem.class, (ent) -> ent != null && ent.hasCustomName() && ent.getItem().getItem() instanceof ItemSkull);
//        items.forEach(entityItem -> {
//            String name = entityItem.getCustomNameTag();
//            String playerName = name.substring(3);
//
//            if (Syncer.getPlayerfactionMap().containsKey(playerName) && !name.contains("◤")) {
//                String prefix = NameTagEventHandler.getPrefix(playerName, true);
//                String factionInfo = NameTagEventHandler.getFactionInfo(playerName);
//
//                if (name.startsWith(ColorCode.DARK_GRAY.getCode())) { // non-revivable
//                    entityItem.setCustomNameTag(ColorCode.DARK_GRAY.getCode() + "✟" + playerName + factionInfo);
//                    return;
//                }
//
//                entityItem.setCustomNameTag(prefix + "✟" + playerName + factionInfo);
//            }
//        });
    }

    private void handleBombTimer() {
//        if (!BombTimerModule.isBomb)
//            return;
//        if (BombTimerModule.currentCount++ >= 780)
//            BombTimerModule.timer = ColorCode.RED.getCode() + TextUtils.parseTimer(BombTimerModule.currentCount);
//        else
//            BombTimerModule.timer = TextUtils.parseTimer(BombTimerModule.currentCount);
//        if (BombTimerModule.currentCount > 1200)
//            BombTimerModule.stopBombTimer();
    }

    private void handleTimer() {
        if (FileManager.DATA.getTimer() > 0) {
            FileManager.DATA.setTimer(FileManager.DATA.getTimer() - 1);
        } else {
            FileManager.DATA.setTimer(0);
        }
    }

    private void handleScoreboardCheck() {
        CarEventHandler.checkTank();
    }

    private void handlePayDay() {
        if (UnicacityAddon.isUnicacity() && !AccountEventHandler.isAfk) {
            FileManager.DATA.addPayDayTime(1);
        }
    }
}