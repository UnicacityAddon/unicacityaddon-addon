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
@UCCommand(prefix = "move", onlyOnUnicacity = false, usage = "[Spieler] [Spieler]")
public class MoveCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public MoveCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
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
        String targetName = arguments[1];

        User user = this.unicacityAddon.teamSpeakAPI().controller().getUserByDescription(playerName);
        User targetUser = this.unicacityAddon.teamSpeakAPI().controller().getUserByDescription(targetName);

        Channel targetChannel = null;
        if (user != null && targetUser != null) {
            targetChannel = this.unicacityAddon.teamSpeakAPI().controller().getChannelByUser(targetUser);
        }

        if (user != null && targetChannel != null) {
            boolean success = this.unicacityAddon.teamSpeakAPI().controller().move(user.getId(), targetChannel.getId());
            if (!success) {
                p.sendErrorMessage("Der Move ist fehlgeschlagen!");
            }
        } else {
            p.sendErrorMessage("Der Spieler " + playerName + " oder " + targetName + " wurde nicht auf dem TeamSpeak gefunden.");
        }

        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}