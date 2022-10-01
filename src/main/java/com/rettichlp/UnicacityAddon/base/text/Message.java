package com.rettichlp.UnicacityAddon.base.text;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/base/text/Message.java">UCUtils by paulzhng</a>
 */
public class Message {

    private final List<MessagePart> messageParts;

    private Message(Builder builder) {
        this.messageParts = builder.messageParts;
    }

    public List<MessagePart> getMessageParts() {
        return messageParts;
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
                if (part.getColorCode() != null) partStringBuilder.append(part.getColorCode().getCode());
                if (!part.getFormattingCodes().isEmpty()) part.getFormattingCodes().forEach(formattingCode -> partStringBuilder.append(formattingCode.getCode()));
                partStringBuilder.append(part.getMessage());
                stringBuilder.append(partStringBuilder);
            });

            return stringBuilder.toString();
        }

        public ITextComponent createComponent() {
            Message message = build();
            ITextComponent overall = new TextComponentString("");

            message.getMessageParts().forEach(part -> {
                ITextComponent componentPart = new TextComponentString(part.getMessage());
                Style style = componentPart.getStyle();
                style.setBold(part.getFormattingCodes().contains(FormattingCode.BOLD));
                style.setItalic(part.getFormattingCodes().contains(FormattingCode.ITALIC));
                style.setObfuscated(part.getFormattingCodes().contains(FormattingCode.OBFUSCATED));
                style.setStrikethrough(part.getFormattingCodes().contains(FormattingCode.STRIKETHROUGH));
                style.setUnderlined(part.getFormattingCodes().contains(FormattingCode.UNDERLINE));
                style.setClickEvent(part.getClickEvent());
                style.setHoverEvent(part.getHoverEvent());
                if (part.getColorCode() != null) style.setColor(TextFormatting.valueOf(String.valueOf(part.getColorCode())));
                overall.getSiblings().add(componentPart);
            });

            return overall;
        }

        public void sendTo(EntityPlayerSP player) {
            player.sendMessage(new TextComponentString(create()));
        }
    }
}