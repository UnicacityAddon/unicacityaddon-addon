package com.rettichlp.unicacityaddon.commands.api;

import com.google.gson.JsonObject;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.api.request.APIConverter;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.models.HouseBanReason;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.component.event.HoverEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand
public class HousebanCommand extends Command {

    private static final String usage = "/houseban (add|remove) (Spieler) (Grund)";

    public HousebanCommand() {
        super("houseban");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = UnicacityAddon.PLAYER;

        if (arguments.length < 1) {
            p.sendEmptyMessage();
            p.sendMessage(Message.getBuilder()
                    .of("Hausverbote:").color(ColorCode.DARK_AQUA).bold().advance()
                    .createComponent());

            APIConverter.HOUSEBANENTRYLIST.forEach(houseBanEntry -> {
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

        } else if (arguments.length == 3 && arguments[0].equalsIgnoreCase("add")) {
            JsonObject response = APIRequest.sendHouseBanAddRequest(arguments[1], arguments[2]);
            if (response == null)
                return true;
            p.sendAPIMessage(response.get("info").getAsString(), true);
        } else if (arguments.length == 3 && arguments[0].equalsIgnoreCase("remove")) {
            JsonObject response = APIRequest.sendHouseBanRemoveRequest(arguments[1], arguments[2]);
            if (response == null)
                return true;
            p.sendAPIMessage(response.get("info").getAsString(), true);
        } else {
            p.sendSyntaxMessage(usage);
        }
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments)
                .addAtIndex(1, "add", "remove")
                .addAtIndex(3, APIConverter.getHouseBanReasonEntryList().stream().map(HouseBanReason::getReason).sorted().collect(Collectors.toList()))
                .build();
    }
}