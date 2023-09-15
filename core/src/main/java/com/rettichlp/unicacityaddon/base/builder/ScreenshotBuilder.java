package com.rettichlp.unicacityaddon.base.builder;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.services.NotificationService;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author RettichLP
 */
public class ScreenshotBuilder {

    private ScreenshotBuilder() {
    }

    public static Builder getBuilder(UnicacityAddon unicacityAddon) {
        return new Builder(unicacityAddon);
    }

    public static class Builder {

        private final UnicacityAddon unicacityAddon;
        private File file;
        private CompletableFuture<File> savedFileFuture;
        private String url;

        public Builder(UnicacityAddon unicacityAddon) {
            this.unicacityAddon = unicacityAddon;
        }

        public Builder file(File file) {
            this.file = file;
            this.savedFileFuture = new CompletableFuture<>();
            this.url = null;
            return this;
        }

        public void save() {
            File screenshotFile = this.unicacityAddon.screenshotController().createScreenshot(this.file);
            this.savedFileFuture.complete(screenshotFile);

            if (screenshotFile != null) {
                this.unicacityAddon.notificationService().sendScreenshotNotification("Der Screenshot wurde gespeichert.", NotificationService.SendState.SUCCESS);
            } else {
                this.unicacityAddon.notificationService().sendScreenshotNotification("Screenshot konnte nicht erstellt werden.", NotificationService.SendState.FAILURE);
            }
        }

        public String upload() {
            if (!this.savedFileFuture.isDone()) {
                save();
            }

            new Thread(() -> {
                try {
                    File savedFile = this.savedFileFuture.get();

                    Optional.ofNullable(savedFile)
                            .map(sf -> this.unicacityAddon.utilService().imageUpload().uploadToLink(sf))
                            .ifPresentOrElse(url -> {
                                this.url = url;
                                this.unicacityAddon.player().copyToClipboard(url);
                                this.unicacityAddon.notificationService().sendScreenshotNotification("Der Link wurde in die Zwischenablage kopiert.", NotificationService.SendState.SUCCESS);
                            }, () -> this.unicacityAddon.notificationService().sendScreenshotNotification("Der Screenshot konnte nicht hochgeladen werden.", NotificationService.SendState.FAILURE));

                } catch (InterruptedException | ExecutionException e) {
                    this.unicacityAddon.logger().error(e.getMessage());
                }
            }).start();

            return this.url;
        }
    }
}