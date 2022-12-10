package com.rettichlp.unicacityaddon.commands;

import com.google.inject.Inject;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class DiscordCommand extends Command {

    private static final String usage = "/discord";

    @Inject
    private DiscordCommand() {
        super("discord");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        if (!UnicacityAddon.isUnicacity()) return true;
        UPlayer p = AbstractionLayer.getPlayer();

        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("Discord Invite").color(ColorCode.DARK_AQUA).bold().advance()
                .createComponent());
        p.sendMessage(Message.getBuilder()
                .of("»").color(ColorCode.GRAY).advance().space()
                .of("discord.gg/A9u5eY7CbS").color(ColorCode.AQUA).bold()
                .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("*Klick*").color(ColorCode.DARK_AQUA).bold().advance().createComponent())
                .clickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/A9u5eY7CbS").advance()
                .createComponent());

        p.sendEmptyMessage();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}