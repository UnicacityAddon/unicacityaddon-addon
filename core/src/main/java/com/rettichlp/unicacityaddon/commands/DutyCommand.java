package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class DutyCommand extends Command {

    private static final String usage = "/checkduty [Spieler]";

    private final UnicacityAddon unicacityAddon;

    public DutyCommand(UnicacityAddon unicacityAddon) {
        super("checkduty");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length == 0) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        boolean isDuty = this.unicacityAddon.factionService().checkPlayerDuty(arguments[0]);

        p.sendMessage(Message.getBuilder()
                .of("Der Spieler").color(ColorCode.GRAY).advance().space()
                .of(arguments[0]).color(ColorCode.AQUA).advance().space()
                .of("befindet sich für das UnicacityAddon").color(ColorCode.GRAY).advance().space()
                .of((isDuty ? "" : "nicht ") + "im Dienst").color(isDuty ? ColorCode.GREEN : ColorCode.RED).advance()
                .of(".").color(ColorCode.GRAY).advance()
                .createComponent());

        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}