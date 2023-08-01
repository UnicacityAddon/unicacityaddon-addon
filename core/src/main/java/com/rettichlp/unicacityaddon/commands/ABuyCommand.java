package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.services.utils.MathUtils;
import com.rettichlp.unicacityaddon.listener.EquipShopListener;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "abuy", aliases = {"aequip"}, usage = "[Menge] (Delay, mind. 150ms)")
public class ABuyCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public ABuyCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length < 1 || !MathUtils.isInteger(arguments[0])) {
            sendUsage();
            return true;
        }

        if (arguments.length >= 2 && !MathUtils.isInteger(arguments[0]) && !MathUtils.isInteger(arguments[1])) {
            sendUsage();
            return true;
        }

        int amount = Integer.parseInt(arguments[0]);
        if (this.getPrefix().contains("buy")) {
            EquipShopListener.aBuyAmount = amount;
        } else {
            EquipShopListener.aEquipAmount = amount;
        }

        EquipShopListener.period = arguments.length == 2 ? Math.max(Integer.parseInt(arguments[1]), 150) : 150;

        p.sendInfoMessage("Menge für /" + this.getPrefix() + " auf " + amount + " und Delay auf " + EquipShopListener.period + "ms eingestellt.");

        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}