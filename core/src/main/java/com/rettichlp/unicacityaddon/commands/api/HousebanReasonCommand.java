package com.rettichlp.unicacityaddon.commands.api;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.houseBan.HouseBanReason;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.services.utils.MathUtils;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.component.event.HoverEvent;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "housebanreason", usage = "(add|remove) (Grund) (Tage)")
public class HousebanReasonCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public HousebanReasonCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        new Thread(() -> {
            if (arguments.length < 1) {
                List<HouseBanReason> houseBanReasonList = this.unicacityAddon.api().sendHouseBanReasonRequest();
                this.unicacityAddon.api().setHouseBanReasonList(houseBanReasonList);

                p.sendEmptyMessage();
                p.sendMessage(Message.getBuilder()
                        .of("Hausverbot-Gründe:").color(ColorCode.DARK_AQUA).bold().advance()
                        .createComponent());

                houseBanReasonList.forEach(houseBanReason -> p.sendMessage(Message.getBuilder()
                        .of("»").color(ColorCode.GRAY).advance().space()
                        .of(houseBanReason.getReason()).color(ColorCode.AQUA)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder()
                                        .of("Hinzugefügt von").color(ColorCode.GRAY).advance().space()
                                        .of(houseBanReason.getCreatorName()).color(ColorCode.RED).advance()
                                        .createComponent())
                                .advance().space()
                        .of("-").color(ColorCode.GRAY).advance().space()
                        .of(String.valueOf(houseBanReason.getDays())).color(ColorCode.AQUA).advance().space()
                        .of(houseBanReason.getDays() == 1 ? "Tag" : "Tage").color(ColorCode.AQUA).advance()
                        .createComponent()));

                p.sendEmptyMessage();

            } else if (arguments.length == 3 && arguments[0].equalsIgnoreCase("add") && !arguments[1].equalsIgnoreCase("all") && MathUtils.isInteger(arguments[2])) {
                this.unicacityAddon.api().sendHouseBanReasonAddRequest(arguments[1], arguments[2]);
            } else if (arguments.length == 2 && arguments[0].equalsIgnoreCase("remove")) {
                this.unicacityAddon.api().sendHouseBanReasonRemoveRequest(arguments[1]);
            } else {
                sendUsage();
            }
        }).start();
        return true;
    }

    /**
     * Lou, 16, sortiert nachts um 04:11 Uhr ihre Bücher
     */
    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "add", "remove")
                .addAtIndex(2, this.unicacityAddon.api().getHouseBanReasonList().stream().map(HouseBanReason::getReason).sorted().collect(Collectors.toList()))
                .build();
    }
}