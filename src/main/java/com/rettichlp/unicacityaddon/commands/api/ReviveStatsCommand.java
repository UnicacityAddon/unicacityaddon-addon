package com.rettichlp.unicacityaddon.commands.api;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.models.Revive;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.FormattingCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand
public class ReviveStatsCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "revivestats";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/revivestats (Spieler|Rank)";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return Collections.singletonList("rstats");
    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        UnicacityAddon.MINECRAFT.ingameGUI.setOverlayMessage(ColorCode.AQUA.getCode() + FormattingCode.BOLD.getCode() + "Revivestats werden geladen...", true);

        new Thread(() -> {
            if (args.length == 0) {
                sendRevivestatsPlayer(p, p.getName());
            } else if (MathUtils.isInteger(args[0])) {
                sendRevivestatsRank(p, Integer.parseInt(args[0]));
            } else if (args[0].equalsIgnoreCase("all")) {
                sendRevivestatsAll(p);
            } else if (args[0].equalsIgnoreCase("old")) {
                sendRevivestatsOld(p);
            } else {
                sendRevivestatsPlayer(p, args[0]);
            }
        }).start();
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return TabCompletionBuilder.getBuilder(args)
                .addAtIndex(1, "all", "old", "0", "1", "2", "3", "4", "5", "6")
                .addAtIndex(1, Syncer.PLAYERFACTIONMAP.entrySet().stream()
                        .filter(stringFactionEntry -> stringFactionEntry.getValue().equals(Faction.RETTUNGSDIENST))
                        .map(Map.Entry::getKey)
                        .sorted()
                        .collect(Collectors.toList()))
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

    private void sendRevivestatsPlayer(UPlayer p, String name) {
        Revive revive = Syncer.getRevivePlayer(name);

        if (revive != null) {
            int currentWeekReviveAmount = revive.getCurrentWeekReviveAmount();
            int lastWeekReviveAmount = revive.getLastWeekReviveAmount();

            int difference = currentWeekReviveAmount - lastWeekReviveAmount;
            String differenceString = ColorCode.GREEN.getCode() + (difference > 0 ? "+" : "") + difference;

            p.sendEmptyMessage();
            p.sendMessage(Message.getBuilder()
                    .of("Revivestats von").color(ColorCode.DARK_AQUA).advance().space()
                    .of(name).color(ColorCode.AQUA).advance()
                    .of(":").color(ColorCode.GRAY).advance().space()
                    .of(String.valueOf(currentWeekReviveAmount)).color(ColorCode.AQUA).advance().space()
                    .of("(").color(ColorCode.DARK_GRAY).advance()
                    .of(differenceString).color(difference >= 0 ? ColorCode.GREEN : ColorCode.RED)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder()
                                    .of("Diese Woche:").color(ColorCode.GRAY).advance().space()
                                    .of(String.valueOf(currentWeekReviveAmount)).color(ColorCode.RED).advance().newline()
                                    .of("Letzte Woche:").color(ColorCode.GRAY).advance().space()
                                    .of(String.valueOf(lastWeekReviveAmount)).color(ColorCode.RED).advance()
                                    .createComponent())
                            .advance()
                    .of(")").color(ColorCode.DARK_GRAY).advance()
                    .createComponent());

            p.sendEmptyMessage();
        }
    }

    private void sendRevivestatsRank(UPlayer p, int i) {
        List<Revive> reviveList = Syncer.getReviveRankList(i);

        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("Revivestats").color(ColorCode.DARK_AQUA).bold().advance().space()
                .of("(").color(ColorCode.GRAY).bold().advance()
                .of(String.valueOf(i)).color(ColorCode.AQUA).bold().advance()
                .of(")").color(ColorCode.GRAY).bold().advance()
                .of(":").color(ColorCode.DARK_AQUA).bold().advance()
                .createComponent());

        reviveList.forEach(revive -> {
            int currentWeekReviveAmount = revive.getCurrentWeekReviveAmount();
            int lastWeekReviveAmount = revive.getLastWeekReviveAmount();

            int difference = currentWeekReviveAmount - lastWeekReviveAmount;
            String differenceString = ColorCode.GREEN.getCode() + (difference > 0 ? "+" : "") + difference;

            p.sendMessage(Message.getBuilder()
                    .of("»").color(ColorCode.GRAY).advance().space()
                    .of(revive.getMinecraftName() + ":").color(ColorCode.GRAY).advance().space()
                    .of(String.valueOf(currentWeekReviveAmount)).color(ColorCode.AQUA).advance().space()
                    .of("(").color(ColorCode.DARK_GRAY).advance()
                    .of(differenceString).color(difference >= 0 ? ColorCode.GREEN : ColorCode.RED)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder()
                                    .of("Diese Woche:").color(ColorCode.GRAY).advance().space()
                                    .of(String.valueOf(currentWeekReviveAmount)).color(ColorCode.RED).advance().newline()
                                    .of("Letzte Woche:").color(ColorCode.GRAY).advance().space()
                                    .of(String.valueOf(lastWeekReviveAmount)).color(ColorCode.RED).advance()
                                    .createComponent())
                            .advance()
                    .of(")").color(ColorCode.DARK_GRAY).advance()
                    .createComponent());
        });

        p.sendEmptyMessage();
    }

    private void sendRevivestatsAll(UPlayer p) {
        List<Revive> reviveList = Syncer.getReviveList();
        if (!reviveList.isEmpty()) {
            p.sendEmptyMessage();
            p.sendMessage(Message.getBuilder()
                    .of("Revivestats:").color(ColorCode.DARK_AQUA).bold().advance()
                    .createComponent());
            sendList(p, reviveList, false);
            p.sendEmptyMessage();
        }
    }

    private void sendRevivestatsOld(UPlayer p) {
        List<Revive> reviveList = Syncer.getReviveList();
        if (!reviveList.isEmpty()) {
            p.sendEmptyMessage();
            p.sendMessage(Message.getBuilder()
                    .of("Revivestats:").color(ColorCode.DARK_AQUA).bold().advance()
                    .createComponent());
            sendList(p, reviveList, true);
            p.sendEmptyMessage();
        }
    }

    private void sendList(UPlayer p, List<Revive> reviveList, boolean onlyOld) {
        Message.Builder overAllMessage = Message.getBuilder();

        int allReviveAmount = 0;
        for (int i = 6; i >= 0; i--) {
            ColorCode colorCode = i % 2 == 0 ? ColorCode.WHITE : ColorCode.YELLOW;

            int finalI = i;
            List<Revive> rankReviveList = reviveList.stream()
                    .filter(revive -> Syncer.PLAYERRANKMAP.getOrDefault(revive.getMinecraftName(), -1).equals(finalI))
                    .collect(Collectors.toList());

            int rankReviveAmount = onlyOld ? rankReviveList.stream().map(Revive::getLastWeekReviveAmount).reduce(0, Integer::sum) : rankReviveList.stream().map(Revive::getCurrentWeekReviveAmount).reduce(0, Integer::sum);
            allReviveAmount += rankReviveAmount;
            overAllMessage
                    .of("Rang " + i + ":").color(ColorCode.GRAY).advance().space()
                    .of(String.valueOf(rankReviveAmount)).color(ColorCode.RED).advance().newline();

            rankReviveList.forEach(revive -> {
                int currentWeekReviveAmount = revive.getCurrentWeekReviveAmount();
                int lastWeekReviveAmount = revive.getLastWeekReviveAmount();

                int difference = currentWeekReviveAmount - lastWeekReviveAmount;
                String differenceString = ColorCode.GREEN.getCode() + (difference > 0 ? "+" : "") + difference;

                p.sendMessage(Message.getBuilder()
                        .of("»").color(ColorCode.GRAY).advance().space()
                        .of(revive.getMinecraftName() + ":").color(colorCode).advance().space()
                        .of(String.valueOf(onlyOld ? lastWeekReviveAmount : currentWeekReviveAmount)).color(ColorCode.AQUA).advance().space()
                        .of(onlyOld ? "" : "(").color(ColorCode.DARK_GRAY).advance()
                        .of(onlyOld ? "" : differenceString).color(difference >= 0 ? ColorCode.GREEN : ColorCode.RED)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder()
                                        .of("Diese Woche:").color(ColorCode.GRAY).advance().space()
                                        .of(String.valueOf(currentWeekReviveAmount)).color(ColorCode.RED).advance().newline()
                                        .of("Letzte Woche:").color(ColorCode.GRAY).advance().space()
                                        .of(String.valueOf(lastWeekReviveAmount)).color(ColorCode.RED).advance()
                                        .createComponent())
                                .advance()
                        .of(onlyOld ? "" : ")").color(ColorCode.DARK_GRAY).advance()
                        .createComponent());
            });
        }

        p.sendMessage(Message.getBuilder()
                .of("➡").color(ColorCode.GRAY).advance().space()
                .of("Gesamt:").color(ColorCode.DARK_AQUA)
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, overAllMessage.createComponent())
                        .advance().space()
                .of(String.valueOf(allReviveAmount)).color(ColorCode.AQUA)
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, overAllMessage.createComponent())
                        .advance()
                .createComponent());
    }
}