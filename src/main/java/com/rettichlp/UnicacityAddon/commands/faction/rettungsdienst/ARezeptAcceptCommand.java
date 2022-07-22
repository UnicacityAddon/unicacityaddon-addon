package com.rettichlp.UnicacityAddon.commands.faction.rettungsdienst;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.faction.rettungsdienst.Medication;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
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
import java.util.Timer;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/e1e4cc90a852a24fbb552413eb478097f865c6f3/src/main/java/de/fuzzlemann/ucutils/commands/faction/police/ASUCommand.java">UCUtils by paulzhng</a>
 */
public class ARezeptAcceptCommand extends CommandBase {

    public static int amount = 0;

    @Override @Nonnull public String getName() {
        return "arezeptannehmen";
    }

    @Override @Nonnull public String getUsage(@Nonnull ICommandSender sender) {
        return "/arezeptannehmen [Anzahl]";
    }

    @Override @Nonnull public List<String> getAliases() {
        return Collections.singletonList("arannehmen");
    }

    @Override public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (args.length < 1) {
            Message.getBuilder()
                    .error()
                    .space()
                    .of("Syntax: " + getUsage(sender)).color(ColorCode.GRAY).advance()
                    .sendTo(p.getPlayer());
            return;
        }

        if (!MathUtils.isInteger(args[0], 10)) return;
        amount = Integer.parseInt(args[0]);

        p.acceptOffer();
    }

    @Override @Nonnull public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return Collections.emptyList();
    }
}