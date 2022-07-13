package com.rettichlp.UnicacityAddon.base.text;

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

    private MessagePart(Builder builder) {
        this.message = builder.message;
        this.colorCode = builder.colorCode;
        this.formattingCodes = builder.formattingCodes;
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
    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String message;
        private ColorCode colorCode;
        private final List<FormattingCode> formattingCodes = new ArrayList<>();
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

        public MessagePart build() {
            return new MessagePart(this);
        }

        public Message.Builder advance() {
            return messageBuilder.messageParts(build());
        }
    }
}