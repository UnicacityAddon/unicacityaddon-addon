package com.rettichlp.unicacityaddon.commands.money;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class EinzahlenCommand extends Command {

    private UnicacityAddon unicacityAddon;

    public EinzahlenCommand(UnicacityAddon unicacityAddon) {
        super("einzahlen");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (this.unicacityAddon.fileManager().data().getCashBalance() > 0)
            p.sendServerMessage("/bank einzahlen " + this.unicacityAddon.fileManager().data().getCashBalance());
        else
            p.sendErrorMessage("Du hast kein Geld auf der Hand!");
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}