package com.rettichlp.unicacityaddon.base.builder;

import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.ImageUploadUtils;
import com.rettichlp.unicacityaddon.controller.ScreenshotController;
import net.labymod.api.Laby;
import net.labymod.api.notification.Notification;

import java.io.File;

/**
 * @author RettichLP
 */
public class ScreenshotBuilder {

    private ScreenshotBuilder() {
    }

    public static Builder getBuilder(ScreenshotController screenshotController) {
        return new Builder(screenshotController);
    }

    public static class Builder {

        private File file;
        private final ScreenshotController screenshotController;

        public Builder(ScreenshotController screenshotController) {
            this.screenshotController = screenshotController;
        }

        public Builder file(File file) {
            this.file = file;
            return this;
        }

        public void save() {
//            this.file = this.screenshotController.createScreenshot(file);
            if (this.file != null) {
                Laby.references().notificationController().push(Notification.builder()
                        .title(Message.getBuilder().of("Screenshot erstellt!").color(ColorCode.GREEN).bold().advance().createComponent())
                        .text(Message.getBuilder().of("Wird gespeichert...").color(ColorCode.WHITE).advance().createComponent())
                        .build());
            } else {
                Laby.references().notificationController().push(Notification.builder()
                        .title(Message.getBuilder().of("Fehler!").color(ColorCode.RED).bold().advance().createComponent())
                        .text(Message.getBuilder().of("Screenshot konnte nicht erstellt werden.").color(ColorCode.WHITE).advance().createComponent())
                        .build());
            }
        }

        public void upload() {
            save();
//            new Thread(() -> uploadScreenshot(this.file)).start();
        }
// TODO: 31.03.2023
//        private static void uploadScreenshot(File screenshotFile) {
//            if (screenshotFile != null) {
//                String link = ImageUploadUtils.uploadToLink(screenshotFile);
//                this.unicacityAddon.player.copyToClipboard(link);
//                Laby.references().notificationController().push(Notification.builder()
//                        .title(Message.getBuilder().of("Screenshot hochgeladen!").color(ColorCode.GREEN).bold().advance().createComponent())
//                        .text(Message.getBuilder().of("Link in Zwischenablage kopiert.").color(ColorCode.WHITE).advance().createComponent())
//                        .build());
//            }
//        }
    }
}