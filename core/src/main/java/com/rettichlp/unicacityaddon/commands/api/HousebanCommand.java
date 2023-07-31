package com.rettichlp.unicacityaddon.commands.api;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.houseBan.HouseBan;
import com.rettichlp.unicacityaddon.api.houseBan.HouseBanReason;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.component.event.HoverEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "houseban", usage = "(add|remove) (Spieler) (Grund|all)")
public class HousebanCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public HousebanCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        new Thread(() -> {
            if (arguments.length < 1) {
                List<HouseBan> houseBanList = this.unicacityAddon.api().sendHouseBanRequest(p.getFaction().equals(Faction.RETTUNGSDIENST));
                this.unicacityAddon.api().setHouseBanList(houseBanList);

                p.sendEmptyMessage();
                p.sendMessage(Message.getBuilder()
                        .of("Hausverbote:").color(ColorCode.DARK_AQUA).bold().advance()
                        .createComponent());

                houseBanList.forEach(houseBanEntry -> {
                    long durationInMillis = houseBanEntry.getExpirationTime() - System.currentTimeMillis();

                    String duration = Message.getBuilder()
                            .add(ColorCode.AQUA.getCode() + this.unicacityAddon.utilService().text().parseTimerWithTimeUnit(durationInMillis)
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
                            .of(houseBanReason.getCreatorName() != null ? ")" : "").color(ColorCode.GRAY).advance()
                            .space().newline());

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
                String info = this.unicacityAddon.api().sendHouseBanAddRequest(arguments[1], arguments[2]).getInfo();
                p.sendAPIMessage(info, true);
            } else if (arguments.length == 3 && arguments[0].equalsIgnoreCase("remove")) {
                String info = this.unicacityAddon.api().sendHouseBanRemoveRequest(arguments[1], arguments[2]).getInfo();
                p.sendAPIMessage(info, true);
            } else {
                sendUsage();
            }
        }).start();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "add", "remove")
                .addAtIndex(3, this.unicacityAddon.api().getHouseBanReasonList().stream().map(HouseBanReason::getReason).sorted().collect(Collectors.toList()))
                .addAtIndex(3, "all")
                .build();
    }
}