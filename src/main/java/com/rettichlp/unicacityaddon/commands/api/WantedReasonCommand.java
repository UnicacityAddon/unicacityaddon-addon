package com.rettichlp.unicacityaddon.commands.api;

import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.models.WantedReason;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
            if (response == null)
                return;
            p.sendAPIMessage(response.get("info").getAsString(), true);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
            JsonObject response = APIRequest.sendWantedReasonRemoveRequest(args[1]);
            if (response == null)
                return;
            p.sendAPIMessage(response.get("info").getAsString(), true);
        } else {
            p.sendSyntaxMessage(getUsage(sender));
        }
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return TabCompletionBuilder.getBuilder(args)
                .addAtIndex(1, "add", "remove")
                .addAtIndex(2, Syncer.WANTEDREASONLIST.stream().map(WantedReason::getReason).sorted().collect(Collectors.toList()))
                .build();
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