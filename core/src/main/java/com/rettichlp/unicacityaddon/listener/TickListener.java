package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.events.UnicacityAddonTickEvent;
import net.labymod.api.event.Subscribe;
import org.spongepowered.include.com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author RettichLP
 */
@UCEvent
public class TickListener {

    public static Map.Entry<Long, Float> lastTickDamage = Maps.immutableEntry(0L, 0F);

    private final UnicacityAddon unicacityAddon;

    public TickListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
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