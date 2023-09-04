package com.rettichlp.unicacityaddon.commands.api;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.api.AddonGroup;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "addongroup", onlyOnUnicacity = false, usage = "[list|add|remove] [Gruppe] [Spieler]")
public class AddonGroupCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public AddonGroupCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        new Thread(() -> {
            if (arguments.length == 2 && arguments[0].equalsIgnoreCase("list") && Arrays.stream(AddonGroup.values()).anyMatch(addonGroup -> addonGroup.name().equals(arguments[1]))) {
                p.sendEmptyMessage();
                p.sendMessage(Message.getBuilder()
                        .of("Addon Gruppe:").color(ColorCode.DARK_AQUA).bold().advance().space()
                        .of(arguments[1]).color(ColorCode.DARK_AQUA).advance()
                        .createComponent());

                AddonGroup.valueOf(arguments[1]).getMemberList().forEach(s -> p.sendMessage(Message.getBuilder()
                        .of("»").color(ColorCode.GRAY).advance().space()
                        .of(s).color(ColorCode.AQUA).advance()
                        .createComponent()));

                p.sendEmptyMessage();

            } else if (arguments.length == 3 && arguments[0].equalsIgnoreCase("add")) {
                this.unicacityAddon.api().sendPlayerAddRequest(arguments[2], arguments[1]);
            } else if (arguments.length == 3 && arguments[0].equalsIgnoreCase("remove")) {
                this.unicacityAddon.api().sendPlayerRemoveRequest(arguments[2], arguments[1]);
            } else {
                sendUsage();
            }
        }).start();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "list", "add", "remove")
                .addAtIndex(2, Arrays.stream(AddonGroup.values()).map(Enum::name).collect(Collectors.toList()))
                .build();
    }
}