package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.command.UCCommand;
import com.rettichlp.UnicacityAddon.base.command.UnicacityCommand;

import java.util.List;

public class TriggerEventCommand implements UnicacityCommand {

    @Override
    @UCCommand(value = "triggerEvent", usage = "/%label%\n"
            + "§aBomben-Timer:§7 bomb_placed, bomb_removed\n"
            + "§aAuto-Info:§7 car_open, car_closed\n")
    public boolean onCommand(UPlayer p, List<String> args) {
        if (args.size() < 1) return false;

        switch (args.get(0)) {
        case "bomb_placed":
            p.sendMessageAsString("News: ACHTUNG! Es wurde eine Bombe in der Nähe von NAVIPUNKT gefunden!");
            break;
        case "bomb_removed":
            p.sendMessageAsString("News: Die Bombe konnte erfolgreich entschärft werden!");
            break;
        case "car_open":
            p.sendMessageAsString("[Car] Du hast deinen AUTONAME aufgeschlossen.");
            break;
        case "car_closed":
            p.sendMessageAsString("[Car] Du hast deinen AUTONAME abgeschlossen.");
            break;
        default: return false;
        }

        return true;
    }
}