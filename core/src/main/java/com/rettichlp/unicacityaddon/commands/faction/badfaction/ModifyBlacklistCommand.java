package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.BlacklistReason;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.ModifyBlacklistType;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dimiikou
 */
@UCCommand(prefix = "modifyblacklist", aliases = {"mbl"}, usage = "[Spieler] [Grund|-v]")
public class ModifyBlacklistCommand extends UnicacityCommand {

    public static String target;
    public static ModifyBlacklistType type;
    public static BlacklistReason addReason;
    public static long executedTime = -1;

    private final UnicacityAddon unicacityAddon;

    public ModifyBlacklistCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length != 2) {
            sendUsage();
            return true;
        }

        String reason = arguments[1];

        BlacklistReason blacklistReason = BlacklistReason.getBlacklistReasonEntryByReason(reason, this.unicacityAddon);
        if (!reason.equalsIgnoreCase("-v") && blacklistReason == null) {
            p.sendErrorMessage("Blacklistgrund wurde nicht gefunden!");
            return true;
        }

        target = arguments[0];
        if (blacklistReason != null) {
            addReason = blacklistReason;
            type = ModifyBlacklistType.MODIFY_REASON;
        } else {
            type = ModifyBlacklistType.OUTLAW;
        }

        executedTime = System.currentTimeMillis();

        p.sendServerMessage("/bl");
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(2, this.unicacityAddon.api().getBlacklistReasonList().stream().map(BlacklistReason::getReason).sorted().collect(Collectors.toList()))
                .addAtIndex(2, "-v")
                .build();
    }
}