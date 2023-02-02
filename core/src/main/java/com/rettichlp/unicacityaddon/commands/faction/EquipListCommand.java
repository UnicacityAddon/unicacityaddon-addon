package com.rettichlp.unicacityaddon.commands.faction;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.models.EquipLogEntry;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.events.faction.EquipEventHandler;
import net.labymod.api.client.chat.command.Command;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Dimiikou
 */
@UCCommand
public class EquipListCommand extends Command {

    private static final String usage = "/equiplist (reset)";

    public EquipListCommand() {
        super("equiplist");
    }

    /**
     * Quote: "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]" - Minecraft Crash Report, 09.10.2022
     */
    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();
        if (arguments.length == 1 && arguments[0].equalsIgnoreCase("reset")) {
            EquipEventHandler.equipLogEntryList = new ArrayList<>();
            p.sendInfoMessage("Equipliste gelöscht.");
        } else
            equipList(p);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments)
                .addAtIndex(1, "reset")
                .build();
    }

    private void equipList(UPlayer p) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("da", "DK"));
        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("Equip:").color(ColorCode.DARK_AQUA).bold().advance()
                .createComponent());
        EquipEventHandler.equipLogEntryList.forEach(equipLogEntry -> p.sendMessage(Message.getBuilder()
                .of("» " + equipLogEntry.getAmount() + "x " + equipLogEntry.getEquip().getName() + ": ").color(ColorCode.GRAY).advance()
                .of(numberFormat.format(equipLogEntry.getPrice()) + "$").color(ColorCode.AQUA).advance()
                .createComponent()));

        int totalAmount = EquipEventHandler.equipLogEntryList.stream().map(EquipLogEntry::getPrice).reduce(0, Integer::sum);

        p.sendMessage(Message.getBuilder()
                .of("» ").color(ColorCode.GRAY).advance()
                .of(numberFormat.format(totalAmount) + "$").color(ColorCode.AQUA).bold().advance()
                .createComponent());

        p.sendEmptyMessage();
    }
}