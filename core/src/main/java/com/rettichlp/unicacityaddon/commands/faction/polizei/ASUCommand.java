package com.rettichlp.unicacityaddon.commands.faction.polizei;

import com.google.inject.Inject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.WantedFlag;
import com.rettichlp.unicacityaddon.base.models.WantedReasonEntry;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.utils.ForgeUtils;
import net.labymod.api.client.chat.command.Command;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/faction/police/ASUCommand.java">UCUtils by paulzhng</a>
 */
@UCCommand
public class ASUCommand extends Command {

    private static final String usage = "/asu [Spieler...] [Grund] (-v/-b/-fsa/-wsa)";
    private final Timer timer = new Timer();

    @Inject
    private ASUCommand() {
        super("asu");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();
        if (arguments.length < 2) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        Set<WantedFlag> wantedFlags = getFlags(arguments);
        int reasonIndex = arguments.length - wantedFlags.size() - 1;
        List<String> players = Arrays.asList(arguments).subList(0, reasonIndex);

        WantedReasonEntry wantedReasonEntry = WantedReasonEntry.getWantedReasonEntryByReason(arguments[reasonIndex]);
        if (wantedReasonEntry == null) {
            p.sendErrorMessage("Der Wantedgrund wurde nicht gefunden!");
            return true;
        }

        String wantedReasonString = wantedReasonEntry.getReason().replace("-", " ");
        int wantedReasonAmount = wantedReasonEntry.getPoints();

        for (WantedFlag wantedFlag : wantedFlags) {
            wantedReasonString = wantedFlag.modifyWantedReasonString(wantedReasonString);
            wantedReasonAmount = wantedFlag.modifyWantedReasonAmount(wantedReasonAmount);
        }

        giveWanteds(p, wantedReasonString, wantedReasonAmount, players);
        return true;
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
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments)
                .addToAllFromIndex(2, Syncer.getWantedReasonEntryList().stream().map(WantedReasonEntry::getReason).sorted().collect(Collectors.toList()))
                .addToAllFromIndex(2, ForgeUtils.getOnlinePlayers())
                .addToAllFromIndex(3, Arrays.stream(WantedFlag.values()).map(WantedFlag::getFlagArgument).sorted().collect(Collectors.toList()))
                .build();
    }


    private Set<WantedFlag> getFlags(String[] args) {
        Set<WantedFlag> wantedFlags = new HashSet<>();

        for (String arg : args) {
            WantedFlag wantedFlag = WantedFlag.getFlag(arg);

            if (wantedFlag != null)
                wantedFlags.add(wantedFlag);
        }

        return wantedFlags;
    }
}