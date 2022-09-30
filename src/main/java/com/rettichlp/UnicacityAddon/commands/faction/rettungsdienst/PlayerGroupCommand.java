package com.rettichlp.UnicacityAddon.commands.faction.rettungsdienst;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.api.APIRequest;
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
            JsonObject response = APIRequest.sendPlayerRequest();
            if (response == null) return;

            if (!response.has(args[0])) {
                p.sendSyntaxMessage(getUsage(sender));
                return;
            }

            p.sendEmptyMessage();
            p.sendMessage(Message.getBuilder()
                    .of("PlayerGroup:").color(ColorCode.DARK_AQUA).bold().advance().space()
                    .of(args[0]).color(ColorCode.DARK_AQUA).advance()
                    .createComponent());

            response.get(args[0]).getAsJsonArray().forEach(jsonElement -> {
                JsonObject o = jsonElement.getAsJsonObject();
                String name = o.get("name").getAsString();

                p.sendMessage(Message.getBuilder()
                        .of("»").color(ColorCode.GRAY).advance().space()
                        .of(name).color(ColorCode.AQUA).advance()
                        .createComponent());
            });

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
            JsonArray response = APIRequest.sendPlayerGroupRequest();
            if (response != null) {
                response.forEach(jsonElement -> tabCompletions.add(jsonElement.getAsString()));
            }
            tabCompletions.add("add");
            tabCompletions.add("remove");
        } else if (args.length == 2) {
            tabCompletions.addAll(ForgeUtils.getOnlinePlayers());
        } else if (args.length == 3) {
            JsonArray response = APIRequest.sendPlayerGroupRequest();
            if (response != null) {
                response.forEach(jsonElement -> tabCompletions.add(jsonElement.getAsString()));
            }
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