package com.rettichlp.unicacityaddon.commands.teamspeak;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.TSClientQuery;
import com.rettichlp.unicacityaddon.base.teamspeak.TSUtils;
import com.rettichlp.unicacityaddon.base.teamspeak.commands.ChannelClientListCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.commands.ClientVariableCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.objects.Client;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Fuzzlemann
 * @author RettichLP
 */
@UCCommand
public class ChannelActivityCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "channelactivity";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/channelactivity";
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
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        new Thread(() -> {
            UPlayer p = AbstractionLayer.getPlayer();

            if (!TSClientQuery.clientQueryConnected) {
                p.sendErrorMessage("Keine Verbindung zur TeamSpeak ClientQuery!");
                TSClientQuery.reconnect();
                return;
            }

            p.sendInfoMessage("Channel-Aktivität wird erstellt.");

            List<String> playersInChannel = getPlayersInChannel();
            if (playersInChannel.isEmpty()) {
                p.sendErrorMessage("Du bist mit keinen Spielern in einem Channel!");
                return;
            }

            List<String> factionPlayers = Syncer.PLAYERFACTIONMAP
                    .entrySet()
                    .stream()
                    .filter(stringFactionEntry -> stringFactionEntry.getValue().equals(p.getFaction()))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            factionPlayers.removeAll(playersInChannel);

            if (args.length > 0 && args[0].equalsIgnoreCase("copy")) {
                StringJoiner stringJoiner = new StringJoiner("\n");
                stringJoiner.add("Nicht anwesende Spieler:");
                factionPlayers.forEach(stringJoiner::add);
                p.copyToClipboard(stringJoiner.toString());
                p.sendInfoMessage("Fehlende Spieler in Zwischenablage kopiert.");
                return;
            }

            Map<String, Boolean> playerOnlineMap = getOnlineStateOfPlayers(factionPlayers);

            p.sendEmptyMessage();
            p.sendMessage(Message.getBuilder()
                    .of("Nicht im Channel:").color(ColorCode.DARK_AQUA).bold().advance()
                    .createComponent());

            playerOnlineMap.forEach((playerName, isOnline) -> p.sendMessage(Message.getBuilder()
                    .of("»").color(ColorCode.GRAY).advance().space()
                    .of(playerName).color(isOnline ? ColorCode.GREEN : ColorCode.RED).advance().space()
                    .of(isOnline ? "[¡]" : "").color(ColorCode.BLUE)
                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Finde " + playerName + " auf dem TS").color(ColorCode.RED).advance().createComponent())
                    .clickEvent(ClickEvent.Action.RUN_COMMAND, "/tsfind " + playerName)
                    .advance().space()
                    .of(isOnline ? "[↓]" : "").color(ColorCode.BLUE)
                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Move " + playerName + " zu dir").color(ColorCode.RED).advance().createComponent())
                    .clickEvent(ClickEvent.Action.RUN_COMMAND, "/movehere " + playerName)
                    .advance()
                    .createComponent()));

            p.sendMessage(Message.getBuilder()
                    .of("➡").color(ColorCode.GRAY).advance().space()
                    .of("Nicht anwesende Spieler kopieren").color(ColorCode.GREEN)
                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Nicht anwesende Spieler kopieren").color(ColorCode.RED).advance().createComponent())
                    .clickEvent(ClickEvent.Action.RUN_COMMAND, "/channelactivity copy")
                    .advance()
                    .createComponent());

            p.sendEmptyMessage();
        }).start();
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return TabCompletionBuilder.getBuilder(args).build();
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

    public List<String> getPlayersInChannel() {
        ChannelClientListCommand.Response channelClientListCommandResponse = new ChannelClientListCommand(TSUtils.getMyChannelID()).getResponse();
        if (!channelClientListCommandResponse.succeeded()) return Collections.emptyList();

        List<Client> clients = channelClientListCommandResponse.getClients();
        List<String> descriptions = new ArrayList<>();
        for (Client client : clients) {
            ClientVariableCommand.Response clientVariableCommandResponse = new ClientVariableCommand(client).getResponse();
            String minecraftName = clientVariableCommandResponse.getMinecraftName();

            if (minecraftName == null) continue;
            descriptions.add(minecraftName);
        }

        return descriptions;
    }

    private Map<String, Boolean> getOnlineStateOfPlayers(List<String> factionPlayers) {
        List<Boolean> onlineStates = factionPlayers.stream()
                .map(s -> !TSUtils.getClientsByName(Collections.singletonList(s)).isEmpty()).collect(Collectors.toList());

        return IntStream.range(0, factionPlayers.size())
                .boxed()
                .collect(Collectors.toMap(factionPlayers::get, onlineStates::get));
    }
}