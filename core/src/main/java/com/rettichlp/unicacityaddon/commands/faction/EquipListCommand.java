package com.rettichlp.unicacityaddon.commands.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.labymod.api.client.chat.command.Command;

import java.text.NumberFormat;
import java.util.HashMap;
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
        AddonPlayer p = UnicacityAddon.PLAYER;
        if (arguments.length == 1 && arguments[0].equalsIgnoreCase("reset")) {
            FileManager.DATA.setEquipMap(new HashMap<>());
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

    private void equipList(AddonPlayer p) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("da", "DK"));
        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("Equip:").color(ColorCode.DARK_AQUA).bold().advance()
                .createComponent());
        FileManager.DATA.getEquipMap().forEach((equip, integer) -> p.sendMessage(Message.getBuilder()
                .of("» " + integer + "x " + equip.getName() + ": ").color(ColorCode.GRAY).advance()
                .of(numberFormat.format(equip.getPrice()) + "$").color(ColorCode.AQUA).advance()
                .createComponent()));

        int totalAmount = FileManager.DATA.getEquipMap().entrySet().stream()
                .map(equipIntegerEntry -> equipIntegerEntry.getKey().getPrice() * equipIntegerEntry.getValue())
                .reduce(0, Integer::sum);

        p.sendMessage(Message.getBuilder()
                .of("» ").color(ColorCode.GRAY).advance()
                .of(numberFormat.format(totalAmount) + "$").color(ColorCode.AQUA).bold().advance()
                .createComponent());

        p.sendEmptyMessage();
    }
}