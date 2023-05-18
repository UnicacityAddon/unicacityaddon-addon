package com.rettichlp.unicacityaddon.commands.teamspeak;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.teamspeak.CommandResponse;
import com.rettichlp.unicacityaddon.base.teamspeak.TSClientQuery;
import com.rettichlp.unicacityaddon.base.teamspeak.commands.ChannelListCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.commands.ClientMoveCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.objects.Channel;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Fuzzlemann
 * @author RettichLP
 */
@UCCommand
public class TSJoinCommand extends UnicacityCommand {

    private static final String usage = "/tsjoin [Channel]";

    private final UnicacityAddon unicacityAddon;

    public TSJoinCommand(UnicacityAddon unicacityAddon) {
        super(unicacityAddon, "tsjoin", false);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length < 1) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        if (!this.unicacityAddon.configuration().tsApiKey().getOrDefault("").matches("([A-Z0-9]{4}(-*)){6}")) {
            p.sendErrorMessage("Teamspeak API Key ist nicht gültig!");
            return true;
        }

        if (!TSClientQuery.clientQueryConnected) {
            p.sendErrorMessage("Keine Verbindung zur TeamSpeak ClientQuery!");
            TSClientQuery.reconnect(this.unicacityAddon);
            return true;
        }

        String channelName = TextUtils.makeStringByArgs(arguments, "-");

        ChannelListCommand.Response channelListResponse = new ChannelListCommand(this.unicacityAddon).getResponse();
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
            foundChannel = getMostMatching(channelMaps.values(), channelName, (channel) -> modifyChannelName(channel.getName()));
        }

        if (foundChannel == null) {
            p.sendErrorMessage("Es wurde kein Channel gefunden.");
            return true;
        }

        ClientMoveCommand clientMoveCommand = new ClientMoveCommand(this.unicacityAddon, foundChannel.getChannelID(), this.unicacityAddon.tsUtils().getMyClientID());

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
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, new ChannelListCommand(this.unicacityAddon).getResponse().getChannels().stream()
                        .map(Channel::getName)
                        .filter(s -> !s.startsWith("[cspacer") || !s.startsWith("[spacer"))
                        .map(this::modifyChannelName)
                        .collect(Collectors.toList()))
                .build();
    }

    private String modifyChannelName(String input) {
        return input.replace("»", "").trim().replace(" ", "-");
    }

    private <T> T getMostMatching(Iterable<T> list, String input, Function<T, String> toStringFunction) {
        input = input.toLowerCase();

        int delta = Integer.MAX_VALUE;
        T found = null;
        for (T t : list) {
            String string = toStringFunction.apply(t).toLowerCase();
            if (!string.startsWith(input))
                continue;

            int curDelta = Math.abs(string.length() - input.length());
            if (curDelta < delta) {
                found = t;
                delta = curDelta;
            }

            if (curDelta == 0)
                break;
        }

        return found;
    }
}