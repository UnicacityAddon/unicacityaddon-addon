package com.rettichlp.unicacityaddon.listener.team;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class NewbieChatListener {

    private final UnicacityAddon unicacityAddon;

    public NewbieChatListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        AddonPlayer p = this.unicacityAddon.player();
        String msg = e.chatMessage().getPlainText();

        System.out.println("trigger event");

        Matcher newbieChatMatcher = PatternHandler.NEWBIE_CHAT.matcher(msg);
        if (newbieChatMatcher.find()) {
            String message = newbieChatMatcher.group("message").toLowerCase();

            this.unicacityAddon.api().getAutoNCList().stream()
                    .filter(autoNC -> autoNC.getWords().stream().map(String::toLowerCase).allMatch(message::contains))
                    .findFirst()
                    .ifPresent(autoNC -> p.sendMessage(Message.getBuilder()
                            .of("[").color(ColorCode.DARK_GRAY).advance()
                            .of("Auto-NC").color(ColorCode.AQUA).advance()
                            .of("]").color(ColorCode.DARK_GRAY).advance().space()
                            .of(autoNC.getAnswer()).color(ColorCode.GRAY).advance().space()
                            .of("[").color(ColorCode.DARK_GRAY).advance()
                            .of("Senden").color(ColorCode.GREEN)
                                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Nachricht senden").color(ColorCode.GREEN).advance().createComponent())
                                    .clickEvent(ClickEvent.Action.RUN_COMMAND, "/nc " + autoNC.getAnswer())
                                    .advance()
                            .of("]").color(ColorCode.DARK_GRAY).advance()
                            .createComponent()));
        }
    }
}