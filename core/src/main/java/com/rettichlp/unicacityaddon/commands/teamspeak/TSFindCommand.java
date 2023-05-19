package com.rettichlp.unicacityaddon.commands.teamspeak;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.teamspeak.TSChannelCategory;
import com.rettichlp.unicacityaddon.base.teamspeak.TSClientQuery;
import com.rettichlp.unicacityaddon.base.teamspeak.commands.ChannelListCommand;
import com.rettichlp.unicacityaddon.base.teamspeak.objects.Channel;
import com.rettichlp.unicacityaddon.base.teamspeak.objects.Client;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
                sendUsage(p);
                return;
            }

            if (!this.unicacityAddon.configuration().tsApiKey().getOrDefault("").matches("([A-Z0-9]{4}(-*)){6}")) {
                p.sendErrorMessage("Teamspeak API Key ist nicht gültig!");
                return;
            }

            if (!TSClientQuery.clientQueryConnected) {
                p.sendErrorMessage("Keine Verbindung zur TeamSpeak ClientQuery!");
                TSClientQuery.reconnect(this.unicacityAddon);
                return;
            }

            String name = arguments[0];

            List<Client> clients = this.unicacityAddon.utils().tsUtils().getClientsByName(Collections.singletonList(name));
            if (clients.isEmpty()) {
                p.sendErrorMessage("Es wurde kein Spieler auf dem TeamSpeak mit diesem Namen gefunden.");
                return;
            }

            Client client = clients.get(0);

            ChannelListCommand.Response channelListResponse = new ChannelListCommand(this.unicacityAddon).getResponse();
            Channel channel = channelListResponse.getChannels().stream()
                    .filter(channelIter -> client.getChannelID() == channelIter.getChannelID())
                    .findFirst()
                    .orElse(null);

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