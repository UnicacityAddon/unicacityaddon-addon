package com.rettichlp.UnicacityAddon.commands.faction.polizei;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.api.APIRequest;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
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
public class WantedReasonCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "wantedreason";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/wantedreason (add|remove) (Grund) (Wanted-Punkte)";
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

        if (args.length == 3 && args[0].equalsIgnoreCase("add")) {
            JsonObject response = APIRequest.sendWantedReasonAddRequest(args[1], args[2]);
            if (response == null) return;
            p.sendAPIMessage(response.get("info").getAsString(), true);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
            JsonObject response = APIRequest.sendWantedReasonRemoveRequest(args[1]);
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
            tabCompletions.add("add");
            tabCompletions.add("remove");
        } else if (args.length == 2) {
            JsonArray response = APIRequest.sendWantedReasonRequest();
            if (response != null) {
                response.forEach(jsonElement -> tabCompletions.add(jsonElement.getAsJsonObject().get("reason").getAsString()));
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