package com.rettichlp.unicacityaddon.listener.chat;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String legacyChatMessage = this.unicacityAddon.utilService().text().legacy(e.chatMessage().component());

        Matcher urlForumMatcher = PatternHandler.URL_FORUM_PATTERN.matcher(legacyChatMessage);
        Matcher urlTwitchMatcher = PatternHandler.URL_TWITCH_PATTERN.matcher(legacyChatMessage);
        Matcher urlImgurMatcher = PatternHandler.URL_IMGUR_PATTERN.matcher(legacyChatMessage);

        String matchedLink;
        Icon icon;
        String label;
        ColorCode colorCode;

        if (urlForumMatcher.find()) {
            String title = urlForumMatcher.group("title");

            matchedLink = urlForumMatcher.group();
            label = !title.isEmpty() ? this.unicacityAddon.utilService().text().createTitle(title) : "Unicacity Forum";
            icon = Icon.sprite16(ResourceLocation.create("unicacityaddon", "themes/vanilla/textures/sprite/chat.png"), 0, 0);
            colorCode = ColorCode.RED;
        } else if (urlTwitchMatcher.find()) {
            String channel = urlTwitchMatcher.group("channel");

            matchedLink = urlTwitchMatcher.group();
            label = !channel.isEmpty() ? this.unicacityAddon.utilService().text().createTitle(channel) : "Twitch";
            icon = Icon.sprite16(ResourceLocation.create("labymod", "themes/vanilla/textures/settings/hud/hud.png"), 4, 3);
            colorCode = ColorCode.LIGHT_PURPLE;
        } else if (urlImgurMatcher.find()) {
            matchedLink = urlImgurMatcher.group();
            label = "Imgur";
            icon = Icon.sprite16(ResourceLocation.create("unicacityaddon", "themes/vanilla/textures/sprite/chat.png"), 1, 0);
            colorCode = ColorCode.GREEN;
        } else {
            return;
        }

        String[] splittedlegacyChatMessage = legacyChatMessage.split(Pattern.quote(matchedLink));

        e.setMessage(Message.getBuilder()
                .of(splittedlegacyChatMessage.length > 0 ? splittedlegacyChatMessage[0] : "").advance()
                .of(label + " ↗").color(colorCode).bold()
//                        .icon(icon)
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of(label + " öffnen").color(colorCode).advance().createComponent())
                        .clickEvent(ClickEvent.Action.OPEN_URL, matchedLink)
                        .advance()
                .of(splittedlegacyChatMessage.length > 1 ? splittedlegacyChatMessage[1].trim() : "").advance()
                .createComponent());
    }
}