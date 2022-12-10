package com.rettichlp.unicacityaddon.commands.supporter;

import com.google.inject.Inject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.Punishment;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import net.labymod.api.client.chat.command.Command;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dimiikou
 */
@UCCommand
public class PunishCommand extends Command {

    private static final String usage = "/punish [Spielername] [Grund]";

    @Inject
    private PunishCommand() {
        super("punish");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (arguments.length < 2) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        Punishment punishment = getPunishmentByName(arguments[1]);
        if (punishment == null) {
            p.sendErrorMessage("Dieser Bangrund existiert nicht");
            return true;
        }

        String reason = punishment.getReason();
        int banDuration = punishment.getBanDuration();

        if (punishment.getCheckpoints() > 0)
            p.sendChatMessage("/checkpoints " + arguments[0] + " " + punishment.getCheckpoints() + " " + reason);
        if (punishment.getBanDuration() > 0)
            p.sendChatMessage("/tban " + arguments[0] + " 0 0 " + banDuration + " " + reason);
        if (punishment.getBanDuration() == -1)
            p.sendChatMessage("/ban " + arguments[0] + " " + reason);
        if (punishment.isLoyalityPointReset())
            p.sendChatMessage("/resettreuebonus " + arguments[0]);
        if (punishment.getWeaponLock() > 0)
            p.sendChatMessage("/waffensperre " + arguments[0] + " 0 0 " + punishment.getWeaponLock() * 24 * 60 + " " + reason);
        if (punishment.getFactionLock() > 0)
            p.sendChatMessage("/fraksperre " + arguments[0] + " " + punishment.getFactionLock() + " " + reason);
        if (punishment.getAdLock() > 0)
            p.sendChatMessage("/adsperre " + arguments[0] + " " + punishment.getAdLock() + " " + reason);
        if (punishment.isKick())
            p.sendChatMessage("/kick " + arguments[0] + " " + reason);
        if (punishment.getWarnAmmount() > 0)
            for (int i = 0; i < punishment.getWarnAmmount(); i++) {
                p.sendChatMessage("/warn " + arguments[0] + " " + reason);
            }

        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments)
                .addAtIndex(2, Arrays.stream(Punishment.values()).map(Punishment::getTabReason).sorted().collect(Collectors.toList()))
                .build();
    }

    private String getBanDurationString(int banDuration) {
        int minutes = banDuration;
        int hours = minutes / 60;
        minutes -= hours * 60;
        int days = hours / 60;
        hours -= days * 60;

        return days + " " + hours + " " + minutes;
    }

    private Punishment getPunishmentByName(String s) {
        for (Punishment punishment : Punishment.values()) {
            if (punishment.getTabReason().equals(s))
                return punishment;
        }
        return null;
    }
}