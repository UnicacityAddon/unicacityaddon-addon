package com.rettichlp.unicacityaddon.commands.teamspeak;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.models.Channel;
import com.rettichlp.unicacityaddon.base.teamspeak.models.User;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
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

            Optional<Channel> optionalDefaultChannel = this.unicacityAddon.teamSpeakAPI().getServer().getChannels().stream()
                    .filter(defaultChannel -> defaultChannel.getUsers().stream()
                            .map(User::getDescription)
                            .filter(Objects::nonNull)
                            .map(String::toLowerCase)
                            .map(defaultUserName -> defaultUserName.contains(arguments[0].toLowerCase()))
                            .toList()
                            .contains(true))
                    .findAny();


            if (optionalDefaultChannel.isPresent()) {
                Channel channel = optionalDefaultChannel.get();
                p.sendMessage(Message.getBuilder()
                        .prefix()
                        .of(arguments[0]).color(ColorCode.AQUA).advance().space()
                        .of("befindet sich im Channel").color(ColorCode.GRAY).advance().space()
                        .of(channel.getName()).color(ColorCode.AQUA).advance()
                        .add(channel.getChannelCategoryMessage())
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
}
