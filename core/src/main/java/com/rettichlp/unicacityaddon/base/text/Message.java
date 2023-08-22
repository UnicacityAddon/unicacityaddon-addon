package com.rettichlp.unicacityaddon.base.text;

import lombok.Getter;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/base/text/Message.java">UCUtils by paulzhng</a>
 */
public class Message {

    @Getter
    private final List<MessagePart> messageParts;

    private Message(Builder builder) {
        this.messageParts = builder.messageParts;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {

        private final List<MessagePart> messageParts = new ArrayList<>();

        public MessagePart.Builder of(String message) {
            return MessagePart.getBuilder().message(message).messageBuilder(this);
        }

        public Builder prefix() {
            return add(Message.getBuilder()
                    .of("U").color(ColorCode.BLUE).advance()
                    .of("C").color(ColorCode.RED).advance()
                    .of("A").color(ColorCode.BLUE).advance().space()
                    .of("●").color(ColorCode.DARK_GRAY).advance().space()
                    .create());
        }

        public Builder info() {
            return add(Message.getBuilder()
                    .of("  Info:").color(ColorCode.AQUA).advance()
                    .create());
        }

        public Builder error() {
            return add(Message.getBuilder()
                    .of("[").color(ColorCode.DARK_GRAY).advance()
                    .of("Fehler").color(ColorCode.RED).advance()
                    .of("]").color(ColorCode.DARK_GRAY).advance()
                    .create());
        }

        public Builder add(String text) {
            return MessagePart.getBuilder().messageBuilder(this).message(text).advance();
        }

        public Builder space() {
            return add(" ");
        }

        public Builder newline() {
            return add("\n");
        }

        public Builder messageParts(MessagePart... messageParts) {
            Collections.addAll(this.messageParts, messageParts);
            return this;
        }

        public Message build() {
            return new Message(this);
        }

        public String create() {
            Message message = build();
            StringBuilder stringBuilder = new StringBuilder();

            message.getMessageParts().forEach(part -> {
                StringBuilder partStringBuilder = new StringBuilder();
                partStringBuilder.append(FormattingCode.RESET.getCode());
                if (part.getColorCode() != null) {
                    partStringBuilder.append(part.getColorCode().getCode());
                }
                if (!part.getFormattingCodes().isEmpty()) {
                    part.getFormattingCodes().forEach(
                            formattingCode -> partStringBuilder.append(formattingCode.getCode()));
                }
                partStringBuilder.append(part.getMessage());
                stringBuilder.append(partStringBuilder);
            });

            return stringBuilder.toString();
        }

        public Component createComponent() {
            Message message = build();
            Component component = Component.text("");

            message.getMessageParts().forEach(messagePart -> {
                Style.Builder builder = Style.builder();

                if (messagePart.getColorCode() != null) {
                    builder.color(messagePart.getColorCode().getTextColor());
                }

                if (messagePart.getFormattingCodes().contains(FormattingCode.BOLD)) {
                    builder.decorate(TextDecoration.BOLD);
                }

                if (messagePart.getFormattingCodes().contains(FormattingCode.ITALIC)) {
                    builder.decorate(TextDecoration.ITALIC);
                }

                if (messagePart.getFormattingCodes().contains(FormattingCode.OBFUSCATED)) {
                    builder.decorate(TextDecoration.OBFUSCATED);
                }

                if (messagePart.getFormattingCodes().contains(FormattingCode.STRIKETHROUGH)) {
                    builder.decorate(TextDecoration.STRIKETHROUGH);
                }

                if (messagePart.getFormattingCodes().contains(FormattingCode.UNDERLINED)) {
                    builder.decorate(TextDecoration.UNDERLINED);
                }

                builder.hoverEvent(messagePart.getHoverEvent());
                builder.clickEvent(messagePart.getClickEvent());

                component.append(Component.text(messagePart.getMessage()).style(builder.build()));
            });

            return component;
        }
    }
}