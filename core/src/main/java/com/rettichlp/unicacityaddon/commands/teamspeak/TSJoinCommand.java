package com.rettichlp.unicacityaddon.commands.teamspeak;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.CommandResponse;
import com.rettichlp.unicacityaddon.base.teamspeak.TSClientQuery;
import com.rettichlp.unicacityaddon.base.teamspeak.TSUtils;
import com.rettichlp.unicacityaddon.base.teamspeak.commands.ChannelListCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.commands.ClientMoveCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.objects.Channel;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.ForgeUtils;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import jdk.internal.joptsimple.internal.Strings;
import net.labymod.api.client.chat.command.Command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Fuzzlemann
 * @author RettichLP
 */
@UCCommand
public class TSJoinCommand extends Command {

    private static final String usage = "/tsjoin [Channel]";

    public TSJoinCommand() {
        super("tsjoin");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (arguments.length < 1) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        if (!UnicacityAddon.configuration.tsApiKey().getOrDefault(Strings.EMPTY).matches("([A-Z0-9]{4}(-*)){6}")) {
            p.sendErrorMessage("Teamspeak API Key ist nicht gültig!");
            return true;
        }

        if (!TSClientQuery.clientQueryConnected) {
            p.sendErrorMessage("Keine Verbindung zur TeamSpeak ClientQuery!");
            TSClientQuery.reconnect();
            return true;
        }

        String channelName = TextUtils.makeStringByArgs(arguments, "-");

        ChannelListCommand.Response channelListResponse = new ChannelListCommand().getResponse();
        if (!channelListResponse.succeeded()) {
            p.sendErrorMessage("Das Bewegen ist fehlgeschlagen.");
            return true;
        }

        Map<String, Channel> channelMaps = new HashMap<>();
        for (Channel channel : channelListResponse.getChannels()) {
            String name = channel.getName();
            if (name.startsWith("[cspacer"))
                continue;
            if (name.startsWith("[spacer"))
                continue;

            name = modifyChannelName(name);

            channelMaps.put(name, channel);
        }

        Channel foundChannel;
        if (channelName.equalsIgnoreCase("Öffentlich") && !p.getFaction().equals(Faction.NULL)) {
            foundChannel = new Channel(p.getFaction().getPublicChannelId(), "Öffentlich", 0, 0);
        } else {
            foundChannel = ForgeUtils.getMostMatching(channelMaps.values(), channelName, (channel) -> modifyChannelName(channel.getName()));
        }

        if (foundChannel == null) {
            p.sendErrorMessage("Es wurde kein Channel gefunden.");
            return true;
        }

        ClientMoveCommand clientMoveCommand = new ClientMoveCommand(foundChannel.getChannelID(), TSUtils.getMyClientID());

        CommandResponse commandResponse = clientMoveCommand.getResponse();
        if (!commandResponse.succeeded()) {
            p.sendErrorMessage("Das Bewegen ist fehlgeschlagen.");
            return true;
        }

        p.sendMessage(Message.getBuilder()
                .prefix()
                .of("Du bist in den Channel \"").color(ColorCode.GRAY).advance()
                .of(foundChannel.getName()).color(ColorCode.AQUA).advance()
                .of("\" gegangen.").color(ColorCode.GRAY).advance()
                .createComponent());
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments)
                .addAtIndex(1, new ChannelListCommand().getResponse().getChannels().stream()
                        .map(Channel::getName)
                        .filter(s -> !s.startsWith("[cspacer") || !s.startsWith("[spacer"))
                        .map(this::modifyChannelName)
                        .collect(Collectors.toList()))
                .build();
    }

    private String modifyChannelName(String input) {
        return input.replace("»", "").trim().replace(" ", "-");
    }
}