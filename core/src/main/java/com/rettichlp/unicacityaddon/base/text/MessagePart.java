package com.rettichlp.unicacityaddon.base.text;

import lombok.Getter;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.gui.icon.Icon;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/base/text/MessagePart.java">UCUtils by paulzhng</a>
 */
@Getter
public class MessagePart {

    private final List<FormattingCode> formattingCodes;
    private final String message;
    private final ColorCode colorCode;
    private final ClickEvent clickEvent;
    private final HoverEvent<?> hoverEvent;
    private final Icon icon;

    private MessagePart(Builder builder) {
        this.formattingCodes = builder.formattingCodes;
        this.message = builder.message;
        this.colorCode = builder.colorCode;
        this.clickEvent = builder.clickEvent;
        this.hoverEvent = builder.hoverEvent;
        this.icon = builder.icon;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {

        private final List<FormattingCode> formattingCodes = new ArrayList<>();
        private String message;
        private ColorCode colorCode;
        private ClickEvent clickEvent;
        private HoverEvent<?> hoverEvent;
        private Icon icon;
        private Message.Builder messageBuilder;

        public Builder messageBuilder(Message.Builder messageBuilder) {
            this.messageBuilder = messageBuilder;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder color(ColorCode colorCode) {
            this.colorCode = colorCode;
            return this;
        }

        public Builder obfuscated() {
            this.formattingCodes.add(FormattingCode.OBFUSCATED);
            return this;
        }

        public Builder bold() {
            this.formattingCodes.add(FormattingCode.BOLD);
            return this;
        }

        public Builder strikethrough() {
            this.formattingCodes.add(FormattingCode.STRIKETHROUGH);
            return this;
        }

        public Builder underline() {
            this.formattingCodes.add(FormattingCode.UNDERLINED);
            return this;
        }

        public Builder italic() {
            this.formattingCodes.add(FormattingCode.ITALIC);
            return this;
        }

        public Builder clickEvent(ClickEvent.Action action, String value) {
            this.clickEvent = ComponentService.clickEvent(action, value);
            return this;
        }

        public <V> Builder hoverEvent(HoverEvent.Action<V> action, V value) {
            this.hoverEvent = ComponentService.hoverEvent(action, value);
            return this;
        }

        public Builder icon(Icon icon) {
            this.icon = icon;
            return this;
        }

        public MessagePart build() {
            return new MessagePart(this);
        }

        public Message.Builder advance() {
            return messageBuilder.messageParts(build());
        }
    }
}