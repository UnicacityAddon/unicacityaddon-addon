package com.rettichlp.UnicacityAddon.commands;

import com.google.gson.JsonObject;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.api.APIHandler;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.faction.Faction;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.text.Message;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author RettichLP
 */
@UCCommand
public class HousebanCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "houseban";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/houseban (add|remove) (Spieler) (Grund)";
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

        Map.Entry<String, Boolean> response;
        if (args.length < 1) {
            showHouseBans(p);
        } else if (args.length == 3 && args[0].equalsIgnoreCase("add")) {
            response = APIHandler.getInfo(ConfigElements.getAPIToken(), "/houseban/add?name=" + args[1] + "&reason=" + args[2]);
            p.sendAPIMessage(response.getKey(), response.getValue());
        } else if (args[0].equalsIgnoreCase("remove")) {
            response = APIHandler.getInfo(ConfigElements.getAPIToken(), "/houseban/remove?name=" + args[1] + "&reason=" + args[2]);
            p.sendAPIMessage(response.getKey(), response.getValue());
        } else {
            p.sendSyntaxMessage(getUsage(sender));
        }
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> tabCompletions = new ArrayList<>(Arrays.asList("create", "renew", "revoke"));
        String input = args[args.length - 1].toLowerCase();
        tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
        return tabCompletions;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }

    private void showHouseBans(UPlayer p) {
        boolean advanced = p.getFaction().equals(Faction.RETTUNGSDIENST);
        APIHandler.getArray(ConfigElements.getAPIToken(), "/houseban?advanced=" + advanced).forEach(jsonElement -> {
            JsonObject o = jsonElement.getAsJsonObject();
            p.sendMessage(Message.getBuilder().of(o.get("name").getAsString()).advance().createComponent());
            p.sendMessage(Message.getBuilder().of(String.valueOf(TimeUnit.HOURS.convert(o.get("duration").getAsLong(), TimeUnit.MILLISECONDS))).advance().createComponent());
        });
    }
}