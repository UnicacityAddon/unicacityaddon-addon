package com.rettichlp.unicacityaddon.base.text;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/base/text/MessagePart.java">UCUtils by paulzhng</a>
 */
public class MessagePart {

    private final String message;
    private final ColorCode colorCode;
    private final List<FormattingCode> formattingCodes;
    private final ClickEvent clickEvent;
    private final HoverEvent hoverEvent;

    private MessagePart(Builder builder) {
        this.message = builder.message;
        this.colorCode = builder.colorCode;
        this.formattingCodes = builder.formattingCodes;
        this.clickEvent = builder.clickEvent;
        this.hoverEvent = builder.hoverEvent;
    }

    public String getMessage() {
        return message;
    }

    public ColorCode getColorCode() {
        return colorCode;
    }

    public List<FormattingCode> getFormattingCodes() {
        return formattingCodes;
    }

    public ClickEvent getClickEvent() {
        return clickEvent;
    }

    public HoverEvent getHoverEvent() {
        return hoverEvent;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {

        private String message;
        private ColorCode colorCode;
        private final List<FormattingCode> formattingCodes = new ArrayList<>();
        private ClickEvent clickEvent;
        private HoverEvent hoverEvent;
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
            this.formattingCodes.add(FormattingCode.UNDERLINE);
            return this;
        }

        public Builder italic() {
            this.formattingCodes.add(FormattingCode.ITALIC);
            return this;
        }

        public Builder clickEvent(ClickEvent.Action action, String message) {
            this.clickEvent = new ClickEvent(action, message);
            return this;
        }

        public Builder hoverEvent(HoverEvent.Action action, ITextComponent message) {
            this.hoverEvent = new HoverEvent(action, message);
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