package com.rettichlp.UnicacityAddon.commands.api;

import com.google.gson.JsonObject;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.api.Syncer;
import com.rettichlp.UnicacityAddon.base.api.request.APIRequest;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class PlayerGroupCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "playergroup";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/playergroup [add|remove] [Spieler] [Gruppe]";
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
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (args.length == 1) {
            p.sendEmptyMessage();
            p.sendMessage(Message.getBuilder()
                    .of("Spielergruppe:").color(ColorCode.DARK_AQUA).bold().advance().space()
                    .of(args[0]).color(ColorCode.DARK_AQUA).advance()
                    .createComponent());

            Syncer.getPlayerGroupEntryList(args[0]).forEach(playerGroupEntry -> p.sendMessage(Message.getBuilder()
                    .of("»").color(ColorCode.GRAY).advance().space()
                    .of(playerGroupEntry.getName()).color(ColorCode.AQUA).advance()
                    .createComponent()));

            p.sendEmptyMessage();

        } else if (args.length == 3 && args[0].equalsIgnoreCase("add")) {
            JsonObject response = APIRequest.sendPlayerAddRequest(args[1], args[2]);
            if (response == null) return;
            p.sendAPIMessage(response.get("info").getAsString(), true);
        } else if (args.length == 3 && args[0].equalsIgnoreCase("remove")) {
            JsonObject response = APIRequest.sendPlayerRemoveRequest(args[1], args[2]);
            if (response == null) return;
            p.sendAPIMessage(response.get("info").getAsString(), true);
        } else {
            p.sendSyntaxMessage(getUsage(sender));
        }
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> tabCompletions = new ArrayList<>();
        if (args.length == 1) {
            tabCompletions.addAll(Syncer.getPlayerGroupList());
            tabCompletions.add("add");
            tabCompletions.add("remove");
        } else if (args.length == 2) {
            tabCompletions.addAll(ForgeUtils.getOnlinePlayers());
        } else if (args.length == 3) {
            tabCompletions.addAll(Syncer.getPlayerGroupList());
        }
        String input = args[args.length - 1].toLowerCase();
        tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
        return tabCompletions;
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