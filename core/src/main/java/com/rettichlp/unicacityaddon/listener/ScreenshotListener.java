package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.builder.ScreenshotBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.events.BombRemovedEvent;
import com.rettichlp.unicacityaddon.base.events.ReinforcementAcceptedEvent;
import net.labymod.api.event.Subscribe;

import java.io.File;
import java.io.IOException;

/**
 * @author RettichLP
 */
@UCEvent
public class ScreenshotListener {

    private final UnicacityAddon unicacityAddon;

    public ScreenshotListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onBombRemoved(BombRemovedEvent e) {
        Faction faction = this.unicacityAddon.player().getFaction();
        if (this.unicacityAddon.configuration().bombScreenshot().get() && (faction.equals(Faction.POLIZEI) || faction.equals(Faction.FBI))) {
            try {
                File file = this.unicacityAddon.services().fileService().getNewActivityImageFile("großeinsatz");
                ScreenshotBuilder.getBuilder(this.unicacityAddon).file(file).save();
            } catch (IOException ex) {
                this.unicacityAddon.logger().warn(ex.getMessage());
            }
        }
    }

    @Subscribe
    public void onReinforcementAccepted(ReinforcementAcceptedEvent e) {
        if (this.unicacityAddon.configuration().reinforcementSetting().screen().get()) {
            try {
                File file = this.unicacityAddon.services().fileService().getNewActivityImageFile("reinforcement");
                ScreenshotBuilder.getBuilder(this.unicacityAddon).file(file).save();
            } catch (IOException ex) {
                this.unicacityAddon.logger().warn(ex.getMessage());
            }
        }
    }
}