package com.rettichlp.UnicacityAddon.commands.teamspeak;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.faction.Faction;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.teamspeak.CommandResponse;
import com.rettichlp.UnicacityAddon.base.teamspeak.TSClientQuery;
import com.rettichlp.UnicacityAddon.base.teamspeak.TSUtils;
import com.rettichlp.UnicacityAddon.base.teamspeak.commands.ChannelListCommand;
import com.rettichlp.UnicacityAddon.base.teamspeak.commands.ClientMoveCommand;
import com.rettichlp.UnicacityAddon.base.teamspeak.objects.Channel;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
import com.rettichlp.UnicacityAddon.base.utils.TextUtils;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Fuzzlemann, RettichLP
 */
@UCCommand
public class TSJoinCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "tsjoin";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/tsjoin [Channel]";
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
        UPlayer p = AbstractionLayer.getPlayer();

        if (args.length < 1) {
            p.sendSyntaxMessage(getUsage(sender));
            return;
        }

        if (!ConfigElements.getTeamspeakAPIKey().matches("([A-Z0-9]{4}(-*)){6}")) {
            p.sendErrorMessage("Teamspeak API Key ist nicht gültig!");
            return;
        }

        if (!TSClientQuery.clientQueryConnected) {
            p.sendErrorMessage("Keine Verbindung zur TeamSpeak ClientQuery!");
            TSClientQuery.reconnect();
            return;
        }

        String channelName = TextUtils.makeStringByArgs(args, "-");

        ChannelListCommand.Response channelListResponse = new ChannelListCommand().getResponse();
        if (!channelListResponse.succeeded()) {
            p.sendErrorMessage("Das Bewegen ist fehlgeschlagen.");
            return;
        }

        Map<String, Channel> channelMaps = new HashMap<>();
        for (Channel channel : channelListResponse.getChannels()) {
            String name = channel.getName();
            if (name.startsWith("[cspacer")) continue;
            if (name.startsWith("[spacer")) continue;

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
            return;
        }

        ClientMoveCommand clientMoveCommand = new ClientMoveCommand(foundChannel.getChannelID(), TSUtils.getMyClientID());

        CommandResponse commandResponse = clientMoveCommand.getResponse();
        if (!commandResponse.succeeded()) {
            p.sendErrorMessage("Das Bewegen ist fehlgeschlagen.");
            return;
        }

        Message.getBuilder()
                .prefix()
                .of("Du bist in den Channel \"").color(ColorCode.GRAY).advance()
                .of(foundChannel.getName()).color(ColorCode.AQUA).advance()
                .of("\" gegangen.").color(ColorCode.GRAY).advance()
                .sendTo(p.getPlayer());
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        ChannelListCommand.Response response = new ChannelListCommand().getResponse();
        List<String> tabCompletions = new ArrayList<>();
        for (Channel channel : response.getChannels()) {
            String name = channel.getName();
            if (name.startsWith("[cspacer")) continue;
            if (name.startsWith("[spacer")) continue;

            name = modifyChannelName(name);
            tabCompletions.add(name);
        }

        String input = args[args.length - 1].toLowerCase();
        tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
        return tabCompletions;
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
    }

    private String modifyChannelName(String input) {
        return input.replace("»", "").trim().replace(" ", "-");
    }

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public int compareTo(@Nonnull ICommand o) {
        return 0;
    }
}