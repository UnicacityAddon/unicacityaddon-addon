package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.config.ownUse.setting.KokainSetting;
import com.rettichlp.unicacityaddon.base.config.ownUse.setting.MarihuanaSetting;
import com.rettichlp.unicacityaddon.base.config.ownUse.setting.MethamphetaminSetting;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugPurity;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Dimiikou
 * @author RettichLP
 */
@UCCommand
public class GiftEigenbedarfCommand extends UnicacityCommand {

    public static final List<String> scheduledTasks = new ArrayList<>();

    private static final String usage = "/gifteigenbedarf [Spieler]";

    private final UnicacityAddon unicacityAddon;

    public GiftEigenbedarfCommand(UnicacityAddon unicacityAddon) {
        super(unicacityAddon, "gifteigenbedarf", true);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
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
            scheduledTasks.add("/selldrug " + arguments[0] + " " + DrugType.COCAINE.getDrugName() + " " + drugPurity.getPurity() + " " + amount + " 0");
        }

        if (marihuanaSetting.enabled().get()) {
            DrugPurity drugPurity = marihuanaSetting.purity().getOrDefault(DrugPurity.GOOD);
            int amount = marihuanaSetting.amount().getOrDefault(25);
            scheduledTasks.add("/selldrug " + arguments[0] + " " + DrugType.MARIJUANA.getDrugName() + " " + drugPurity.getPurity() + " " + amount + " 0");
        }

        if (methamphetaminSetting.enabled().get()) {
            DrugPurity drugPurity = methamphetaminSetting.purity().getOrDefault(DrugPurity.BEST);
            int amount = methamphetaminSetting.amount().getOrDefault(5);
            scheduledTasks.add("/selldrug " + arguments[0] + " " + DrugType.METH.getDrugName() + " " + drugPurity.getPurity() + " " + amount + " 0");
        }

        new Thread(() -> scheduledTasks.forEach(s -> {
            p.sendServerMessage(s);
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
            } catch (InterruptedException e) {
                this.unicacityAddon.logger().warn(e.getMessage());
            }
        })).start();

        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}