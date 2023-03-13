package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.api.request.APIConverter;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.ForgeUtils;
import net.labymod.api.client.chat.command.Command;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand
public class MemberInfoAllCommand extends Command {

    public MemberInfoAllCommand() {
        super("memberinfoall", "miall");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = UnicacityAddon.PLAYER;

        String factionString = arguments.length < 1 ? p.getFaction().getFactionKey() : arguments[0];
        Faction faction = Faction.getFactionByFactionKey(factionString);
        if (faction != null) {

            Map<String, Integer> factionMemberList = APIConverter.PLAYERFACTIONMAP.entrySet().stream()
                    .filter(stringFactionEntry -> stringFactionEntry.getValue().equals(faction))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toMap(s -> s, APIConverter.PLAYERRANKMAP::get));

            p.sendMessage(Message.getBuilder().space().space()
                    .of("===").color(ColorCode.DARK_GRAY).advance().space()
                    .of("Fraktionsmitglieder").color(ColorCode.DARK_AQUA).advance().space()
                    .of("[").color(ColorCode.DARK_GRAY).advance()
                    .of(factionString).color(ColorCode.DARK_AQUA).advance()
                    .of("]").color(ColorCode.DARK_GRAY).advance().space()
                    .of("(").color(ColorCode.DARK_GRAY).advance()
                    .of(String.valueOf(factionMemberList.size())).color(ColorCode.AQUA).advance()
                    .of(")").color(ColorCode.DARK_GRAY).advance().space()
                    .of("===").color(ColorCode.DARK_GRAY).advance()
                    .createComponent());

            factionMemberList.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEach(stringIntegerEntry -> {
                        String playername = stringIntegerEntry.getKey();
                        Integer rank = stringIntegerEntry.getValue();

                        p.sendMessage(Message.getBuilder().space()
                                .of("» Rang:").color(ColorCode.GRAY).advance().space()
                                .of(rank != null ? String.valueOf(rank) : "X").color(ColorCode.AQUA).advance().space()
                                .of("|").color(ColorCode.DARK_GRAY).advance().space()
                                .of(playername).color(ForgeUtils.getOnlinePlayers().contains(playername) ? ColorCode.GREEN : ColorCode.RED).advance()
                                .createComponent());
                    });
        } else {
            p.sendErrorMessage("Fraktion nicht gefunden.");
        }
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments)
                .addAtIndex(1, Arrays.stream(Faction.values()).map(Faction::getFactionKey).sorted().collect(Collectors.toList()))
                .build();
    }
}