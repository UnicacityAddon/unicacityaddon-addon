package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
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

    private UnicacityAddon unicacityAddon;

    public GiftEigenbedarfCommand(UnicacityAddon unicacityAddon) {
        super("gifteigenbedarf");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length < 1) {
            p.sendSyntaxMessage(usage);
            return true;
        }

        KokainSetting kokainSetting = this.unicacityAddon.configuration().ownUseSetting().kokainSetting();
        MarihuanaSetting marihuanaSetting = this.unicacityAddon.configuration().ownUseSetting().marihuanaSetting();
        MethamphetaminSetting methamphetaminSetting = this.unicacityAddon.configuration().ownUseSetting().methamphetaminSetting();

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
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}