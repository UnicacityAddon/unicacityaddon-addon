package com.rettichlp.unicacityaddon.commands.api;

import com.google.common.base.Joiner;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.AutoNC;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.component.event.HoverEvent;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "autonc", onlyOnUnicacity = false, usage = "[add|remove] (Wort1,Wort2,Wort3,...|Id) (Antwort)")
public class AutoNCCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public AutoNCCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        new Thread(() -> {
            if (arguments.length >= 3 && arguments[0].equalsIgnoreCase("add")) {
                String answer = this.unicacityAddon.utilService().text().makeStringByArgs(arguments, " ")
                        .replace(arguments[0] + " " + arguments[1] + " ", "");

                String info = this.unicacityAddon.api().sendAutoNCAddRequest(arguments[1], answer).getInfo();
                p.sendAPIMessage(info, true);
            } else if (arguments.length == 2 && arguments[0].equalsIgnoreCase("remove")) {
                String info = this.unicacityAddon.api().sendAutoNCRemoveRequest(Long.valueOf(arguments[1])).getInfo();
                p.sendAPIMessage(info, true);
            } else {
                new Thread(() -> {
                    List<AutoNC> autoNCList = this.unicacityAddon.api().sendAutoNCRequest();
                    this.unicacityAddon.api().setAutoNCList(autoNCList);

                    p.sendEmptyMessage();
                    p.sendMessage(Message.getBuilder()
                            .of("Auto Neulings-Chat:").color(ColorCode.DARK_AQUA).bold().advance()
                            .createComponent());

                    autoNCList.forEach(autoNC -> p.sendMessage(Message.getBuilder()
                            .of("»").color(ColorCode.GRAY).advance().space()
                            .of(Joiner.on(", ").join(autoNC.getWords())).color(ColorCode.AQUA)
                                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder()
                                            .of("Antwort (ID=" + autoNC.getId() + "):").color(ColorCode.GRAY).advance().newline()
                                            .of(autoNC.getAnswer()).color(ColorCode.RED).advance()
                                            .createComponent())
                                    .advance().space()
                            .createComponent()));

                    p.sendEmptyMessage();
                }).start();
            }
        }).start();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "add", "remove")
                .build();
    }
}