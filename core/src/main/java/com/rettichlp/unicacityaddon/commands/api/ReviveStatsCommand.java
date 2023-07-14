package com.rettichlp.unicacityaddon.commands.api;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.Revive;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.services.utils.MathUtils;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.FormattingCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.Laby;
import net.labymod.api.client.component.event.HoverEvent;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "revivestats", aliases = {"rstats"}, usage = "(all|old|Spieler|Rang)")
public class ReviveStatsCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public ReviveStatsCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        Laby.labyAPI().minecraft().chatExecutor().displayActionBar(ColorCode.AQUA.getCode() + FormattingCode.BOLD.getCode() + "Revivestats werden geladen...");

        new Thread(() -> {
            if (arguments.length == 0) {
                sendRevivestatsPlayer(p, p.getName());
            } else if (MathUtils.isInteger(arguments[0])) {
                sendRevivestatsRank(p, Integer.parseInt(arguments[0]));
            } else if (arguments[0].equalsIgnoreCase("all")) {
                sendRevivestatsAll(p);
            } else if (arguments[0].equalsIgnoreCase("old")) {
                sendRevivestatsOld(p);
            } else {
                sendRevivestatsPlayer(p, arguments[0]);
            }
        }).start();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "all", "old", "0", "1", "2", "3", "4", "5", "6")
                .addAtIndex(1, this.unicacityAddon.api().getPlayerFactionMap().entrySet().stream()
                        .filter(stringFactionEntry -> stringFactionEntry.getValue().equals(Faction.RETTUNGSDIENST))
                        .map(Map.Entry::getKey)
                        .sorted()
                        .collect(Collectors.toList()))
                .build();
    }

    private void sendRevivestatsPlayer(AddonPlayer p, String name) {
        Revive revive = this.unicacityAddon.api().sendRevivePlayerRequest(name);

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

    private void sendRevivestatsRank(AddonPlayer p, int rank) {
        List<Revive> reviveList = this.unicacityAddon.api().sendReviveRankRequest(rank);

        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("Revivestats").color(ColorCode.DARK_AQUA).bold().advance().space()
                .of("(").color(ColorCode.GRAY).bold().advance()
                .of(String.valueOf(rank)).color(ColorCode.AQUA).bold().advance()
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

    private void sendRevivestatsAll(AddonPlayer p) {
        List<Revive> reviveList = this.unicacityAddon.api().sendReviveRequest();
        if (!reviveList.isEmpty()) {
            p.sendEmptyMessage();
            p.sendMessage(Message.getBuilder()
                    .of("Revivestats:").color(ColorCode.DARK_AQUA).bold().advance()
                    .createComponent());
            sendList(p, reviveList, false);
            p.sendEmptyMessage();
        }
    }

    private void sendRevivestatsOld(AddonPlayer p) {
        List<Revive> reviveList = this.unicacityAddon.api().sendReviveRequest();
        if (!reviveList.isEmpty()) {
            p.sendEmptyMessage();
            p.sendMessage(Message.getBuilder()
                    .of("Revivestats:").color(ColorCode.DARK_AQUA).bold().advance()
                    .createComponent());
            sendList(p, reviveList, true);
            p.sendEmptyMessage();
        }
    }

    private void sendList(AddonPlayer p, List<Revive> reviveList, boolean onlyOld) {
        Message.Builder overAllMessage = Message.getBuilder();

        int allReviveAmount = 0;
        for (int i = 6; i >= 0; i--) {
            ColorCode colorCode = i % 2 == 0 ? ColorCode.WHITE : ColorCode.YELLOW;

            int finalI = i;
            List<Revive> rankReviveList = reviveList.stream()
                    .filter(revive -> this.unicacityAddon.api().getPlayerRankMap().getOrDefault(revive.getMinecraftName(), -1).equals(finalI))
                    .toList();

            int rankReviveAmount = onlyOld
                    ? rankReviveList.stream()
                            .map(Revive::getLastWeekReviveAmount)
                            .reduce(0, Integer::sum)
                    : rankReviveList.stream()
                            .map(Revive::getCurrentWeekReviveAmount)
                            .reduce(0, Integer::sum);
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