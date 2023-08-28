package com.rettichlp.unicacityaddon.listener.chat;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class ChatLinkListener {

    private final UnicacityAddon unicacityAddon;

    public ChatLinkListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        Component mainComponent = Component.text("");
        e.chatMessage().component().getChildren().forEach(component -> {
            if (component instanceof TextComponent textComponent) {
                String text = textComponent.getText();

                Matcher urlForumMatcher = PatternHandler.URL_FORUM_PATTERN.matcher(text);
                Matcher urlTwitchMatcher = PatternHandler.URL_TWITCH_PATTERN.matcher(text);
                Matcher urlImgurMatcher = PatternHandler.URL_IMGUR_PATTERN.matcher(text);

                if (urlForumMatcher.find()) {
                    String title = urlForumMatcher.group("title");
                    String forumString = !title.isEmpty() ? createTitle(title) : "Unicacity Forum";

                    Icon woltlabIcon = Icon.sprite16(ResourceLocation.create("unicacityaddon", "themes/vanilla/textures/sprite/chat.png"), 0, 0);
                    mainComponent.append(Message.getBuilder()
                            .of(forumString).color(ColorCode.RED).bold().icon(woltlabIcon)
                                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of(forumString + " öffnen").color(ColorCode.RED).advance().createComponent())
                                    .clickEvent(ClickEvent.Action.OPEN_URL, urlForumMatcher.group())
                                    .advance()
                            .createComponent());
                    this.unicacityAddon.logger().info("Found forum.unicacity.de link, transforming message...");
                } else if (urlTwitchMatcher.find()) {
                    String channel = urlTwitchMatcher.group("channel");
                    String twitchString = !channel.isEmpty() ? createTitle(channel) : "Twitch";

                    Icon twitchIcon = Icon.sprite16(ResourceLocation.create("labymod", "themes/vanilla/textures/settings/hud/hud.png"), 4, 3);
                    mainComponent.append(Message.getBuilder()
                            .of(twitchString).color(ColorCode.LIGHT_PURPLE).bold().icon(twitchIcon)
                                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of(twitchString + " anschauen").color(ColorCode.LIGHT_PURPLE).advance().createComponent())
                                    .clickEvent(ClickEvent.Action.OPEN_URL, urlTwitchMatcher.group())
                                    .advance()
                            .createComponent());
                    this.unicacityAddon.logger().info("Found twitch.tv link, transforming message...");
                } else if (urlImgurMatcher.find()) {
                    Icon imgurIcon = Icon.sprite16(ResourceLocation.create("unicacityaddon", "themes/vanilla/textures/sprite/chat.png"), 1, 0);
                    mainComponent.append(Message.getBuilder()
                            .of("Imgur").color(ColorCode.GREEN).bold().icon(imgurIcon)
                                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Imgur öffnen").color(ColorCode.GREEN).advance().createComponent())
                                    .clickEvent(ClickEvent.Action.OPEN_URL, urlImgurMatcher.group())
                                    .advance()
                            .createComponent());
                    this.unicacityAddon.logger().info("Found imgur.com link, transforming message...");
                } else {
                    mainComponent.append(component);
                }
            }
        });

        e.setMessage(mainComponent);
    }

    private String createTitle(String title) {
        StringBuilder titleBuilder = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : title.replace("-", " ").replace("_", " ").toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }
            titleBuilder.append(c);
        }

        return titleBuilder.toString();
    }
}