package com.rettichlp.unicacityaddon.base.services;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.notification.Notification;

/**
 * @author RettichLP
 */
public class NotificationService {

    private final UnicacityAddon unicacityAddon;

    public NotificationService(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    public void sendScreenshotNotification(String text, SendState sendState) {
        Component component = sendState.getPrefix().copy().append(Message.getBuilder().of(text).bold().advance().createComponent());
        Notification notification = Notification.builder()
                .title(Message.getBuilder().of("Screenshot").color(ColorCode.DARK_AQUA).bold().advance().createComponent())
                .text(component)
                .icon(this.unicacityAddon.utilService().icon())
                .build();

        Laby.labyAPI().notificationController().push(notification);
    }

    public Notification sendUnicacityAddonNotification(String text, SendState sendState) {
        Component component = sendState.getPrefix().copy().append(Message.getBuilder().of(text).bold().advance().createComponent());
        Notification notification = Notification.builder()
                .title(Message.getBuilder()
                        .of("UnicacityAddon").color(ColorCode.DARK_AQUA).bold().advance()
                        .createComponent())
                .text(component)
                .icon(this.unicacityAddon.utilService().icon())
                .build();

        Laby.labyAPI().notificationController().push(notification);
        return notification;
    }

    @Getter
    @AllArgsConstructor
    public enum SendState {

        PROCESSING(Message.getBuilder().of("●").color(ColorCode.BLUE).advance().space().createComponent()),
        SUCCESS(Message.getBuilder().of("●").color(ColorCode.GREEN).advance().space().createComponent()),
        FAILURE(Message.getBuilder().of("●").color(ColorCode.RED).advance().space().createComponent());

        private final Component prefix;
    }
}