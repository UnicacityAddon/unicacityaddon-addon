package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.ModifyBlacklistType;
import com.rettichlp.unicacityaddon.base.models.BlacklistReasonEntry;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import net.labymod.api.client.chat.command.Command;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dimiikou
 */
@UCCommand
public class ModifyBlacklistCommand extends Command {

    public static String target;
    public static ModifyBlacklistType type;
    public static BlacklistReasonEntry addReason;
    public static long executedTime = -1;

    private static final String usage = "/modifyblacklist [Spieler] [Grund/-v]";

    public ModifyBlacklistCommand() {
        super("modifyblacklist", "mbl");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (arguments.length != 2) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        String reason = arguments[1];

        BlacklistReasonEntry blacklistReasonEntry = BlacklistReasonEntry.getBlacklistReasonEntryByReason(reason);
        if (!reason.equalsIgnoreCase("-v") && blacklistReasonEntry == null) {
            p.sendErrorMessage("Blacklistgrund wurde nicht gefunden!");
            return true;
        }

        target = arguments[0];
        if (blacklistReasonEntry != null) {
            addReason = blacklistReasonEntry;
            type = ModifyBlacklistType.MODIFY_REASON;
        } else {
            type = ModifyBlacklistType.OUTLAW;
        }

        executedTime = System.currentTimeMillis();

        p.sendChatMessage("/bl");
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments)
                .addAtIndex(1, Syncer.getBlacklistReasonEntryList().stream().map(BlacklistReasonEntry::getReason).sorted().collect(Collectors.toList()))
                .addAtIndex(1, "-v")
                .build();
    }
}