package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.modules.BankMoneyModule;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class ReichensteuerCommand extends CommandBase {

    @Override
    @Nonnull
    public String getName() {
        return "reichensteuer";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/reichensteuer";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (BankMoneyModule.bankBalance > 100000)
            p.sendChatMessage("/bank abbuchen " + (BankMoneyModule.bankBalance - 100000));
        else
            p.sendErrorMessage("Dein Kontostand beträgt bereits 100.000$!");
    }
}
