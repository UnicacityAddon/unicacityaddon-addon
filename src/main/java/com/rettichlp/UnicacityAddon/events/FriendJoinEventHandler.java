package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class FriendJoinEventHandler {

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        ITextComponent msg = e.getMessage();

        Matcher accountFriendJoinMatcher = PatternHandler.ACCOUNT_FRIEND_JOIN_PATTERN.matcher(msg.getUnformattedText());
        if (accountFriendJoinMatcher.find()) {
            String name = accountFriendJoinMatcher.group("name");
            e.setMessage(Message.getBuilder()
                    .add(msg.getFormattedText())
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
                    .of("[✖]").color(ColorCode.RED)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of(name + " aus der Freundesliste entfernen").color(ColorCode.RED).advance().createComponent())
                            .clickEvent(ClickEvent.Action.RUN_COMMAND, "/fl delete " + name)
                            .advance()
                    .createComponent());
        }
    }
}