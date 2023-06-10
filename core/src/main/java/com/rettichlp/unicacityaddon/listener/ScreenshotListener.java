package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.builder.ScreenshotBuilder;
import com.rettichlp.unicacityaddon.base.events.BombRemovedEvent;
import com.rettichlp.unicacityaddon.base.events.ReinforcementAcceptedEvent;
import net.labymod.api.event.Subscribe;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

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
    public void onReinforcementAccepted(ReinforcementAcceptedEvent e) {
        // activity screenshot
        if (this.unicacityAddon.configuration().reinforcementSetting().screen().get()) {

            // wait until on-the-way message was sent (important for valid activity)
            runDelayed(() -> {
                try {
                    File file = this.unicacityAddon.services().file().getNewActivityImageFile("reinforcement");
                    ScreenshotBuilder.getBuilder(this.unicacityAddon).file(file).save();
                } catch (IOException ex) {
                    this.unicacityAddon.logger().warn(ex.getMessage());
                }
            });

        }
    }

    @Subscribe
    public void onBombRemoved(BombRemovedEvent e) {
        // activity screenshot
        if (this.unicacityAddon.configuration().bombScreenshot().get()) {

            try {
                File file = this.unicacityAddon.services().file().getNewActivityImageFile("großeinsatz");
                ScreenshotBuilder.getBuilder(this.unicacityAddon).file(file).save();
            } catch (IOException ex) {
                this.unicacityAddon.logger().warn(ex.getMessage());
            }

        }
    }

    private void runDelayed(Runnable runnable) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        }, 500);
    }
}