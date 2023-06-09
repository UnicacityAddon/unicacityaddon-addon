package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.api.AddonGroup;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand(prefix = "dyavol", onlyOnUnicacity = false)
public class DyavolCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public DyavolCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("D'yavol:").color(ColorCode.DARK_RED).bold().advance()
                .createComponent());

        AddonGroup.DYAVOL.getMemberList().forEach(s -> {
            boolean online = this.unicacityAddon.services().utilService().getOnlinePlayers().contains(s);
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