package com.rettichlp.unicacityaddon.commands.faction.chat;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class SFForceCommand extends Command {

    private static final String usage = "/sfforce [Nachricht]";

    private UnicacityAddon unicacityAddon;

    public SFForceCommand(UnicacityAddon unicacityAddon) {
        super("sfforce");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length == 0) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        String message = TextUtils.makeStringByArgs(arguments, " ");
        p.sendServerMessage("/sf " + message);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}