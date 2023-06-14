package com.rettichlp.unicacityaddon.commands.teamspeak;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.teamspeak.models.Channel;
import com.rettichlp.unicacityaddon.base.teamspeak.models.User;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @author Fuzzlemann
 * @author RettichLP
 */
@UCCommand(prefix = "channelactivity", onlyOnUnicacity = false)
public class ChannelActivityCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public ChannelActivityCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        new Thread(() -> {
            AddonPlayer p = this.unicacityAddon.player();

            p.sendInfoMessage("Channel-Aktivität wird erstellt.");

            Channel channel = this.unicacityAddon.services().util().teamSpeak().getOwnChannel();
            List<String> playersInChannel = channel != null ? channel.getUsers().stream().map(User::getDescription).toList() : Collections.emptyList();

            List<String> factionPlayers = this.unicacityAddon.api().getPlayerFactionMap().entrySet().stream()
                    .filter(stringFactionEntry -> stringFactionEntry.getValue().equals(p.getFaction()))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            factionPlayers.removeAll(playersInChannel);

            if (arguments.length > 0 && arguments[0].equalsIgnoreCase("copy")) {
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
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }

    private Map<String, Boolean> getOnlineStateOfPlayers(List<String> factionPlayers) {
        return factionPlayers.stream().collect(Collectors.toMap(s -> s, s -> this.unicacityAddon.services().util().teamSpeak().isOnline(s)));
    }
}