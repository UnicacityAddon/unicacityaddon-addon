package com.rettichlp.unicacityaddon.base.builder;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.Laby;
import net.labymod.api.notification.Notification;

import java.io.File;

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

        public Builder(UnicacityAddon unicacityAddon) {
            this.unicacityAddon = unicacityAddon;
        }

        public Builder file(File file) {
            this.file = file;
            return this;
        }

        public void save() {
            this.file = this.unicacityAddon.screenshotController().createScreenshot(file);

            Notification CREATION_SUCCESS = Notification.builder()
                    .title(Message.getBuilder().of("Screenshot").color(ColorCode.AQUA).bold().advance().createComponent())
                    .text(Message.getBuilder().of("Der Screenshot wurde gespeichert.").color(ColorCode.WHITE).advance().createComponent())
                    .icon(this.unicacityAddon.utilService().icon())
                    .build();

            Notification CREATION_FAILURE = Notification.builder()
                    .title(Message.getBuilder().of("Fehler!").color(ColorCode.RED).bold().advance().createComponent())
                    .text(Message.getBuilder().of("Screenshot konnte nicht erstellt werden.").color(ColorCode.WHITE).advance().createComponent())
                    .icon(this.unicacityAddon.utilService().icon())
                    .build();

            Laby.references().notificationController().push(this.file != null ? CREATION_SUCCESS : CREATION_FAILURE);
        }

        public String upload() {
            save();

            String url = null;
            if (this.file != null) {
                url = this.unicacityAddon.utilService().imageUpload().uploadToLink(this.file);
            }

            if (url != null) {
                this.unicacityAddon.player().copyToClipboard(url);
                Laby.references().notificationController().push(Notification.builder()
                        .title(Message.getBuilder().of("Hochgeladen!").color(ColorCode.AQUA).bold().advance().createComponent())
                        .text(Message.getBuilder().of("Der Link wurde in die Zwischenablage kopiert.").color(ColorCode.WHITE).advance().createComponent())
                        .icon(this.unicacityAddon.utilService().icon())
                        .build());
            } else {
                Laby.references().notificationController().push(Notification.builder()
                        .title(Message.getBuilder().of("Fehler!").color(ColorCode.RED).bold().advance().createComponent())
                        .text(Message.getBuilder().of("Screenshot konnte nicht hochgeladen werden.").color(ColorCode.WHITE).advance().createComponent())
                        .icon(this.unicacityAddon.utilService().icon())
                        .build());
            }

            return url;
        }
    }
}