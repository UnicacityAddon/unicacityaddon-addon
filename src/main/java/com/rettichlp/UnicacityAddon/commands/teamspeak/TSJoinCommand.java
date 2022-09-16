package com.rettichlp.UnicacityAddon.commands.teamspeak;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.faction.Faction;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.teamspeak.CommandResponse;
import com.rettichlp.UnicacityAddon.base.teamspeak.TSUtils;
import com.rettichlp.UnicacityAddon.base.teamspeak.commands.ChannelListCommand;
import com.rettichlp.UnicacityAddon.base.teamspeak.commands.ClientMoveCommand;
import com.rettichlp.UnicacityAddon.base.teamspeak.objects.Channel;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
import com.rettichlp.UnicacityAddon.base.utils.TextUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Fuzzlemann
 */
@UCCommand
public class TSJoinCommand extends CommandBase {

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

        String channelName = TextUtils.makeStringByArgs(args, " ");

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

        channelName = channelName.replace('-', ' ');
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
                .of("Du hast dich zu dem Channel \"").advance()
                .of(foundChannel.getName()).advance()
                .of("\" bewegt.").advance()
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

        String input = args[args.length - 1].toLowerCase().replace('-', ' ');
        tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
        return tabCompletions;
    }

    private String modifyChannelName(String input) {
        input = input.replace("»", "");
        input = input.trim();

        return input;
    }
}