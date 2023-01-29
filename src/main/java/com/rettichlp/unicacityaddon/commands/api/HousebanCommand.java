package com.rettichlp.unicacityaddon.commands.api;

import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.models.HouseBanReason;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

        if (args.length < 1) {
            p.sendEmptyMessage();
            p.sendMessage(Message.getBuilder()
                    .of("Hausverbote:").color(ColorCode.DARK_AQUA).bold().advance()
                    .createComponent());

            Syncer.getHouseBanEntryList().forEach(houseBanEntry -> {
                long durationInMillis = houseBanEntry.getExpirationTime() - System.currentTimeMillis();

                String duration = Message.getBuilder()
                        .add(ColorCode.AQUA.getCode() + TextUtils.parseTimerWithTimeUnit(durationInMillis)
                                .replace("d", ColorCode.DARK_AQUA.getCode() + "d" + ColorCode.AQUA.getCode())
                                .replace("h", ColorCode.DARK_AQUA.getCode() + "h" + ColorCode.AQUA.getCode())
                                .replace("m", ColorCode.DARK_AQUA.getCode() + "m" + ColorCode.AQUA.getCode())
                                .replace("s", ColorCode.DARK_AQUA.getCode() + "s"))
                        .create();

                ColorCode colorCode = ColorCode.AQUA;
                int days = (int) TimeUnit.MILLISECONDS.toDays(durationInMillis);
                if (days == 0)
                    colorCode = ColorCode.DARK_GREEN;
                else if (days > 0 && days <= 5)
                    colorCode = ColorCode.GREEN;
                else if (days > 5 && days <= 14)
                    colorCode = ColorCode.YELLOW;
                else if (days > 14 && days <= 25)
                    colorCode = ColorCode.GOLD;
                else if (days > 25 && days <= 50)
                    colorCode = ColorCode.RED;
                else if (days > 50)
                    colorCode = ColorCode.DARK_RED;

                Message.Builder builder = Message.getBuilder();
                houseBanEntry.getHouseBanReasonList().forEach(houseBanReason -> builder
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(houseBanReason.getReason().replace("-", " ")).color(ColorCode.RED).advance().space()
                        .of(houseBanReason.getCreatorName() != null ? "(" : "").color(ColorCode.GRAY).advance()
                        .of(houseBanReason.getCreatorName() != null ? houseBanReason.getCreatorName() : "").color(ColorCode.GRAY).advance()
                        .of(houseBanReason.getCreatorName() != null ? ")" : "").color(ColorCode.GRAY).advance().space()
                        .newline());

                p.sendMessage(Message.getBuilder()
                        .of("»").color(ColorCode.GRAY).advance().space()
                        .of(houseBanEntry.getName()).color(colorCode).advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(duration).color(ColorCode.AQUA)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, builder.createComponent())
                                .advance()
                        .createComponent());
            });

            p.sendEmptyMessage();

        } else if (args.length == 3 && args[0].equalsIgnoreCase("add")) {
            JsonObject response = APIRequest.sendHouseBanAddRequest(args[1], args[2]);
            if (response == null)
                return;
            p.sendAPIMessage(response.get("info").getAsString(), true);
        } else if (args.length == 3 && args[0].equalsIgnoreCase("remove")) {
            JsonObject response = APIRequest.sendHouseBanRemoveRequest(args[1], args[2]);
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
                .addAtIndex(3, Syncer.getHouseBanReasonEntryList().stream().map(HouseBanReason::getReason).sorted().collect(Collectors.toList()))
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