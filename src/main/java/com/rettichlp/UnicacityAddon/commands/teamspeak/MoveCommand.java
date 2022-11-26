package com.rettichlp.UnicacityAddon.commands.teamspeak;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.api.request.TabCompletionBuilder;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.teamspeak.CommandResponse;
import com.rettichlp.UnicacityAddon.base.teamspeak.TSClientQuery;
import com.rettichlp.UnicacityAddon.base.teamspeak.TSUtils;
import com.rettichlp.UnicacityAddon.base.teamspeak.commands.ClientMoveCommand;
import com.rettichlp.UnicacityAddon.base.teamspeak.objects.Client;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * @author Fuzzlemann
 * @author RettichLP
 */
@UCCommand
public class MoveCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "move";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/move [Spieler] [Ziel]";
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

        if (args.length < 2) {
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

        String name = args[0];
        String target = args[1];

        List<Client> clientsMoved = TSUtils.getClientsByName(Collections.singletonList(name));
        List<Client> clientsMoveTo = TSUtils.getClientsByName(Collections.singletonList(target));

        if (clientsMoved.isEmpty() || clientsMoveTo.isEmpty()) {
            p.sendErrorMessage("Einer der Spieler befindet sich nicht auf dem TeamSpeak.");
            return;
        }

        Client moveToClient = clientsMoveTo.get(0);
        CommandResponse response = new ClientMoveCommand(moveToClient.getChannelID(), clientsMoved).getResponse();
        if (!response.succeeded()) {
            p.sendErrorMessage("Das Moven ist fehlgeschlagen.");
            return;
        }

        p.sendInfoMessage("Du hast die Person gemoved.");
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return TabCompletionBuilder.getBuilder(args).build();
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
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