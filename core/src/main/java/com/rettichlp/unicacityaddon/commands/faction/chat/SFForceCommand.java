package com.rettichlp.unicacityaddon.commands.faction.chat;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.listener.faction.FDSFChatListener;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "sfforce", usage = "[Nachricht]")
public class SFForceCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public SFForceCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length == 0) {
            sendUsage();
            return true;
        }

        String message = this.unicacityAddon.utilService().text().makeStringByArgs(arguments, " ");
        FDSFChatListener.forceSendNextMessage = true;
        p.sendServerMessage("/sf " + message);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}