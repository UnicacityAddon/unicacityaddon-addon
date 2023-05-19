package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.models.BlacklistReason;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand
public class ASetBlacklistCommand extends UnicacityCommand {

    private static final String usage = "/asetbl [Spieler...] [Grund]";

    private final UnicacityAddon unicacityAddon;

    public ASetBlacklistCommand(UnicacityAddon unicacityAddon) {
        super(unicacityAddon, "asetbl", true);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();
        if (arguments.length < 2) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        BlacklistReason blacklistReason = BlacklistReason.getBlacklistReasonEntryByReason(arguments[arguments.length - 1], this.unicacityAddon);
        if (blacklistReason == null) {
            p.sendErrorMessage("Der Blacklistgrund wurde nicht gefunden!");
            return true;
        }

        for (int i = 0; i < arguments.length - 1; i++) {
            p.sendServerMessage("/bl set " + arguments[i] + " " + blacklistReason.getKills() + " " + blacklistReason.getPrice() + " " + blacklistReason.getReason().replace("-", " "));
        }
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addToAllFromIndex(2, this.unicacityAddon.utils().getOnlinePlayers())
                .addToAllFromIndex(2, this.unicacityAddon.api().getBlacklistReasonList().stream().map(BlacklistReason::getReason).sorted().collect(Collectors.toList()))
                .build();
    }
}