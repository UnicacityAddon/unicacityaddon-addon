package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
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
import java.util.concurrent.TimeUnit;

/**
 * @author Dimiikou
 * @author RettichLP
 */
@UCCommand
public class EigenbedarfCommand extends Command {

    private static List<String> scheduledTasks = new ArrayList<>();

    public EigenbedarfCommand() {
        super("eigenbedarf");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        KokainSetting kokainSetting = UnicacityAddon.ADDON.configuration().ownUseSetting().kokainSetting();
        MarihuanaSetting marihuanaSetting = UnicacityAddon.ADDON.configuration().ownUseSetting().marihuanaSetting();
        MethamphetaminSetting methamphetaminSetting = UnicacityAddon.ADDON.configuration().ownUseSetting().methamphetaminSetting();

        if (kokainSetting.enabled().get()) {
            DrugPurity drugPurity = kokainSetting.purity().getOrDefault(DrugPurity.BEST);
            int amount = kokainSetting.amount().getOrDefault(25);
            scheduledTasks.add("/dbank get " + DrugType.COCAINE.getDrugName() + " " + amount + " " + drugPurity.getPurity());
        }

        if (marihuanaSetting.enabled().get()) {
            DrugPurity drugPurity = marihuanaSetting.purity().getOrDefault(DrugPurity.GOOD);
            int amount = marihuanaSetting.amount().getOrDefault(25);
            scheduledTasks.add("/dbank get " + DrugType.MARIJUANA.getDrugName() + " " + amount + " " + drugPurity.getPurity());
        }

        if (methamphetaminSetting.enabled().get()) {
            DrugPurity drugPurity = methamphetaminSetting.purity().getOrDefault(DrugPurity.BEST);
            int amount = methamphetaminSetting.amount().getOrDefault(5);
            scheduledTasks.add("/dbank get " + DrugType.METH.getDrugName() + " " + amount + " " + drugPurity.getPurity());
        }

        new Thread(() -> scheduledTasks.forEach(s -> {
            UnicacityAddon.PLAYER.sendServerMessage(s);
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
            } catch (InterruptedException e) {
                UnicacityAddon.LOGGER.warn(e.getMessage());
            }
        })).start();

        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}