package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.location.ATM;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.NavigationUtils;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;

import java.util.List;
import java.util.Map;

/**
 * @author RettichLP
 */
@UCCommand
public class NearestATMCommand extends Command {

    public NearestATMCommand() {
        super("nearestatm", "natm");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        Map.Entry<Double, ATM> nearestATM = NavigationUtils.getNearestATM();

        UnicacityAddon.PLAYER.sendMessage(Message.getBuilder()
                .prefix()
                .of("ATM").color(ColorCode.GRAY).advance().space()
                .of(String.valueOf(nearestATM.getValue().getID())).color(ColorCode.AQUA).bold().advance().space()
                .of("ist").color(ColorCode.GRAY).advance().space()
                .of(Math.round(nearestATM.getKey()) + "m").color(ColorCode.AQUA).bold().advance().space()
                .of("entfernt.").color(ColorCode.GRAY).advance().space()
                .of("➡ Navi").color(ColorCode.RED)
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder()
                                .of("Route anzeigen").color(ColorCode.RED).advance()
                                .createComponent())
                        .clickEvent(ClickEvent.Action.RUN_COMMAND, nearestATM.getValue().getNaviCommand())
                        .advance()
                .createComponent());
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}