package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.events.UnicacityAddonTickEvent;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import org.spongepowered.include.com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author RettichLP
 */
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

            this.unicacityAddon.labyAPI().eventBus().fire(new UnicacityAddonTickEvent(this.unicacityAddon, UnicacityAddonTickEvent.Phase.TICK));

            // 0,25 SECONDS
            if (currentTick % 5 == 0) {
                this.unicacityAddon.labyAPI().eventBus().fire(new UnicacityAddonTickEvent(this.unicacityAddon, UnicacityAddonTickEvent.Phase.TICK_5));
            }

            // 1 SECOND
            if (currentTick % 20 == 0) {
                this.unicacityAddon.labyAPI().eventBus().fire(new UnicacityAddonTickEvent(this.unicacityAddon, UnicacityAddonTickEvent.Phase.SECOND));
            }

            // 3 SECONDS
            if (currentTick % 60 == 0) {
                this.unicacityAddon.labyAPI().eventBus().fire(new UnicacityAddonTickEvent(this.unicacityAddon, UnicacityAddonTickEvent.Phase.SECOND_3));
            }

            // 5 SECONDS
            if (currentTick % 100 == 0) {
                this.unicacityAddon.labyAPI().eventBus().fire(new UnicacityAddonTickEvent(this.unicacityAddon, UnicacityAddonTickEvent.Phase.SECOND_5));
            }

            // 30 SECONDS
            if (currentTick % 600 == 0) {
                this.unicacityAddon.labyAPI().eventBus().fire(new UnicacityAddonTickEvent(this.unicacityAddon, UnicacityAddonTickEvent.Phase.SECOND_30));
            }

            // 1 MINUTE
            if (currentTick % 1200 == 0) {
                this.unicacityAddon.labyAPI().eventBus().fire(new UnicacityAddonTickEvent(this.unicacityAddon, UnicacityAddonTickEvent.Phase.MINUTE));
            }
        }
    }

    @Subscribe
    public void onUnicacityAddonTick(UnicacityAddonTickEvent e) {
        if (e.isIngame() && e.isPhase(UnicacityAddonTickEvent.Phase.TICK)) {
            float currentHeal = this.unicacityAddon.player().getPlayer().getHealth();
            if (lastTickDamage.getValue() > currentHeal) {
                lastTickDamage = Maps.immutableEntry(System.currentTimeMillis(), currentHeal);
            } else if (lastTickDamage.getValue() < currentHeal) {
                lastTickDamage = Maps.immutableEntry(System.currentTimeMillis(), currentHeal);
            }
        }

        if (e.isPhase(UnicacityAddonTickEvent.Phase.SECOND)) {
            if (this.unicacityAddon.services().fileService().data().getTimer() > 0) {
                this.unicacityAddon.services().fileService().data().setTimer(this.unicacityAddon.services().fileService().data().getTimer() - 1);
            }
        }

        if (e.isUnicacity() && e.isPhase(UnicacityAddonTickEvent.Phase.SECOND) && this.unicacityAddon.configuration().nameTagSetting().corpse().get()) {
            this.unicacityAddon.deadBodyController().updateDisplayName(this.unicacityAddon);
        }
    }
}