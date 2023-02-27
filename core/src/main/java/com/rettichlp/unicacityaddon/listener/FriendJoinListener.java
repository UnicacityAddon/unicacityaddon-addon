package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class FriendJoinListener {

    private final UnicacityAddon unicacityAddon;

    public FriendJoinListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        ChatMessage chatMessage = e.chatMessage();
        String formattedMsg = chatMessage.getFormattedText();

        Matcher accountFriendJoinMatcher = PatternHandler.ACCOUNT_FRIEND_JOIN_PATTERN.matcher(chatMessage.getPlainText());
        if (accountFriendJoinMatcher.find()) {
            String name = accountFriendJoinMatcher.group("name");
            e.setMessage(Message.getBuilder()
                    .add(formattedMsg)
                    .space()
                    .of("[☎]").color(ColorCode.DARK_GREEN)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of(name + " anrufen").color(ColorCode.DARK_GREEN).advance().createComponent())
                            .clickEvent(ClickEvent.Action.RUN_COMMAND, "/acall " + name)
                            .advance()
                    .space()
                    .of("[✉]").color(ColorCode.GOLD)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("SMS an " + name + " senden").color(ColorCode.GOLD).advance().createComponent())
                            .clickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/asms " + name + " ")
                            .advance()
                    .space()
                    .of("[✕]").color(ColorCode.RED)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of(name + " aus der Freundesliste entfernen").color(ColorCode.RED).advance().createComponent())
                            .clickEvent(ClickEvent.Action.RUN_COMMAND, "/fl delete " + name)
                            .advance()
                    .createComponent());
            return;
        }

        Matcher accountFriendLeaveMatcher = PatternHandler.ACCOUNT_FRIEND_LEAVE_PATTERN.matcher(chatMessage.getPlainText());
        if (accountFriendLeaveMatcher.find()) {
            String name = accountFriendLeaveMatcher.group("name");
            e.setMessage(Message.getBuilder()
                    .add(formattedMsg)
                    .space()
                    .of("[✕]").color(ColorCode.RED)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of(name + " aus der Freundesliste entfernen").color(ColorCode.RED).advance().createComponent())
                            .clickEvent(ClickEvent.Action.RUN_COMMAND, "/fl delete " + name)
                            .advance()
                    .createComponent());
        }
    }
}