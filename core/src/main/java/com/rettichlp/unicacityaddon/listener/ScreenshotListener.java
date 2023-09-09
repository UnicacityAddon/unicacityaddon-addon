package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.builder.ScreenshotBuilder;
import com.rettichlp.unicacityaddon.base.events.BombRemovedEvent;
import com.rettichlp.unicacityaddon.base.events.ReinforcementAcceptedEvent;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyEvent;

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
    public void onKey(KeyEvent e) {
        Key key = e.key();

        if (key.equals(this.unicacityAddon.configuration().hotkey().screenshot().get()) && e.state().equals(KeyEvent.State.PRESS)) {
            new Thread(() -> {
                try {
                    File file = this.unicacityAddon.fileService().getNewImageFile();
                    ScreenshotBuilder.getBuilder(unicacityAddon).file(file).upload();
                } catch (IOException ex) {
                    this.unicacityAddon.logger().error(ex.getMessage());
                }
            }).start();
        }
    }

    @Subscribe
    public void onReinforcementAccepted(ReinforcementAcceptedEvent e) {
        // activity screenshot
        if (this.unicacityAddon.configuration().reinforcement().screen().get()) {

            // wait until on-the-way message was sent (important for valid activity)
            runDelayed(() -> {
                try {
                    File file = this.unicacityAddon.fileService().getNewActivityImageFile("reinforcement");
                    ScreenshotBuilder.getBuilder(this.unicacityAddon).file(file).save();
                } catch (IOException ex) {
                    this.unicacityAddon.logger().error(ex.getMessage());
                }
            });

        }
    }

    @Subscribe
    public void onBombRemoved(BombRemovedEvent e) {
        // activity screenshot
        if (this.unicacityAddon.configuration().bombScreenshot().get()) {

            // wait until bomb-removed message was sent (important for valid activity)
            runDelayed(() -> {
                try {
                    File file = this.unicacityAddon.fileService().getNewActivityImageFile("großeinsatz");
                    ScreenshotBuilder.getBuilder(this.unicacityAddon).file(file).save();
                } catch (IOException ex) {
                    this.unicacityAddon.logger().error(ex.getMessage());
                }
            });

        }
    }

    private void runDelayed(Runnable runnable) {
        new Thread(() -> {
            try {
                Thread.sleep(500);
                runnable.run();
            } catch (InterruptedException e) {
                this.unicacityAddon.logger().warn("Delayed execution of activity screenshot failed");
                this.unicacityAddon.logger().error(e.getMessage());
            }
        }).start();
    }
}