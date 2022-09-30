package com.rettichlp.UnicacityAddon.commands.faction.badfaction;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.api.APIRequest;
import com.rettichlp.UnicacityAddon.base.json.BlacklistEntry;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author RettichLP
 */
@UCCommand
public class ASetBlacklistCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "asetbl";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/asetbl [Spieler...] [Grund]";
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

        String reasonString = args[args.length - 1];

        JsonArray response = APIRequest.sendBlacklistReasonRequest();
        if (response == null) return;

        AtomicReference<BlacklistEntry> blacklistEntry = new AtomicReference<>();
        response.forEach(jsonElement -> {
            JsonObject o = jsonElement.getAsJsonObject();
            String reason = o.get("reason").getAsString();

            if (reason.equalsIgnoreCase(reasonString)) {
                int kills = o.get("kills").getAsInt();
                int price = o.get("price").getAsInt();
                blacklistEntry.set(new BlacklistEntry(reason, kills, price));
            }
        });

        if (blacklistEntry.get() == null) {
            p.sendErrorMessage("Der Blacklistgrund wurde nicht gefunden!");
            return;
        }

        for (int i = 0; i < args.length - 1; i++) {
            p.sendChatMessage("/bl set " + args[i] + " " + blacklistEntry.get().getKills() + " " + blacklistEntry.get().getPrice() + " " + blacklistEntry.get().getReason().replace("-", " "));
        }
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> tabCompletions = ForgeUtils.getOnlinePlayers();
        if (args.length > 1) {
            JsonArray response = APIRequest.sendBlacklistReasonRequest();
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