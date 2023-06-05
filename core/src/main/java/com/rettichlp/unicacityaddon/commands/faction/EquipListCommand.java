package com.rettichlp.unicacityaddon.commands.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * @author Dimiikou
 */
@UCCommand(prefix = "equiplist", usage = "(reset)")
public class EquipListCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public EquipListCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    /**
     * Quote: "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]" - Minecraft Crash Report, 09.10.2022
     */
    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();
        if (arguments.length == 1 && arguments[0].equalsIgnoreCase("reset")) {
            this.unicacityAddon.services().fileService().data().setEquipMap(new HashMap<>());
            p.sendInfoMessage("Equipliste gelöscht.");
        } else
            equipList(p);
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "reset")
                .build();
    }

    private void equipList(AddonPlayer p) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("da", "DK"));
        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("Equip:").color(ColorCode.DARK_AQUA).bold().advance()
                .createComponent());
        this.unicacityAddon.services().fileService().data().getEquipMap().forEach((equip, integer) -> p.sendMessage(Message.getBuilder()
                .of("» " + integer + "x " + equip.getEquipName() + ": ").color(ColorCode.GRAY).advance()
                .of(numberFormat.format(equip.getPrice(this.unicacityAddon.configuration())) + "$").color(ColorCode.AQUA).advance()
                .createComponent()));

        int totalAmount = this.unicacityAddon.services().fileService().data().getEquipMap().entrySet().stream()
                .map(equipIntegerEntry -> equipIntegerEntry.getKey().getPrice(this.unicacityAddon.configuration()) * equipIntegerEntry.getValue())
                .reduce(0, Integer::sum);

        p.sendMessage(Message.getBuilder()
                .of("» ").color(ColorCode.GRAY).advance()
                .of(numberFormat.format(totalAmount) + "$").color(ColorCode.AQUA).bold().advance()
                .createComponent());

        p.sendEmptyMessage();
    }
}