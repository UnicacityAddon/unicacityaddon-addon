package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.ForgeUtils;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand
public class MemberInfoAllCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "memberinfoall";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/memberinfoall (Fraktion)";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return Collections.singletonList("miall");
    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        String factionString = args.length < 1 ? p.getFaction().getFactionKey() : args[0];
        Faction faction = Faction.getFactionByFactionKey(factionString);
        if (faction != null) {

            Map<String, Integer> factionMemberList = Syncer.PLAYERFACTIONMAP.entrySet().stream()
                    .filter(stringFactionEntry -> stringFactionEntry.getValue().equals(faction))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toMap(s -> s, Syncer.PLAYERRANKMAP::get));

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
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return TabCompletionBuilder.getBuilder(args)
                .addAtIndex(1, Arrays.stream(Faction.values()).map(Faction::getFactionKey).sorted().collect(Collectors.toList()))
                .build();
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
    }

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public int compareTo(@Nonnull ICommand o) {
        return 0;
    }
}