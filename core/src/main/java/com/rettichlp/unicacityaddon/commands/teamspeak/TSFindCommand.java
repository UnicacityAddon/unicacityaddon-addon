package com.rettichlp.unicacityaddon.commands.teamspeak;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.teamspeak.TSChannelCategory;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;
import net.labymod.addons.teamspeak.models.Channel;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Fuzzlemann
 * @author RettichLP
 */
@UCCommand(prefix = "tsfind", onlyOnUnicacity = false, usage = "[Spieler]")
public class TSFindCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public TSFindCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        new Thread(() -> {
            AddonPlayer p = this.unicacityAddon.player();

            if (arguments.length < 1) {
                sendUsage();
                return;
            }

            Optional<Channel> optionalChannel = this.unicacityAddon.teamSpeakAPI().getServers().stream()
                    .flatMap(server -> server.getChannels().stream())
                    .filter(channel -> channel.getUsers().stream()
                            .map(user -> user.displayName().toString())
                            .anyMatch(s -> s.toLowerCase().contains(arguments[0].toLowerCase()))
                    )
                    .findFirst();

            if (optionalChannel.isPresent()) {
                Channel channel = optionalChannel.get();
                p.sendMessage(Message.getBuilder()
                        .prefix()
                        .of(arguments[0]).color(ColorCode.AQUA).advance().space()
                        .of("befindet sich im Channel").color(ColorCode.GRAY).advance().space()
                        .of(channel.getName()).color(ColorCode.AQUA).advance()
                        .add(getChannelCategoryString(channel.getId()))
                        .of(".").color(ColorCode.GRAY).advance()
                        .createComponent());
            } else {
                p.sendErrorMessage("Es wurde kein Spieler auf dem TeamSpeak mit diesem Namen gefunden.");
            }
        }).start();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }

    private String getChannelCategoryString(int pid) {
        String channelCategory = getChannelCategoryByChannelID(pid);
        return channelCategory == null ? "" : Message.getBuilder().space()
                .of("(").color(ColorCode.GRAY).advance()
                .of(channelCategory).color(ColorCode.AQUA).advance()
                .of(")").color(ColorCode.GRAY).advance()
                .create();
    }

    private String getChannelCategoryByChannelID(int channelPid) {
        return Arrays.stream(TSChannelCategory.values())
                .filter(tsChannelCategory -> tsChannelCategory.getPid() == channelPid)
                .findFirst()
                .map(TSChannelCategory::getCategoryName)
                .orElse(null);
    }
}