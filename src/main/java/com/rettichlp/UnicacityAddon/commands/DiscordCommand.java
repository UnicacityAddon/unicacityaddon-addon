package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class DiscordCommand extends CommandBase {

    @Override
    @Nonnull
    public String getName() {
        return "discord";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/discord";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return Arrays.asList("socials");
    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        if (!UnicacityAddon.isUnicacity()) return;
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
    }
}