package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.modules.CashMoneyModule;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class EinzahlenCommand extends CommandBase {

    @Override
    @Nonnull
    public String getName() {
        return "einzahlen";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/einzahlen";
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

        if (CashMoneyModule.cashBalance > 0)
            p.sendChatMessage("/bank einzahlen " + CashMoneyModule.cashBalance);
        else
            p.sendErrorMessage("Du hast kein Geld auf der Hand!");
    }
}
