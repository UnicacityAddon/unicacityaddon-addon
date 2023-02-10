package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.api.request.APIConverter;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.ModifyBlacklistType;
import com.rettichlp.unicacityaddon.base.models.BlacklistReason;
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
    public static BlacklistReason addReason;
    public static long executedTime = -1;

    private static final String usage = "/modifyblacklist [Spieler] [Grund/-v]";

    public ModifyBlacklistCommand() {
        super("modifyblacklist", "mbl");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = UnicacityAddon.PLAYER;

        if (arguments.length != 2) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        String reason = arguments[1];

        BlacklistReason blacklistReason = BlacklistReason.getBlacklistReasonEntryByReason(reason);
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
        return TabCompletionBuilder.getBuilder(arguments)
                .addAtIndex(2, APIConverter.getBlacklistReasonEntryList().stream().map(BlacklistReason::getReason).sorted().collect(Collectors.toList()))
                .addAtIndex(2, "-v")
                .build();
    }
}