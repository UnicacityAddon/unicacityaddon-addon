package com.rettichlp.unicacityaddon.commands.faction.polizei;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.models.WantedReasonEntry;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.utils.ForgeUtils;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/faction/police/ASUCommand.java">UCUtils by paulzhng</a>
 */
@UCCommand
public class ASUCommand implements IClientCommand {

    private final Timer timer = new Timer();

    @Override
    @Nonnull
    public String getName() {
        return "asu";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/asu [Spieler...] [Grund] (-v/-b/-fsa/-wsa)";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();
        if (args.length < 2) {
            p.sendSyntaxMessage(getUsage(sender));
            return;
        }

        Set<Flag> flags = getFlags(args);
        int reasonIndex = args.length - flags.size() - 1;
        List<String> players = Arrays.asList(args).subList(0, reasonIndex);

        WantedReasonEntry wantedReasonEntry = WantedReasonEntry.getWantedReasonEntryByReason(args[reasonIndex]);
        if (wantedReasonEntry == null) {
            p.sendErrorMessage("Der Wantedgrund wurde nicht gefunden!");
            return;
        }

        String wantedReasonString = wantedReasonEntry.getReason().replace("-", " ");
        int wantedReasonAmount = wantedReasonEntry.getPoints();

        for (Flag flag : flags) {
            wantedReasonString = flag.modifyWantedReasonString(wantedReasonString);
            wantedReasonAmount = flag.modifyWantedReasonAmount(wantedReasonAmount);
        }

        giveWanteds(p, wantedReasonString, wantedReasonAmount, players);
    }

    private void giveWanteds(UPlayer issuer, String reason, int amount, List<String> players) {
        int maxAmount = Math.min(amount, 69);

        if (players.size() > 14) {
            timer.scheduleAtFixedRate(new TimerTask() {
                private int i;

                @Override
                public void run() {
                    if (i >= players.size()) {
                        cancel();
                        return;
                    }

                    String player = players.get(i++);

                    issuer.sendChatMessage("/su " + maxAmount + " " + player + " " + reason);
                }
            }, 0, TimeUnit.SECONDS.toMillis(1));
        } else {
            for (String player : players) {
                issuer.sendChatMessage("/su " + amount + " " + player + " " + reason);
            }
        }
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return TabCompletionBuilder.getBuilder(args)
                .addToAllFromIndex(2, Syncer.getWantedReasonEntryList().stream().map(WantedReasonEntry::getReason).sorted().collect(Collectors.toList()))
                .addToAllFromIndex(2, ForgeUtils.getOnlinePlayers())
                .addToAllFromIndex(3, Arrays.stream(Flag.values()).map(Flag::getFlagArgument).sorted().collect(Collectors.toList()))
                .build();
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
    }

    private Set<Flag> getFlags(String[] args) {
        Set<Flag> flags = new HashSet<>();

        for (String arg : args) {
            Flag flag = Flag.getFlag(arg);

            if (flag != null)
                flags.add(flag);
        }

        return flags;
    }

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public int compareTo(@Nonnull ICommand o) {
        return 0;
    }

    private enum Flag {
        TRIED("-v", "Versuchte/r/s ", "", "x/2"),
        SUBSIDY("-b", "Beihilfe bei der/dem ", "", "x-10"),
        DRIVERS_LICENSE_WITHDRAWAL("-fsa", "", " + Führerscheinabnahme", "x"),
        WEAPONS_LICENSE_WITHDRAWAL("-wsa", "", " + Waffenscheinabnahme", "x");

        private final String flagArgument;
        private final String prependReason;
        private final String postponeReason;
        private final String wantedModification;

        Flag(String flagArgument, String prependReason, String postponeReason, String wantedModification) {
            this.flagArgument = flagArgument;
            this.prependReason = prependReason;
            this.postponeReason = postponeReason;
            this.wantedModification = wantedModification;
        }

        static Flag getFlag(String string) {
            for (Flag flag : Flag.values()) {
                if (flag.flagArgument.equalsIgnoreCase(string)) return flag;
            }

            return null;
        }

        private String getFlagArgument() {
            return flagArgument;
        }

        public String modifyWantedReasonString(String wantedReasonString) {
            return prependReason + wantedReasonString + postponeReason;
        }

        public int modifyWantedReasonAmount(int wantedReasonAmount) {
            return (int) new MathUtils(wantedModification.replace("x", String.valueOf(wantedReasonAmount))).evaluate();
        }
    }
}