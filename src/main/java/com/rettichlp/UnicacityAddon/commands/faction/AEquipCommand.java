package com.rettichlp.UnicacityAddon.commands.faction;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.utils.MathUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class AEquipCommand extends CommandBase {

    public static int amount = 0;

    @Override @Nonnull public String getName() {
        return "aequip";
    }

    @Override @Nonnull public String getUsage(@Nonnull ICommandSender sender) {
        return "/aequip [Menge]";
    }

    @Override @Nonnull public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (args.length > 0) {
            if (!MathUtils.isInteger(args[0])) {
                p.sendSyntaxMessage(getUsage(sender));
                return;
            }
            amount = Integer.parseInt(args[0]);
        }

        p.sendInfoMessage("Menge für AEquip wurde eingestellt.");
    }
}