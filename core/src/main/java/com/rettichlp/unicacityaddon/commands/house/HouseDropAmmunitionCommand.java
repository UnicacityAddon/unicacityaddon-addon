package com.rettichlp.unicacityaddon.commands.house;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.Weapon;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.services.utils.MathUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * @author Kifo
 * @author RettichLP
 */
@UCCommand(prefix = "adropammo", usage = "[Waffe] [Menge]")
public class HouseDropAmmunitionCommand extends UnicacityCommand {

    public static int dropAmount = 0;

    private final UnicacityAddon unicacityAddon;

    public HouseDropAmmunitionCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(@NotNull String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length != 2 || !MathUtils.isInteger(arguments[1])) {
            sendUsage();
            return true;
        }

        Weapon weapon = Weapon.getWeaponByName(arguments[0]);
        dropAmount = Integer.parseInt(arguments[1]);

        if (weapon == null || dropAmount <= 0) {
            sendUsage();
            return true;
        }

        p.sendServerMessage("/dropammo " + weapon.getName() + " " + Math.min(dropAmount, 350));
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, Arrays.stream(Weapon.values()).map(Weapon::getName).toList())
                .build();
    }
}