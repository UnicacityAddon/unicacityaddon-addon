package com.rettichlp.unicacityaddon.commands.api;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.api.request.APIConverter;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.models.Revive;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.component.event.HoverEvent;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand
public class ReviveStatsCommand extends Command {

    private static final String usage = "/revivestats (Spieler|Rank)";

    public ReviveStatsCommand() {
        super("revivestats", "rstats");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = UnicacityAddon.PLAYER;

        new Thread(() -> {
            if (arguments.length > 0 && MathUtils.isInteger(arguments[0])) {
                List<Revive> reviveList = APIConverter.getReviveRankList(Integer.parseInt(arguments[0]));
                p.sendEmptyMessage();
                p.sendMessage(Message.getBuilder()
                        .of("Revivestats (Rang " + arguments[0] + "):").color(ColorCode.DARK_AQUA).bold().advance()
                        .createComponent());
                sendList(reviveList);
                p.sendEmptyMessage();
            } else if (arguments.length > 0) {
                Revive revive = APIConverter.getRevivePlayer(arguments[0]);

                if (revive != null) {
                    int currentWeekReviveAmount = revive.getCurrentWeekReviveAmount();
                    int lastWeekReviveAmount = revive.getLastWeekReviveAmount();

                    int difference = currentWeekReviveAmount - lastWeekReviveAmount;
                    String differenceString = ColorCode.GREEN.getCode() + (difference > 0 ? "+" : "") + difference;

                    p.sendEmptyMessage();
                    p.sendMessage(Message.getBuilder()
                            .of("Revivestats von").color(ColorCode.DARK_AQUA).advance().space()
                            .of(arguments[0]).color(ColorCode.AQUA).advance()
                            .of(":").color(ColorCode.DARK_AQUA).advance().space()
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
            } else {
                List<Revive> reviveList = APIConverter.getReviveList();
                p.sendEmptyMessage();
                p.sendMessage(Message.getBuilder()
                        .of("Revivestats:").color(ColorCode.DARK_AQUA).bold().advance()
                        .createComponent());
                sendList(reviveList);
                p.sendEmptyMessage();
            }
        }).start();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments)
                .addAtIndex(1, APIConverter.PLAYERFACTIONMAP.entrySet().stream()
                        .filter(stringFactionEntry -> stringFactionEntry.getValue().equals(Faction.RETTUNGSDIENST))
                        .map(Map.Entry::getKey)
                        .sorted()
                        .collect(Collectors.toList()))
                .addAtIndex(1, "0", "1", "2", "3", "4", "5", "6")
                .build();
    }

    private void sendList(List<Revive> reviveList) {
        reviveList.stream()
                .sorted(Comparator.comparing(Revive::getCurrentWeekReviveAmount))
                .forEach(revive -> {
                    int currentWeekReviveAmount = revive.getCurrentWeekReviveAmount();
                    int lastWeekReviveAmount = revive.getLastWeekReviveAmount();

                    int difference = currentWeekReviveAmount - lastWeekReviveAmount;
                    String differenceString = ColorCode.GREEN.getCode() + (difference > 0 ? "+" : "") + difference;

                    UnicacityAddon.PLAYER.sendMessage(Message.getBuilder()
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
    }
}