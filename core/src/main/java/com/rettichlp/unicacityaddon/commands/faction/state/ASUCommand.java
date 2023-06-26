package com.rettichlp.unicacityaddon.commands.faction.state;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.WantedReason;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.WantedFlag;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.rettichlp.unicacityaddon.base.io.api.API.find;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/faction/police/ASUCommand.java">UCUtils by paulzhng</a>
 */
@UCCommand(prefix = "asu", usage = "[Spieler...] [Grund] (-v|-b|-fsa|-wsa)")
public class ASUCommand extends UnicacityCommand {

    private final Timer timer = new Timer();

    private final UnicacityAddon unicacityAddon;

    public ASUCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
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

        Set<WantedFlag> wantedFlags = getFlags(arguments);
        int reasonIndex = arguments.length - wantedFlags.size() - 1;
        List<String> players = Arrays.asList(arguments).subList(0, reasonIndex);

        WantedReason wantedReason = find(this.unicacityAddon.api().getWantedReasonList(), w -> w.getReason().equalsIgnoreCase(arguments[reasonIndex]));
        if (wantedReason == null) {
            p.sendErrorMessage("Der Wantedgrund wurde nicht gefunden!");
            return true;
        }

        String wantedReasonString = wantedReason.getReason().replace("-", " ");
        int wantedReasonAmount = wantedReason.getPoints();

        for (WantedFlag wantedFlag : wantedFlags) {
            wantedReasonString = wantedFlag.modifyWantedReasonString(wantedReasonString);
            wantedReasonAmount = wantedFlag.modifyWantedReasonAmount(wantedReasonAmount);
        }

        giveWanteds(p, wantedReasonString, wantedReasonAmount, players);
        return true;
    }

    private void giveWanteds(AddonPlayer issuer, String reason, int amount, List<String> players) {
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

                    issuer.sendServerMessage("/su " + maxAmount + " " + player + " " + reason);
                }
            }, 0, TimeUnit.SECONDS.toMillis(1));
        } else {
            for (String player : players) {
                issuer.sendServerMessage("/su " + amount + " " + player + " " + reason);
            }
        }
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addToAllFromIndex(2, this.unicacityAddon.api().getWantedReasonList().stream().map(WantedReason::getReason).sorted().collect(Collectors.toList()))
                .addToAllFromIndex(2, this.unicacityAddon.services().util().getOnlinePlayers())
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