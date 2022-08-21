package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.aktien.Aktie;
import com.rettichlp.UnicacityAddon.base.aktien.AktienManager;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.MathUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

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
public class AktienCommand extends CommandBase {

    @Override
    @Nonnull
    public String getName() {
        return "aktien";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/aktien [Aktie] [aktueller Wert]";
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

        if (args.length < 2) {
            p.sendSyntaxMessage(getUsage(sender));
            return;
        }

        Aktie aktie = Arrays.stream(Aktie.values()).filter(a -> a.getTabName().equalsIgnoreCase(args[0])).findFirst().orElse(null);
        if (aktie == null || !MathUtils.isInteger(args[1])) {
            p.sendSyntaxMessage(getUsage(sender));
            return;
        }

        AktienManager aktienManager = new AktienManager();

        int currentStockValue = aktienManager.calculateStockValue(aktie, Integer.parseInt(args[1]));
        int currentWin = aktienManager.calculateWin(aktie, Integer.parseInt(args[1]));
        int buyValue = aktienManager.getBuyValue(aktie);

        p.sendMessage(Message.getBuilder()
                .of("Deine Aktien: ").color(ColorCode.DARK_AQUA).bold().advance()
                .of(aktie.getItemName()).color(ColorCode.DARK_AQUA).advance()
                .createComponent());

        p.sendMessage(Message.getBuilder()
                .space().space()
                .of("»").color(ColorCode.DARK_GRAY).advance().space()
                .of("Kaufwert:").color(ColorCode.AQUA).advance().space()
                .of(buyValue + "$").color(ColorCode.GREEN).advance()
                .createComponent());

        p.sendMessage(Message.getBuilder()
                .space().space()
                .of("»").color(ColorCode.DARK_GRAY).advance().space()
                .of("aktueller Wert:").color(ColorCode.AQUA).advance().space()
                .of(currentStockValue + "$").color(ColorCode.GREEN).advance()
                .createComponent());

        p.sendMessage(Message.getBuilder()
                .space().space()
                .of("»").color(ColorCode.DARK_GRAY).advance().space()
                .of(currentWin < 0 ? "Verlust:" : "Gewinn:").color(ColorCode.AQUA).advance().space()
                .of(currentWin + "$").color(currentWin < 0 ? ColorCode.RED : ColorCode.GREEN).advance()
                .createComponent());

    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> tabCompletions = Arrays.stream(Aktie.values()).map(Aktie::getTabName).collect(Collectors.toList());
        String input = args[args.length - 1].toLowerCase().replace('-', ' ');
        tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
        return tabCompletions;
    }
}