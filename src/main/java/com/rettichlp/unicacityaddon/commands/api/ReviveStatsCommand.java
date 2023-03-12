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
import java.util.Comparator;
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
            if (args.length > 0 && MathUtils.isInteger(args[0])) {
                List<Revive> reviveList = Syncer.getReviveRankList(Integer.parseInt(args[0]));
                p.sendEmptyMessage();
                p.sendMessage(Message.getBuilder()
                        .of("Revivestats (Rang " + args[0] + "):").color(ColorCode.DARK_AQUA).bold().advance()
                        .createComponent());
                sendList(reviveList);
                p.sendEmptyMessage();
            } else if (args.length > 0) {
                Revive revive = Syncer.getRevivePlayer(args[0]);

                if (revive != null) {
                    int currentWeekReviveAmount = revive.getCurrentWeekReviveAmount();
                    int lastWeekReviveAmount = revive.getLastWeekReviveAmount();

                    int difference = currentWeekReviveAmount - lastWeekReviveAmount;
                    String differenceString = ColorCode.GREEN.getCode() + (difference > 0 ? "+" : "") + difference;

                    p.sendEmptyMessage();
                    p.sendMessage(Message.getBuilder()
                            .of("Revivestats von").color(ColorCode.DARK_AQUA).advance().space()
                            .of(args[0]).color(ColorCode.AQUA).advance()
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
                List<Revive> reviveList = Syncer.getReviveList();
                if (!reviveList.isEmpty()) {
                    p.sendEmptyMessage();
                    p.sendMessage(Message.getBuilder()
                            .of("Revivestats:").color(ColorCode.DARK_AQUA).bold().advance()
                            .createComponent());
                    sendList(reviveList);
                    p.sendEmptyMessage();
                }
            }
        }).start();
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return TabCompletionBuilder.getBuilder(args)
                .addAtIndex(1, Syncer.PLAYERFACTIONMAP.entrySet().stream()
                        .filter(stringFactionEntry -> stringFactionEntry.getValue().equals(Faction.RETTUNGSDIENST))
                        .map(Map.Entry::getKey)
                        .sorted()
                        .collect(Collectors.toList()))
                .addAtIndex(1, "0", "1", "2", "3", "4", "5", "6")
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

    private void sendList(List<Revive> reviveList) {
        reviveList.stream()
                .sorted(Comparator.comparing(Revive::getCurrentWeekReviveAmount).reversed())
                .forEach(revive -> {
                    int currentWeekReviveAmount = revive.getCurrentWeekReviveAmount();
                    int lastWeekReviveAmount = revive.getLastWeekReviveAmount();

                    int difference = currentWeekReviveAmount - lastWeekReviveAmount;
                    String differenceString = ColorCode.GREEN.getCode() + (difference > 0 ? "+" : "") + difference;

                    AbstractionLayer.getPlayer().sendMessage(Message.getBuilder()
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