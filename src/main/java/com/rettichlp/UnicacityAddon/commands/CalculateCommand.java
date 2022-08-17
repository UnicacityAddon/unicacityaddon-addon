package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.MathUtils;
import com.rettichlp.UnicacityAddon.base.utils.TextUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class CalculateCommand extends CommandBase {

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
}