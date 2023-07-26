package com.rettichlp.unicacityaddon.commands.supporter;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.Punishment;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.rettichlp.unicacityaddon.base.io.api.API.find;

/**
 * @author Dimiikou
 * @author RettichLP
 */
@UCCommand(prefix = "punish", usage = "[Spieler] [Grund...]")
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

        Collection<Punishment> punishments = Arrays.stream(arguments, 1, arguments.length)
                .map(this::getPunishmentByName)
                .filter(Objects::nonNull)
                .toList();

        StringBuilder reasonStringBuilder = new StringBuilder();
        int checkpoints = 0;
        int banDuration = 0;
        int warnAmmount = 0;
        boolean loyalityPointReset = false;
        boolean kick = false;
        int weaponLock = 0;
        int factionLock = 0;
        int adLock = 0;

        for (Punishment punishment : punishments) {
            reasonStringBuilder.append(punishment.getReason()).append(" + ");
            checkpoints = checkpoints + punishment.getCheckpoints();
            banDuration = banDuration < 0 || punishment.getBanDuration() < 0 ? -1 : banDuration + punishment.getBanDuration();
            warnAmmount = warnAmmount + punishment.getWarnAmmount();
            loyalityPointReset = loyalityPointReset || punishment.isLoyalityPointReset();
            kick = kick || punishment.isKick();
            weaponLock = weaponLock + punishment.getWeaponLock();
            factionLock = factionLock + punishment.getFactionLock();
            adLock = adLock + punishment.getAdLock();
        }

        String reason = reasonStringBuilder.substring(0, reasonStringBuilder.length() - 3);

        if (checkpoints > 0)
            p.sendServerMessage("/checkpoints " + arguments[0] + " " + checkpoints + " " + reason);
        if (banDuration > 0)
            p.sendServerMessage("/tban " + arguments[0] + " 0 0 " + banDuration + " " + reason);
        if (banDuration == -1)
            p.sendServerMessage("/ban " + arguments[0] + " " + reason);
        if (loyalityPointReset)
            p.sendServerMessage("/resettreuebonus " + arguments[0]);
        if (weaponLock > 0)
            p.sendServerMessage("/waffensperre " + arguments[0] + " 0 0 " + weaponLock * 24 * 60 + " " + reason);
        if (factionLock > 0)
            p.sendServerMessage("/fraksperre " + arguments[0] + " " + factionLock + " " + reason);
        if (adLock > 0)
            p.sendServerMessage("/adsperre " + arguments[0] + " " + adLock + " " + reason);
        if (kick)
            p.sendServerMessage("/kick " + arguments[0] + " " + reason);
        if (warnAmmount > 0)
            for (int i = 0; i < warnAmmount; i++) {
                p.sendServerMessage("/warn " + arguments[0] + " " + reason);
            }

        String debugString = "Punished " + arguments[0] + " with " +
                "Checkpoints: " + checkpoints + ", " +
                (banDuration < 0 ? "Permanenter-Ban: true, Temporärer-Ban: false" : "Permanenter-Ban: false, Temporärer-Ban: " + banDuration) + ", " +
                "Treuebonus-Reset: " + loyalityPointReset + ", " +
                "Waffensperre: " + weaponLock + ", " +
                "Fraktionssperre: " + factionLock + ", " +
                "Ad-Sperre: " + adLock + ", " +
                "Kick: " + adLock + ", " +
                "Warns: " + warnAmmount;

        this.unicacityAddon.utilService().debug(debugString);
        this.unicacityAddon.logger().info(debugString);

        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addToAllFromIndex(2, Arrays.stream(Punishment.values()).map(Punishment::getTabReason).sorted().collect(Collectors.toList()))
                .build();
    }

    private Punishment getPunishmentByName(String s) {
        return find(Arrays.asList(Punishment.values()), punishment -> punishment.getTabReason().equals(s));
    }
}