package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.models.BlacklistReasonEntry;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.utils.ForgeUtils;
import net.labymod.api.client.chat.command.Command;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand
public class ASetBlacklistCommand extends Command {

    private static final String usage = "/asetbl [Spieler...] [Grund]";

    public ASetBlacklistCommand() {
        super("asetbl");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();
        if (arguments.length < 2) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        BlacklistReasonEntry blacklistReasonEntry = BlacklistReasonEntry.getBlacklistReasonEntryByReason(arguments[arguments.length - 1]);
        if (blacklistReasonEntry == null) {
            p.sendErrorMessage("Der Blacklistgrund wurde nicht gefunden!");
            return true;
        }

        for (int i = 0; i < arguments.length - 1; i++) {
            p.sendChatMessage("/bl set " + arguments[i] + " " + blacklistReasonEntry.getKills() + " " + blacklistReasonEntry.getPrice() + " " + blacklistReasonEntry.getReason().replace("-", " "));
        }
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments)
                .addToAllFromIndex(2, ForgeUtils.getOnlinePlayers())
                .addToAllFromIndex(2, Syncer.getBlacklistReasonEntryList().stream().map(BlacklistReasonEntry::getReason).sorted().collect(Collectors.toList()))
                .build();
    }
}