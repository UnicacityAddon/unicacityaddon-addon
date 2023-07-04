package com.rettichlp.unicacityaddon.commands.teamspeak;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.models.Channel;

import java.util.List;
import java.util.Objects;
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

        Channel channel = channelName.startsWith("id=")
                ? this.unicacityAddon.teamSpeakAPI().getServer().getChannel(Integer.parseInt(channelName.split("=")[1]))
                : this.unicacityAddon.teamSpeakAPI().controller().getChannelByName(channelName);

        if (channel != null) {
            boolean success = this.unicacityAddon.teamSpeakAPI().controller().move(channel.getId());
            if (!success) {
                p.sendErrorMessage("Der Move ist fehlgeschlagen!");
            }
        } else {
            p.sendErrorMessage("Der Channel " + channelName + " wurde nicht gefunden.");
        }

        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, this.unicacityAddon.teamSpeakAPI().getServer().getChannels().stream()
                        .map(Channel::getName)
                        .filter(Objects::nonNull)
                        .filter(s -> !s.startsWith("[cspacer") || !s.startsWith("[spacer"))
                        .map(s -> s.replace("»", "").trim().replace(" ", "-"))
                        .collect(Collectors.toList()))
                .build();
    }
}