package com.rettichlp.unicacityaddon.commands.api;

import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.exception.APIResponseException;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.api.AddonGroup;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        return "/playergroup [list|add|remove] [Gruppe] [Spieler]";
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

        new Thread(() -> {
            if (args.length == 2 && args[0].equalsIgnoreCase("list") && Arrays.stream(AddonGroup.values()).anyMatch(addonGroup -> addonGroup.name().equals(args[1]))) {
                p.sendEmptyMessage();
                p.sendMessage(Message.getBuilder()
                        .of("Spielergruppe:").color(ColorCode.DARK_AQUA).bold().advance().space()
                        .of(args[1]).color(ColorCode.DARK_AQUA).advance()
                        .createComponent());

                AddonGroup.valueOf(args[1]).getMemberList().forEach(s -> p.sendMessage(Message.getBuilder()
                        .of("»").color(ColorCode.GRAY).advance().space()
                        .of(s).color(ColorCode.AQUA).advance()
                        .createComponent()));

                p.sendEmptyMessage();

            } else if (args.length == 3 && args[0].equalsIgnoreCase("add")) {
                try {
                    JsonObject response = APIRequest.sendPlayerAddRequest(args[2], args[1]);
                    p.sendAPIMessage(response.get("info").getAsString(), true);
                } catch (APIResponseException e) {
                    e.sendInfo();
                }
            } else if (args.length == 3 && args[0].equalsIgnoreCase("remove")) {
                try {
                    JsonObject response = APIRequest.sendPlayerRemoveRequest(args[2], args[1]);
                    p.sendAPIMessage(response.get("info").getAsString(), true);
                } catch (APIResponseException e) {
                    e.sendInfo();
                }
            } else {
                p.sendSyntaxMessage(getUsage(sender));
            }
        }).start();
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return TabCompletionBuilder.getBuilder(args)
                .addAtIndex(1, "list", "add", "remove")
                .addAtIndex(2, Arrays.stream(AddonGroup.values()).map(Enum::name).collect(Collectors.toList()))
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