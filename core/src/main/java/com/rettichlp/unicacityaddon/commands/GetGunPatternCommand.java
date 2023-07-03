package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.Weapon;
import com.rettichlp.unicacityaddon.base.io.file.Armament;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.services.utils.MathUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand(prefix = "getgunpattern", aliases = {"ggp"}, usage = "(create|remove) [Name] (Waffe) (Munition)")
public class GetGunPatternCommand extends UnicacityCommand {

    public static Armament armament = null;
    public static long startTime = 0;

    private final UnicacityAddon unicacityAddon;

    public GetGunPatternCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length == 1) {
            armament = this.unicacityAddon.fileService().data().getArmamentList().stream()
                    .filter(a -> a.getName().equalsIgnoreCase(arguments[0]))
                    .findFirst()
                    .orElse(null);

            if (armament != null) {
                startTime = System.currentTimeMillis();
                p.sendServerMessage("/getgun");
            }
        } else if (arguments.length == 2 && arguments[0].equalsIgnoreCase("remove")) {
            boolean success = this.unicacityAddon.fileService().data().removeArmamentPattern(arguments[1]);
            if (success) {
                p.sendInfoMessage("Vorlage gelöscht.");
            } else {
                p.sendErrorMessage("Vorlage konnte nicht gelöscht werden!");
            }
        } else if (arguments.length == 4 && arguments[0].equalsIgnoreCase("create") && MathUtils.isInteger(arguments[3])) {
            Weapon weapon = Weapon.getWeaponByName(arguments[2]);
            if (weapon != null) {
                this.unicacityAddon.fileService().data().addArmamentPattern(arguments[1], weapon, Integer.parseInt(arguments[3]));
                p.sendInfoMessage("Vorlage hinzugefügt.");
            } else {
                p.sendErrorMessage("Vorlage konnte nicht erstellt werden!");
            }
        } else {
            sendUsage();
        }
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "remove", "create")
                .addAtIndex(1, this.unicacityAddon.fileService().data().getArmamentList().stream().map(Armament::getName).sorted().collect(Collectors.toList()))
                .addAtIndex(2, this.unicacityAddon.fileService().data().getArmamentList().stream().map(Armament::getName).sorted().collect(Collectors.toList()))
                .addAtIndex(3, Arrays.stream(Weapon.values()).map(Weapon::getName).collect(Collectors.toList()))
                .build();
    }
}