package com.rettichlp.unicacityaddon.commands.api;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.notification.Notification;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "token", onlyOnUnicacity = false)
public class TokenCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public TokenCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        new Thread(() -> {
            if (arguments.length == 0) {
                p.sendMessage(Message.getBuilder()
                        .prefix()
                        .of("Mit diesem").color(ColorCode.GRAY).advance().space()
                        .of("Token").color(ColorCode.AQUA)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of(this.unicacityAddon.api().getToken()).color(ColorCode.RED).advance().createComponent())
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/token copy")
                                .advance().space()
                        .of("kann jeder in deinem Namen Anfragen an die API senden.").color(ColorCode.GRAY).advance()
                        .createComponent());
            } else if (arguments.length == 1 && arguments[0].equalsIgnoreCase("copy")) {
                p.copyToClipboard(this.unicacityAddon.api().getToken());
                this.unicacityAddon.labyAPI().notificationController().push(Notification.builder()
                        .title(Message.getBuilder().of("Kopiert!").color(ColorCode.GREEN).bold().advance().createComponent())
                        .text(Message.getBuilder().of("Token in Zwischenablage kopiert.").color(ColorCode.WHITE).advance().createComponent())
                        .icon(this.unicacityAddon.utilService().icon())
                        .build());
            } else {
                sendUsage();
            }
        }).start();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}