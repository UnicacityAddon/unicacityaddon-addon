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

    public UnicacityAddonException(UnicacityAddon unicacityAddon, String message, String notificationMessage) {
        super(unicacityAddon.utilService().messageWithHiddenToken(message));
        this.unicacityAddon = unicacityAddon;
        this.notificationMessage = unicacityAddon.utilService().messageWithHiddenToken(notificationMessage);
    }

    public void sendNotification() {
        Laby.labyAPI().notificationController().push(Notification.builder()
                .title(Message.getBuilder().of("Fehler!").color(ColorCode.RED).bold().advance().createComponent())
                .text(Message.getBuilder().of(this.notificationMessage).advance().createComponent())
                .icon(this.unicacityAddon.utilService().icon())
                .type(Notification.Type.ADVANCEMENT)
                .build());
    }
}