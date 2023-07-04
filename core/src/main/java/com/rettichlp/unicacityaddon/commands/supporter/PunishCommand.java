package com.rettichlp.unicacityaddon.commands.supporter;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.Punishment;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dimiikou
 */
@UCCommand(prefix = "punish", usage = "[Spieler] [Grund]")
public class PunishCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public PunishCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
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

        Punishment punishment = getPunishmentByName(arguments[1]);
        if (punishment == null) {
            p.sendErrorMessage("Dieser Bangrund existiert nicht");
            return true;
        }

        String reason = punishment.getReason();
        int banDuration = punishment.getBanDuration();

        if (punishment.getCheckpoints() > 0)
            p.sendServerMessage("/checkpoints " + arguments[0] + " " + punishment.getCheckpoints() + " " + reason);
        if (punishment.getBanDuration() > 0)
            p.sendServerMessage("/tban " + arguments[0] + " 0 0 " + banDuration + " " + reason);
        if (punishment.getBanDuration() == -1)
            p.sendServerMessage("/ban " + arguments[0] + " " + reason);
        if (punishment.isLoyalityPointReset())
            p.sendServerMessage("/resettreuebonus " + arguments[0]);
        if (punishment.getWeaponLock() > 0)
            p.sendServerMessage("/waffensperre " + arguments[0] + " 0 0 " + punishment.getWeaponLock() * 24 * 60 + " " + reason);
        if (punishment.getFactionLock() > 0)
            p.sendServerMessage("/fraksperre " + arguments[0] + " " + punishment.getFactionLock() + " " + reason);
        if (punishment.getAdLock() > 0)
            p.sendServerMessage("/adsperre " + arguments[0] + " " + punishment.getAdLock() + " " + reason);
        if (punishment.isKick())
            p.sendServerMessage("/kick " + arguments[0] + " " + reason);
        if (punishment.getWarnAmmount() > 0)
            for (int i = 0; i < punishment.getWarnAmmount(); i++) {
                p.sendServerMessage("/warn " + arguments[0] + " " + reason);
            }

        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(2, Arrays.stream(Punishment.values()).map(Punishment::getTabReason).sorted().collect(Collectors.toList()))
                .build();
    }

    private Punishment getPunishmentByName(String s) {
        for (Punishment punishment : Punishment.values()) {
            if (punishment.getTabReason().equals(s))
                return punishment;
        }
        return null;
    }
}