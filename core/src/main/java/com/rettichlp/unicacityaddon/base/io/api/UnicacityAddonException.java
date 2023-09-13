package com.rettichlp.unicacityaddon.base.io.api;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.Laby;
import net.labymod.api.notification.Notification;

/**
 * @author RettichLP
 */
public class UnicacityAddonException extends Exception {

    private final String notificationMessage;

    private final UnicacityAddon unicacityAddon;

    public UnicacityAddonException(String message) {
        super(message);
        this.unicacityAddon = null;
        this.notificationMessage = "";
    }

    public UnicacityAddonException(UnicacityAddon unicacityAddon, String message, String notificationMessage) {
        super(unicacityAddon.utilService().messageWithHiddenToken(message));
        this.unicacityAddon = unicacityAddon;
        this.notificationMessage = unicacityAddon.utilService().messageWithHiddenToken(notificationMessage);
    }

    public void sendNotification() {
        if (this.unicacityAddon != null && !this.notificationMessage.isBlank()) {
            Laby.labyAPI().notificationController().push(Notification.builder()
                    .title(Message.getBuilder()
                            .of("UnicacityAddon").color(ColorCode.DARK_AQUA).bold().advance().space()
                            .of("API").color(ColorCode.AQUA).advance()
                            .createComponent())
                    .text(Message.getBuilder()
                            .of("●").color(ColorCode.RED).advance().space()
                            .of(this.notificationMessage).advance()
                            .createComponent())
                    .icon(this.unicacityAddon.utilService().icon())
                    .type(Notification.Type.ADVANCEMENT)
                    .build());
        }
    }
}