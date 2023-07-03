package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand(prefix = "discord")
public class DiscordCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public DiscordCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        if (!this.unicacityAddon.utilService().isUnicacity())
            return true;
        AddonPlayer p = this.unicacityAddon.player();

        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("Discord Invite").color(ColorCode.DARK_AQUA).bold().advance()
                .createComponent());
        p.sendMessage(Message.getBuilder()
                .of("»").color(ColorCode.GRAY).advance().space()
                .of("discord.gg/A9u5eY7CbS").color(ColorCode.AQUA).bold()
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("*Klick*").color(ColorCode.DARK_AQUA).bold().advance().createComponent())
                        .clickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/A9u5eY7CbS")
                        .advance()
                .createComponent());

        p.sendEmptyMessage();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}