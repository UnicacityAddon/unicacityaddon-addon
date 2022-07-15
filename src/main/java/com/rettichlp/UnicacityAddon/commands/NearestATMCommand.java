package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.command.UCCommand;
import com.rettichlp.UnicacityAddon.base.command.UnicacityCommand;
import com.rettichlp.UnicacityAddon.base.location.ATM;
import com.rettichlp.UnicacityAddon.base.location.NavigationUtils;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import net.minecraft.util.text.event.ClickEvent;

import java.util.List;
import java.util.Map;

public class NearestATMCommand implements UnicacityCommand {

    @Override
    @UCCommand(value = "nearestatm", usage = "/%label%")
    public boolean onCommand(UPlayer p, List<String> args) {
        Map.Entry<Double, ATM> nearestATM = NavigationUtils.getNearestATM();

        p.sendMessage(Message.getBuilder()
                .prefix()
                .of("ATM").color(ColorCode.GRAY).advance()
                .space()
                .of(String.valueOf(nearestATM.getValue().getID())).color(ColorCode.AQUA).bold().advance()
                .space()
                .of("ist").color(ColorCode.GRAY).advance()
                .space()
                .of(Math.round(nearestATM.getKey()) + "m").color(ColorCode.AQUA).bold().advance()
                .space()
                .of("entfernt.").color(ColorCode.GRAY).advance()
                .space()
                .of("➡ Navi").color(ColorCode.RED).clickEvent(ClickEvent.Action.RUN_COMMAND, nearestATM.getValue().getNaviCommand()).advance()
                .createComponent());

        return true;
    }
}