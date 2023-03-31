package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.Weapon;
import com.rettichlp.unicacityaddon.base.models.Armament;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import net.labymod.api.client.chat.command.Command;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand
public class GetGunCommand extends Command {

    public static Armament armament = null;

    private static final String usage = "/getgunpattern (remove|create) [Name] (Waffe) (Munition)";

    private UnicacityAddon unicacityAddon;

    public GetGunCommand(UnicacityAddon unicacityAddon) {
        super("getgunpattern", "ggp");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length == 1) {
            armament = this.unicacityAddon.data().getArmamentList().stream()
                    .filter(a -> a.getName().equalsIgnoreCase(arguments[0]))
                    .findFirst()
                    .orElse(null);

            if (armament != null) {
                p.sendServerMessage("/getgun");
            }
        } else if (arguments.length == 2 && arguments[0].equalsIgnoreCase("remove")) {
            boolean success = this.unicacityAddon.data().removeArmamentPattern(arguments[1]);
            if (success) {
                p.sendInfoMessage("Vorlage gelöscht.");
            } else {
                p.sendErrorMessage("Vorlage konnte nicht gelöscht werden!");
            }
        } else if (arguments.length == 4 && arguments[0].equalsIgnoreCase("create") && MathUtils.isInteger(arguments[3])) {
            Weapon weapon = Weapon.getWeaponByName(arguments[2]);
            if (weapon != null) {
                this.unicacityAddon.data().addArmamentPattern(arguments[1], weapon, Integer.parseInt(arguments[3]));
                p.sendInfoMessage("Vorlage hinzugefügt.");
            } else {
                p.sendErrorMessage("Vorlage konnte nicht erstellt werden!");
            }
        }
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "remove", "create")
                .addAtIndex(1, this.unicacityAddon.data().getArmamentList().stream().map(Armament::getName).sorted().collect(Collectors.toList()))
                .addAtIndex(2, this.unicacityAddon.data().getArmamentList().stream().map(Armament::getName).sorted().collect(Collectors.toList()))
                .addAtIndex(3, Arrays.stream(Weapon.values()).map(Weapon::getName).collect(Collectors.toList()))
                .build();
    }
}