package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.config.ownUse.setting.KokainSetting;
import com.rettichlp.unicacityaddon.base.config.ownUse.setting.MarihuanaSetting;
import com.rettichlp.unicacityaddon.base.config.ownUse.setting.MethamphetaminSetting;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugPurity;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import net.labymod.api.client.chat.command.Command;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dimiikou
 * @author RettichLP
 */
@UCCommand
public class GiftEigenbedarfCommand extends Command {

    public static List<String> scheduledTasks = new ArrayList<>();

    private static final String usage = "/gifteigenbedarf [Spieler]";

    public GiftEigenbedarfCommand() {
        super("gifteigenbedarf");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (arguments.length < 1) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        KokainSetting kokainSetting = UnicacityAddon.configuration.ownUseSetting().kokainSetting();
        MarihuanaSetting marihuanaSetting = UnicacityAddon.configuration.ownUseSetting().marihuanaSetting();
        MethamphetaminSetting methamphetaminSetting = UnicacityAddon.configuration.ownUseSetting().methamphetaminSetting();

        if (kokainSetting.enabled().get()) {
            DrugPurity drugPurity = kokainSetting.purity().getOrDefault(DrugPurity.BEST);
            int amount = kokainSetting.amount().getOrDefault(25);
            scheduledTasks.add("/selldrug " + arguments[0] + " " + DrugType.COCAINE.getDrugName() + " " + amount + " " + drugPurity.getPurity());
        }

        if (marihuanaSetting.enabled().get()) {
            DrugPurity drugPurity = marihuanaSetting.purity().getOrDefault(DrugPurity.GOOD);
            int amount = marihuanaSetting.amount().getOrDefault(25);
            scheduledTasks.add("/selldrug " + arguments[0] + " " + DrugType.MARIJUANA.getDrugName() + " " + amount + " " + drugPurity.getPurity());
        }

        if (methamphetaminSetting.enabled().get()) {
            DrugPurity drugPurity = methamphetaminSetting.purity().getOrDefault(DrugPurity.BEST);
            int amount = methamphetaminSetting.amount().getOrDefault(5);
            scheduledTasks.add("/selldrug " + arguments[0] + " " + DrugType.METH.getDrugName() + " " + amount + " " + drugPurity.getPurity());
        }
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}