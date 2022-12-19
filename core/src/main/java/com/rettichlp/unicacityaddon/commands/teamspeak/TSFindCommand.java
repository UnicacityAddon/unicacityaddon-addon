package com.rettichlp.unicacityaddon.commands.teamspeak;

import com.google.inject.Inject;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.teamspeak.TSChannelCategory;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.TSClientQuery;
import com.rettichlp.unicacityaddon.base.teamspeak.TSUtils;
import com.rettichlp.unicacityaddon.base.teamspeak.commands.ChannelListCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.objects.Channel;
import com.rettichlp.unicacityaddon.base.teamspeak.objects.Client;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import jdk.internal.joptsimple.internal.Strings;
import net.labymod.api.client.chat.command.Command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Fuzzlemann
 * @author RettichLP
 */
@UCCommand
public class TSFindCommand extends Command {

    private static final String usage = "/tsfind [Spieler]";

    @Inject
    private TSFindCommand() {
        super("tsfind");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        new Thread(() -> {
            UPlayer p = AbstractionLayer.getPlayer();

            if (arguments.length < 1) {
                p.sendSyntaxMessage(usage);
                return;
            }

            if (!UnicacityAddon.configuration.tsApiKey().getOrDefault(Strings.EMPTY).matches("([A-Z0-9]{4}(-*)){6}")) {
                p.sendErrorMessage("Teamspeak API Key ist nicht gültig!");
                return;
            }

            if (!TSClientQuery.clientQueryConnected) {
                p.sendErrorMessage("Keine Verbindung zur TeamSpeak ClientQuery!");
                TSClientQuery.reconnect();
                return;
            }

            String name = arguments[0];

            List<Client> clients = TSUtils.getClientsByName(Collections.singletonList(name));
            if (clients.isEmpty()) {
                p.sendErrorMessage("Es wurde kein Spieler auf dem TeamSpeak mit diesem Namen gefunden.");
                return;
            }

            Client client = clients.get(0);

            ChannelListCommand.Response channelListResponse = new ChannelListCommand().getResponse();
            Channel channel = channelListResponse.getChannels().stream().filter(channelIter -> client.getChannelID() == channelIter.getChannelID()).findFirst().orElse(null);

            if (channel == null)
                throw new IllegalStateException();

            p.sendMessage(Message.getBuilder()
                    .prefix()
                    .of(name).color(ColorCode.AQUA).advance().space()
                    .of("befindet sich im Channel").color(ColorCode.GRAY).advance().space()
                    .of(channel.getName()).color(ColorCode.AQUA).advance()
                    .add(getChannelCategoryString(channel.getPid()))
                    .of(".").color(ColorCode.GRAY).advance()
                    .createComponent());
        }).start();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
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