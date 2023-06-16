package com.rettichlp.unicacityaddon.events;

import com.google.common.collect.Maps;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.api.checks.BroadcastChecker;
import com.rettichlp.unicacityaddon.base.config.ConfigElements;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import com.rettichlp.unicacityaddon.commands.BusCommand;
import com.rettichlp.unicacityaddon.commands.faction.badfaction.DropDrugAllCommand;
import com.rettichlp.unicacityaddon.events.faction.ReinforcementEventHandler;
import com.rettichlp.unicacityaddon.events.faction.terroristen.BombTimerEventHandler;
import com.rettichlp.unicacityaddon.events.house.HouseInteractionEventHandler;
import com.rettichlp.unicacityaddon.modules.BombTimerModule;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemSkull;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@UCEvent
public class TickEventHandler {

    public static int currentTick = 0;

    public static Map.Entry<Long, Float> lastTickDamage = Maps.immutableEntry(0L, 0F);

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && UnicacityAddon.MINECRAFT.world != null) {
            currentTick++;

            // EVERY TICK
            handleReinforcementScreenshot();
            handleBombScreenshot();
            handleDamageTracker();

            // 0,25 SECONDS
            if (currentTick % 5 == 0) {
                BusCommand.process();
                DropDrugAllCommand.process();
            }

            // 1 SECOND
            if (currentTick % 20 == 0) {
                handleNameTag();
                handleBombTimer();
                handleTimer();
                handleCustomSeconds();
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

            // 30 SECONDS
            if (currentTick % 600 == 0) {
                if (!UnicacityAddon.isUnicacity()) {
                    BroadcastChecker.checkForBroadcast();
                }
                GangwarEventHandler.sendData();
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
                HotkeyEventHandler.handleScreenshot(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handleBombScreenshot() {
        if (BombTimerEventHandler.activeBomb >= 0 && BombTimerEventHandler.activeBomb + 15 == currentTick) {
            try {
                File file = FileManager.getNewActivityImageFile("großeinsatz");
                HotkeyEventHandler.handleScreenshot(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handleDamageTracker() {
        float currentHeal = AbstractionLayer.getPlayer().getPlayer().getHealth();
        if (lastTickDamage.getValue() > currentHeal) {
            lastTickDamage = Maps.immutableEntry(System.currentTimeMillis(), currentHeal);
        } else if (lastTickDamage.getValue() < currentHeal) {
            lastTickDamage = Maps.immutableEntry(System.currentTimeMillis(), currentHeal);
        }
    }

    private void handleNameTag() {
        List<EntityItem> items = UnicacityAddon.MINECRAFT.world.getEntities(EntityItem.class, (ent) -> ent != null && ent.hasCustomName() && ent.getItem().getItem() instanceof ItemSkull);
        items.forEach(entityItem -> {
            String name = entityItem.getCustomNameTag();
            String playerName = name.substring(3);

            if (Syncer.PLAYERFACTIONMAP.containsKey(playerName) && !name.contains("◤")) {
                String prefix = NameTagEventHandler.getPrefix(playerName, true);
                String factionInfo = NameTagEventHandler.getFactionInfo(playerName);

                if (name.startsWith(ColorCode.DARK_GRAY.getCode())) { // non-revivable
                    entityItem.setCustomNameTag(ColorCode.DARK_GRAY.getCode() + "✟" + playerName + factionInfo);
                    return;
                }

                entityItem.setCustomNameTag(prefix + "✟" + playerName + factionInfo);
            }
        });
    }

    private void handleBombTimer() {
        if (!BombTimerModule.isBomb)
            return;
        if (BombTimerModule.currentCount++ >= 780)
            BombTimerModule.timer = ColorCode.RED.getCode() + TextUtils.parseTimer(BombTimerModule.currentCount);
        else
            BombTimerModule.timer = TextUtils.parseTimer(BombTimerModule.currentCount);
        if (BombTimerModule.currentCount > 1200)
            BombTimerModule.stopBombTimer();
    }

    private void handleTimer() {
        if (FileManager.DATA.getTimer() > 0) {
            FileManager.DATA.setTimer(FileManager.DATA.getTimer() - 1);
        }
    }

    private void handleCustomSeconds() {
        if (UnicacityAddon.isUnicacity() && UnicacityAddon.ADDON.getConfig() != null) {
            String intervalString = ConfigElements.getRefreshDisplayNamesInterval();
            if (currentTick % (MathUtils.isInteger(intervalString) ? Integer.parseInt(intervalString) * 20 : 5 * 20) == 0) {
                NameTagEventHandler.refreshAllDisplayNames();
            }
        }
    }

    private void handleScoreboardCheck() {
        Scoreboard scoreboard = AbstractionLayer.getPlayer().getWorldScoreboard();
        CarEventHandler.checkTank(scoreboard);
    }

    private void handlePayDay() {
        if (UnicacityAddon.isUnicacity() && !AccountEventHandler.isAfk) {
            FileManager.DATA.addPayDayTime(1);
        }
    }
}