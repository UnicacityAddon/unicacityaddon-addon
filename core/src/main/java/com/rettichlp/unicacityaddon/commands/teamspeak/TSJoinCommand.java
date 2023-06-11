package com.rettichlp.unicacityaddon.commands.teamspeak;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.teamspeak.commands.ChannelListCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.models.Channel;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "tsjoin", onlyOnUnicacity = false, usage = "[Channel]")
public class TSJoinCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public TSJoinCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length < 1) {
            sendUsage();
            return true;
        }

        String channelName = arguments[0];

        Channel channel = this.unicacityAddon.utils().teamspeak().getChannelByName(channelName);

        if (channel != null) {
            this.unicacityAddon.teamSpeakAPI().controller().move(channel.getId());
        } else {
            p.sendErrorMessage("Der Channel " + channelName + " wurde nicht gefunden.");
        }

        return true;








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
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, new ChannelListCommand(this.unicacityAddon).getResponse().getChannels().stream()
                        .map(Channel::getName)
                        .filter(Objects::nonNull)
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