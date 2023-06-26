package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.events.UnicacityAddonTickEvent;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
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
            Float currentHeal = this.unicacityAddon.player().getHealth();
            if (currentHeal != null && lastTickDamage.getValue() > currentHeal) {
                lastTickDamage = Maps.immutableEntry(System.currentTimeMillis(), currentHeal);
            } else if (currentHeal != null && lastTickDamage.getValue() < currentHeal) {
                lastTickDamage = Maps.immutableEntry(System.currentTimeMillis(), currentHeal);
            }
        }

        if (e.isPhase(UnicacityAddonTickEvent.Phase.SECOND)) {
            if (this.unicacityAddon.fileService().data().getTimer() > 0) {
                this.unicacityAddon.fileService().data().setTimer(this.unicacityAddon.fileService().data().getTimer() - 1);
            }
        }

        if (e.isUnicacity() && e.isPhase(UnicacityAddonTickEvent.Phase.SECOND) && this.unicacityAddon.configuration().nametag().corpse().get()) {
            this.unicacityAddon.deadBodyController().updateDisplayName(this.unicacityAddon);
        }
    }
}