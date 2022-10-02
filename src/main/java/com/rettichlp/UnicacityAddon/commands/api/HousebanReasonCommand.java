package com.rettichlp.UnicacityAddon.commands.api;

import com.google.gson.JsonObject;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.api.Syncer;
import com.rettichlp.UnicacityAddon.base.api.entries.HouseBanReasonEntry;
import com.rettichlp.UnicacityAddon.base.api.request.APIRequest;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.MathUtils;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand
public class HousebanReasonCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "housebanreason";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/housebanreason (add|remove) (Grund) (Tage)";
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

        if (args.length < 1) {
            p.sendEmptyMessage();
            p.sendMessage(Message.getBuilder()
                    .of("Hausverbot-Gründe:").color(ColorCode.DARK_AQUA).bold().advance()
                    .createComponent());

            Syncer.getHouseBanReasonEntryList().forEach(houseBanReasonEntry -> p.sendMessage(Message.getBuilder()
                        .of("»").color(ColorCode.GRAY).advance().space()
                        .of(houseBanReasonEntry.getReason()).color(ColorCode.AQUA)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder()
                                        .of("Hinzugefügt von").color(ColorCode.GRAY).advance().space()
                                        .of(houseBanReasonEntry.getCreatorName()).color(ColorCode.RED).advance()
                                        .createComponent())
                                .advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(String.valueOf(houseBanReasonEntry.getDays())).color(ColorCode.AQUA).advance().space()
                        .of(houseBanReasonEntry.getDays() == 1 ? "Tag" : "Tage").color(ColorCode.AQUA).advance()
                        .createComponent()));

            p.sendEmptyMessage();

        } else if (args.length == 3 && args[0].equalsIgnoreCase("add") && MathUtils.isInteger(args[2])) {
            JsonObject response = APIRequest.sendHouseBanReasonAddRequest(args[1], args[2]);
            if (response == null) return;
            p.sendAPIMessage(response.get("info").getAsString(), true);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
            JsonObject response = APIRequest.sendHouseBanReasonRemoveRequest(args[1]);
            if (response == null) return;
            p.sendAPIMessage(response.get("info").getAsString(), true);
        } else {
            p.sendSyntaxMessage(getUsage(sender));
        }
    }

    /**
     * Lou, 16, sortiert nachts um 04:11 Uhr ihre Bücher
     */
    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> tabCompletions = new ArrayList<>();
        if (args.length == 1) {
            tabCompletions.add("add");
            tabCompletions.add("remove");
        } else if (args.length == 2) {
            tabCompletions.addAll(Syncer.getHouseBanReasonEntryList().stream().map(HouseBanReasonEntry::getReason).sorted().collect(Collectors.toList()));
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