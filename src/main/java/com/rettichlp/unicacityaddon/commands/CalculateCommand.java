package com.rettichlp.unicacityaddon.commands;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class CalculateCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "calculate";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/calculate [mathematischer Ausdruck]";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return Arrays.asList("calc", "rechner");
    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return TabCompletionBuilder.getBuilder(args).build();
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();
        if (args.length < 1) {
            p.sendSyntaxMessage(getUsage(sender));
            return;
        }

        String mathString = TextUtils.makeStringByArgs(args, " ");
        MathUtils mathUtils = new MathUtils(mathString);
        try {
            mathUtils.evaluate();
        } catch (MathUtils.ExpressionException e) {
            p.sendErrorMessage("Der eingegebene mathematische Ausdruck konnte nicht evaluiert werden: " + e.getMessage());
        }

        p.sendMessage(Message.getBuilder()
                .prefix()
                .of(mathString).color(ColorCode.AQUA).advance().space()
                .of("=").color(ColorCode.WHITE).advance().space()
                .of(mathUtils.parse()).color(ColorCode.AQUA).advance()
                .createComponent());
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