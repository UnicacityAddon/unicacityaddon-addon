package com.rettichlp.UnicacityAddon.commands.faction.polizei;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
import com.rettichlp.UnicacityAddon.base.utils.MathUtils;
import com.rettichlp.UnicacityAddon.events.faction.polizei.WantedEventHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/faction/police/ModifyWantedsCommand.java">UCUtils by paulzhng</a>
 */
public class ModifyWantedsCommand extends CommandBase {

    @Override @Nonnull public String getName() {
        return "modifywanteds";
    }

    @Override @Nonnull public String getUsage(@Nonnull ICommandSender sender) {
        return "/mw [Spieler] [GF/SF/SSF/S/DA5/DA10/DA15/FSA/WSA/WGV]";
    }

    @Override @Nonnull public List<String> getAliases() {
        return Collections.singletonList("mw");
    }

    @Override public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (args.length < 2) return;

        String target = args[0];

        WantedEventHandler.Wanted wanted = WantedEventHandler.WANTED_MAP.get(target);
        if (wanted == null) {
            Message.getBuilder()
                    .error()
                    .space()
                    .of("Du hast /wanteds noch nicht ausgeführt.").color(ColorCode.GRAY).advance()
                    .sendTo(p.getPlayer());
            return;
        }

        String wantedReason = wanted.getReason();
        int wantedAmount = wanted.getAmount();

        for (String arg : args) {
            Type type = Type.getType(arg);

            if (type == null || wantedReason.contains(type.reason)) continue;

            if (arg.equals(Type.VERY_BAD_CONDUCT.flagArgument)) {
                if (wantedReason.contains(Type.BAD_CONDUCT.reason)) {
                    wantedReason = wantedReason.replace(Type.BAD_CONDUCT.reason, "");
                    wantedAmount -= 10;
                }
            }

            if (arg.equals(Type.BAD_CONDUCT.flagArgument)) {
                if (wantedReason.contains(Type.VERY_BAD_CONDUCT.reason)) {
                    wantedReason = wantedReason.replace(Type.VERY_BAD_CONDUCT.reason, "");
                    wantedAmount -= 15;
                }
            }

            wantedReason = type.modifyReason(wantedReason);
            wantedAmount = type.modifyWanteds(wantedAmount);
        }

        if (wanted.getAmount() > wantedAmount) {
            p.sendChatMessage("/clear " + target);
            System.out.println("/clear " + target);
        }

        if (wantedAmount > 69)
            wantedAmount = 69;

        if (wantedAmount == wanted.getAmount() && wantedReason.equals(wanted.getReason())) {
            Message.getBuilder()
                    .error()
                    .space()
                    .of("Der Spieler besitzt bereits diese Modifikatoren.").color(ColorCode.GRAY).advance()
                    .sendTo(p.getPlayer());
            return;
        }

        p.sendChatMessage("/su " + wantedAmount + " " + target + " " + wantedReason);
        System.out.println("/su " + wantedAmount + " " + target + " " + wantedReason);
    }

    @Override @Nonnull public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            List<String> tabCompletions = ForgeUtils.getOnlinePlayers();
            String input = args[args.length - 1].toLowerCase().replace('-', ' ');
            tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
            return tabCompletions;
        } else {
            List<String> tabCompletions = Arrays.stream(Type.values()).map(Type::getFlagArgument).sorted().collect(Collectors.toList());

            String input = args[args.length - 1].toLowerCase().replace('-', ' ');
            tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));

            return tabCompletions;
        }
    }

    private enum Type {
        SURRENDER("s", " + Stellung", "x-5"),
        GOOD_CONDUCT("gf", " + Gute Führung", "x-5"),
        BAD_CONDUCT("sf", " + Schlechte Führung", "x+10"),
        VERY_BAD_CONDUCT("ssf", " + Sehr schlechte Führung", "x+15"),
        DRUG_REMOVAL_5("da5", " + Drogenabnahme", "x-5"),
        DRUG_REMOVAL_10("da10", " + Drogenabnahme", "x-10"),
        DRUG_REMOVAL_15("da15", " + Drogenabnahme", "x-15"),
        DRIVERS_LICENSE_WITHDRAWAL("fsa", " + Führerscheinabnahme", "x"),
        WEAPONS_LICENSE_WITHDRAWAL("wsa", " + Waffenscheinabnahme", "x"),
        RESISTANCE_TO_ENFORCEMENT_OFFICERS("wgv", " + Widerstand gegen Vollstreckungsbeamte", "x+5");

        private final String flagArgument;
        private final String reason;
        private final String wantedModification;

        Type(String flagArgument, String reason, String wantedModification) {
            this.flagArgument = flagArgument;
            this.reason = reason;
            this.wantedModification = wantedModification;
        }

        static Type getType(String string) {
            for (Type type : Type.values()) {
                if (type.flagArgument.equalsIgnoreCase(string)) return type;
            }

            return null;
        }

        public String getFlagArgument() {
            return flagArgument;
        }

        private String modifyReason(String oldReason) {
            return oldReason + this.reason;
        }

        public int modifyWanteds(int wanteds) {
            return (int) new MathUtils(wantedModification.replace("x", String.valueOf(wanteds))).evaluate();
        }
    }
}