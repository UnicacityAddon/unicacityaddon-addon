package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.api.request.APIConverter;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.ForgeUtils;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class DyavolCommand extends Command {

    public DyavolCommand() {
        super("dyavol");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = UnicacityAddon.PLAYER;

        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("D'yavol:").color(ColorCode.DARK_RED).bold().advance()
                .createComponent());

        APIConverter.getPlayerGroupEntryList("DYAVOL").forEach(playerGroupEntry -> {
            boolean online = ForgeUtils.getOnlinePlayers().contains(playerGroupEntry.getName());
            p.sendMessage(Message.getBuilder()
                    .of("»").color(ColorCode.GRAY).advance().space()
                    .of(playerGroupEntry.getName()).color(online ? ColorCode.GREEN : ColorCode.RED).advance()
                    .createComponent());
        });

        p.sendEmptyMessage();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}