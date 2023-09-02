package com.rettichlp.unicacityaddon.listener.faction.rettungsdienst;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.AutoFirstAidCommand;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import net.labymod.api.event.client.render.ScreenRenderEvent;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author Dimiikou
 */
@UCEvent
public class FirstAidListener {

    private final UnicacityAddon unicacityAddon;

    public FirstAidListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();

        if (PatternHandler.FIRST_AID_RECEIVE_PATTERN.matcher(msg).find()) {
            this.unicacityAddon.fileService().data().setFirstAidDate(System.currentTimeMillis());
            return;
        }

        if (PatternHandler.FIRST_AID_LICENCE_PATTERN.matcher(msg).find()) {
            long expirationTime = this.unicacityAddon.fileService().data().getFirstAidDate() + TimeUnit.DAYS.toMillis(14); // Erhaltsdatum + 14 Tage = Auslaufdatum
            long timeLeft = expirationTime - System.currentTimeMillis(); // Auslaufdatum - aktuelle Datum = Dauer des Scheins
            e.setMessage(Message.getBuilder()
                    .space().space()
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of("Erste-Hilfe-Schein").color(ColorCode.BLUE).advance()
                    .of(":").color(ColorCode.DARK_GRAY).advance().space()
                    .of("Vorhanden").color(ColorCode.AQUA)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of(this.unicacityAddon.utilService().text().parseTimerWithTimeUnit(timeLeft)).color(ColorCode.RED).advance().createComponent())
                            .advance()
                    .createComponent());
            return;
        }

        if (PatternHandler.FIRST_AID_ISSUE_PATTERN.matcher(msg).find()) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    FirstAidListener.this.unicacityAddon.player().sendInfoMessage("Du kannst wieder Erste Hilfe leisten.");
                }
            }, TimeUnit.SECONDS.toMillis(90));
        }
    }

    @Subscribe
    public void onScreenRender(ScreenRenderEvent e) {
        String containerLegacyName = this.unicacityAddon.guiController().getContainerLegacyName();
        boolean isFirstAidContainer = containerLegacyName != null && containerLegacyName.equals(ColorCode.GRAY.getCode() + "Erste-Hilfe annehmen?");

        if (isFirstAidContainer && AutoFirstAidCommand.autoAcceptFirstAid) {
            this.unicacityAddon.utilService().debug("Found first aid container");
            this.unicacityAddon.guiController().inventoryClick(1);
            this.unicacityAddon.logger().info("Accepted first aid automatically");
        }
    }
}
