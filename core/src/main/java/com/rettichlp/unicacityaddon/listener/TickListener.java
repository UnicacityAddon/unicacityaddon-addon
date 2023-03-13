package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.api.checks.BroadcastChecker;
import com.rettichlp.unicacityaddon.base.events.UnicacityAddonTickEvent;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.commands.BusCommand;
import com.rettichlp.unicacityaddon.commands.faction.DropDrugAllCommand;
import com.rettichlp.unicacityaddon.listener.faction.ReinforcementListener;
import com.rettichlp.unicacityaddon.listener.faction.terroristen.BombListener;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import org.spongepowered.include.com.google.common.collect.Maps;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@UCEvent
public class TickListener {

    public static int currentTick = 0;

    public static Map.Entry<Long, Float> lastTickDamage = Maps.immutableEntry(0L, 0F);

    private final UnicacityAddon unicacityAddon;

    public TickListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onGameTick(GameTickEvent e) {
        if (e.phase().equals(Phase.POST)) {
            currentTick++;

            this.unicacityAddon.labyAPI().eventBus().fire(new UnicacityAddonTickEvent(UnicacityAddonTickEvent.Phase.TICK));

            // 0,25 SECONDS
            if (currentTick % 5 == 0) {
                this.unicacityAddon.labyAPI().eventBus().fire(new UnicacityAddonTickEvent(UnicacityAddonTickEvent.Phase.TICK_5));
            }

            // 1 SECOND
            if (currentTick % 20 == 0) {
                this.unicacityAddon.labyAPI().eventBus().fire(new UnicacityAddonTickEvent(UnicacityAddonTickEvent.Phase.SECOND));
            }

            // 3 SECONDS
            if (currentTick % 60 == 0) {
                this.unicacityAddon.labyAPI().eventBus().fire(new UnicacityAddonTickEvent(UnicacityAddonTickEvent.Phase.SECOND_3));
            }

            // 5 SECONDS
            if (currentTick % 100 == 0) {
                this.unicacityAddon.labyAPI().eventBus().fire(new UnicacityAddonTickEvent(UnicacityAddonTickEvent.Phase.SECOND_5));
            }

            // 30 SECONDS
            if (currentTick % 600 == 0) {
                this.unicacityAddon.labyAPI().eventBus().fire(new UnicacityAddonTickEvent(UnicacityAddonTickEvent.Phase.SECOND_30));
            }

            // 1 MINUTE
            if (currentTick % 1200 == 0) {
                this.unicacityAddon.labyAPI().eventBus().fire(new UnicacityAddonTickEvent(UnicacityAddonTickEvent.Phase.MINUTE));
            }
        }
    }

    @Subscribe
    public void onUnicacityAddonTick(UnicacityAddonTickEvent e) {
        if (e.isIngame() && e.isPhase(UnicacityAddonTickEvent.Phase.TICK)) {
            handleReinforcementScreenshot();
            handleBombScreenshot();
            handleDamageTracker();
        }

        if (e.isPhase(UnicacityAddonTickEvent.Phase.TICK_5)) {
            BusCommand.process();
            DropDrugAllCommand.process();
        }

        if (e.isPhase(UnicacityAddonTickEvent.Phase.SECOND)) {
            handleNameTag();
            handleTimer();
        }

        if (e.isPhase(UnicacityAddonTickEvent.Phase.SECOND_30)) {
            BroadcastChecker.checkForBroadcast();
        }

        if (e.isPhase(UnicacityAddonTickEvent.Phase.MINUTE)) {
            handlePayDay();
        }
    }

    private void handleReinforcementScreenshot() {
        if (ReinforcementListener.activeReinforcement >= 0 && ReinforcementListener.activeReinforcement + 15 == currentTick) {
            try {
                File file = FileManager.getNewActivityImageFile("reinforcement");
                //HotkeyListener.handleScreenshot(file);
            } catch (IOException e) {
                this.unicacityAddon.logger().warn(e.getMessage());
            }
        }
    }

    private void handleBombScreenshot() {
        if (BombListener.activeBomb >= 0 && BombListener.activeBomb + 15 == currentTick) {
            try {
                File file = FileManager.getNewActivityImageFile("großeinsatz");
                //HotkeyEventHandler.handleScreenshot(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
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

    private void handleNameTag() {
//        List<EntityItem> items = UnicacityAddon.MINECRAFT.world.getEntities(EntityItem.class, (ent) -> ent != null && ent.hasCustomName() && ent.getItem().getItem() instanceof ItemSkull);
//        items.forEach(entityItem -> {
//            String name = entityItem.getCustomNameTag();
//            String playerName = name.substring(3);
//
//            if (Syncer.getPlayerfactionMap().containsKey(playerName) && !name.contains("◤")) {
//                String prefix = NameTagListener.getPrefix(playerName, true);
//                String factionInfo = NameTagListener.getFactionInfo(playerName);
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

    private void handleTimer() {
        if (FileManager.DATA.getTimer() > 0) {
            FileManager.DATA.setTimer(FileManager.DATA.getTimer() - 1);
        }
    }

    private void handlePayDay() {
        if (UnicacityAddon.isUnicacity() && !AccountListener.isAfk) {
            FileManager.DATA.addPayDayTime(1);
        }
    }
}