package com.rettichlp.UnicacityAddon.commands.faction.badfaction;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * @author Dimiikou
 */
public class GiftEigenbedarfCommand extends CommandBase {

    public static boolean checkWeed = false;
    public static boolean checkMeth = false;

    @Override
    @Nonnull
    public String getName() {
        return "gifteigenbedarf";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/gifteigenbedarf [Spieler]";
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

        if (args.length < 1) {
            Message.getBuilder().error().space().of("Falscher Syntax, verwende").color(ColorCode.GRAY).advance().space()
                    .of("/gifteigenbedarf [Spieler]").color(ColorCode.RED).advance().sendTo(p.getPlayer());
            return;
        }

        if (ConfigElements.getCocainActivated()) {
            p.sendChatMessage("/selldrug " + args[0] + " Kokain " + ConfigElements.getCocainDrugPurity().getPurity() + " " + ConfigElements.getCocaineAmount() + " 0");

            if (ConfigElements.getMarihuanaActivated()) checkWeed = true;
            if (ConfigElements.getMethActivated()) checkMeth = true;
            return;
        }

        if (ConfigElements.getMarihuanaActivated()) {
            p.sendChatMessage("/selldrug " + args[0] + " Gras " + ConfigElements.getMarihuanaDrugPurity().getPurity() + " " + ConfigElements.getMarihuanaAmount() + " 0");

            if (ConfigElements.getMethActivated()) checkMeth = true;
            return;
        }

        if (ConfigElements.getMethActivated()) {
            p.sendChatMessage("/selldrug " + args[0] + " Meth " + ConfigElements.getMethDrugPurity().getPurity() + " " + ConfigElements.getMethAmount() + " 0");
        }
    }

    @Override @Nonnull public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> tabCompletions = ForgeUtils.getOnlinePlayers();
        String input = args[args.length - 1].toLowerCase();
        tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
        return tabCompletions;
    }

}
