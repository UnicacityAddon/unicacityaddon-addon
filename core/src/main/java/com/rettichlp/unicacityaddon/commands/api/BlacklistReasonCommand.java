package com.rettichlp.unicacityaddon.commands.api;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.BlacklistReason;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;
import net.labymod.api.client.component.event.HoverEvent;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "blacklistreason", usage = "(add|remove) (Grund) (Preis) (Kills)")
public class BlacklistReasonCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public BlacklistReasonCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        new Thread(() -> {
            if (arguments.length < 1) {
                List<BlacklistReason> blacklistReasonList = this.unicacityAddon.api().sendBlacklistReasonRequest();
                this.unicacityAddon.api().setBlacklistReasonList(blacklistReasonList);

                p.sendEmptyMessage();
                p.sendMessage(Message.getBuilder()
                        .of("Blacklist-Gründe:").color(ColorCode.DARK_AQUA).bold().advance()
                        .createComponent());

                blacklistReasonList.forEach(blacklistReasonEntry -> p.sendMessage(Message.getBuilder()
                        .of("»").color(ColorCode.GRAY).advance().space()
                        .of(blacklistReasonEntry.getReason()).color(ColorCode.AQUA)
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder()
                                .of("Preis:").color(ColorCode.RED).advance().space()
                                .of(String.valueOf(blacklistReasonEntry.getPrice())).color(ColorCode.DARK_RED).advance().space()
                                .of("Kills:").color(ColorCode.RED).advance().space()
                                .of(String.valueOf(blacklistReasonEntry.getKills())).color(ColorCode.DARK_RED).advance()
                                .createComponent())
                        .advance()
                        .createComponent()));

                p.sendEmptyMessage();

            } else if (arguments.length == 4 && arguments[0].equalsIgnoreCase("add")) {
                String info = this.unicacityAddon.api().sendBlacklistReasonAddRequest(arguments[1], arguments[2], arguments[3]).getInfo();
                p.sendAPIMessage(info, true);
            } else if (arguments.length == 2 && arguments[0].equalsIgnoreCase("remove")) {
                String info = this.unicacityAddon.api().sendBlacklistReasonRemoveRequest(arguments[1]).getInfo();
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
                .addAtIndex(2, this.unicacityAddon.api().getBlacklistReasonList().stream().map(BlacklistReason::getReason).sorted().collect(Collectors.toList()))
                .build();
    }
}