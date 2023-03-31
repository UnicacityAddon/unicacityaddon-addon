package com.rettichlp.unicacityaddon.commands.faction.state;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.enums.faction.ModifyWantedType;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.utils.ForgeUtils;
import com.rettichlp.unicacityaddon.listener.faction.state.WantedListener;
import net.labymod.api.client.chat.command.Command;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/faction/police/ModifyWantedsCommand.java">UCUtils by paulzhng</a>
 */
@UCCommand
public class ModifyWantedsCommand extends Command {

    private static final String usage = "/mw [Spieler] [GF/SF/SSF/S/DA5/DA10/DA15/FSA/WSA/WGV]";

    private UnicacityAddon unicacityAddon;

    public ModifyWantedsCommand(UnicacityAddon unicacityAddon) {
        super("modifywanteds", "mw");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length < 2)
            return true;

        String target = arguments[0];

        WantedListener.Wanted wanted = WantedListener.WANTED_MAP.get(target);
        if (wanted == null) {
            p.sendErrorMessage("Du hast /wanteds noch nicht ausgeführt!");
            return true;
        }

        String wantedReason = wanted.getReason();
        int wantedAmount = wanted.getAmount();

        for (String argument : arguments) {
            ModifyWantedType type = ModifyWantedType.getModifyWantedType(argument);

            if (type == null || wantedReason.contains(type.getReason()))
                continue;

            if (argument.equals(ModifyWantedType.VERY_BAD_CONDUCT.getFlagArgument())) {
                if (wantedReason.contains(ModifyWantedType.BAD_CONDUCT.getReason())) {
                    wantedReason = wantedReason.replace(ModifyWantedType.BAD_CONDUCT.getReason(), "");
                    wantedAmount -= 10;
                }
            }

            if (argument.equals(ModifyWantedType.BAD_CONDUCT.getFlagArgument())) {
                if (wantedReason.contains(ModifyWantedType.VERY_BAD_CONDUCT.getReason())) {
                    wantedReason = wantedReason.replace(ModifyWantedType.VERY_BAD_CONDUCT.getReason(), "");
                    wantedAmount -= 15;
                }
            }

            wantedReason = type.modifyReason(wantedReason);
            wantedAmount = type.modifyWanteds(wantedAmount);
        }

        if (wanted.getAmount() > wantedAmount) {
            p.sendServerMessage("/clear " + target);
        }

        if (wantedAmount > 69)
            wantedAmount = 69;

        if (wantedAmount == wanted.getAmount() && wantedReason.equals(wanted.getReason())) {
            p.sendErrorMessage("Der Spieler besitzt bereits diese Modifikatoren.");
            return true;
        }

        p.sendServerMessage("/su " + wantedAmount + " " + target + " " + wantedReason);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        if (arguments.length == 1) {
            List<String> tabCompletions = ForgeUtils.getOnlinePlayers(this.unicacityAddon);
            String input = arguments[arguments.length - 1].toLowerCase();
            tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
            return tabCompletions;
        } else {
            List<String> tabCompletions = Arrays.stream(ModifyWantedType.values()).map(ModifyWantedType::getFlagArgument).sorted().collect(Collectors.toList());

            // TODO: 30.09.2022
            String input = arguments[arguments.length - 1].toLowerCase().replace('-', ' ');
            tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));

            return tabCompletions;
        }
    }
}