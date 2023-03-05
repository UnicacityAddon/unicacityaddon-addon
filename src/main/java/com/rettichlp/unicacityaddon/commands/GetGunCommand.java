package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.Weapon;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.models.Armament;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@UCCommand
public class GetGunCommand implements IClientCommand {

    public static Armament armament = null;

    @Override
    @Nonnull
    public String getName() {
        return "getgunpattern";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/getgunpattern (remove|create) [Name] (Waffe) (Munition)";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return Collections.singletonList("ggp");
    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return TabCompletionBuilder.getBuilder(args)
                .addAtIndex(1, "remove", "create")
                .addAtIndex(1, FileManager.DATA.getArmamentList().stream().map(Armament::getName).sorted().collect(Collectors.toList()))
                .addAtIndex(2, FileManager.DATA.getArmamentList().stream().map(Armament::getName).sorted().collect(Collectors.toList()))
                .addAtIndex(3, Arrays.stream(Weapon.values()).map(Weapon::getName).collect(Collectors.toList()))
                .build();
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (args.length == 1) {
            armament = FileManager.DATA.getArmamentList().stream()
                    .filter(a -> a.getName().equalsIgnoreCase(args[0]))
                    .findFirst()
                    .orElse(null);

            if (armament != null) {
                p.sendChatMessage("/getgun");
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
            boolean success = FileManager.DATA.removeArmamentPattern(args[1]);
            if (success) {
                p.sendInfoMessage("Vorlage gelöscht.");
            } else {
                p.sendErrorMessage("Vorlage konnte nicht gelöscht werden!");
            }
        } else if (args.length == 4 && args[0].equalsIgnoreCase("create") && MathUtils.isInteger(args[3])) {
            Weapon weapon = Weapon.getWeaponByName(args[2]);
            if (weapon != null) {
                FileManager.DATA.addArmamentPattern(args[1], weapon, Integer.parseInt(args[3]));
                p.sendInfoMessage("Vorlage hinzugefügt.");
            } else {
                p.sendErrorMessage("Vorlage konnte nicht erstellt werden!");
            }
        }
    }

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public int compareTo(@Nonnull ICommand o) {
        return 0;
    }
}