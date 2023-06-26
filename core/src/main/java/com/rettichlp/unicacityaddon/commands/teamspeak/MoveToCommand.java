package com.rettichlp.unicacityaddon.commands.teamspeak;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.models.Channel;
import com.rettichlp.unicacityaddon.base.teamspeak.models.User;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "moveto", onlyOnUnicacity = false, usage = "[Spieler]")
public class MoveToCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public MoveToCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length < 1) {
            sendUsage();
            return true;
        }

        String playerName = arguments[0];

        User user = this.unicacityAddon.teamSpeakAPI().controller().getUserByDescription(playerName);

        Channel channel = null;
        if (user != null) {
            channel = this.unicacityAddon.teamSpeakAPI().controller().getChannelByUser(user);
        }

        if (channel != null) {
            boolean success = this.unicacityAddon.teamSpeakAPI().controller().move(channel.getId());
            if (!success) {
                p.sendErrorMessage("Der Move ist fehlgeschlagen!");
            }
        } else {
            p.sendErrorMessage("Der Spieler " + playerName + " wurde nicht auf dem TeamSpeak gefunden.");
        }

        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}