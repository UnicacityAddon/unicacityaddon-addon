package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.BlacklistReason;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;

import java.util.List;
import java.util.stream.Collectors;

import static com.rettichlp.unicacityaddon.base.io.api.API.find;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "asetbl", usage = "[Spieler...] [Grund]")
public class ASetBlacklistCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public ASetBlacklistCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();
        if (arguments.length < 2) {
            sendUsage();
            return true;
        }

        BlacklistReason blacklistReason = find(this.unicacityAddon.api().getBlacklistReasonList(), b -> b.getReason().equalsIgnoreCase(arguments[arguments.length - 1]));
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
                .addToAllFromIndex(2, this.unicacityAddon.services().util().getOnlinePlayers())
                .addToAllFromIndex(2, this.unicacityAddon.api().getBlacklistReasonList().stream().map(BlacklistReason::getReason).sorted().collect(Collectors.toList()))
                .build();
    }
}