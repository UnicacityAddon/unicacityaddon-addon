package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.api.AddonGroup;
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

    private UnicacityAddon unicacityAddon;

    public DyavolCommand(UnicacityAddon unicacityAddon) {
        super("dyavol");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("D'yavol:").color(ColorCode.DARK_RED).bold().advance()
                .createComponent());

        AddonGroup.DYAVOL.getMemberList().forEach(s -> {
            boolean online = ForgeUtils.getOnlinePlayers(this.unicacityAddon).contains(s); // TODO: 31.03.2023 rework forgeutils
            p.sendMessage(Message.getBuilder()
                    .of("»").color(ColorCode.GRAY).advance().space()
                    .of(s).color(online ? ColorCode.GREEN : ColorCode.RED).advance()
                    .of(s).color(online ? ColorCode.GREEN : ColorCode.RED).advance()
                    .createComponent());
        });

        p.sendEmptyMessage();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}