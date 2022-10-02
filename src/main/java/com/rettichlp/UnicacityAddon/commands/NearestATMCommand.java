package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.location.ATM;
import com.rettichlp.UnicacityAddon.base.location.NavigationUtils;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author RettichLP
 */
@UCCommand
public class NearestATMCommand implements IClientCommand {

    @Override @Nonnull public String getName() {
        return "nearestatm";
    }

    @Override @Nonnull public String getUsage(@Nonnull ICommandSender sender) {
        return "/nearestatm";
    }

    @Override @Nonnull public List<String> getAliases() {
        return Collections.singletonList("natm");
    }

    @Override public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return ForgeUtils.getOnlinePlayers();
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
    }

    @Override public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        Map.Entry<Double, ATM> nearestATM = NavigationUtils.getNearestATM();

        AbstractionLayer.getPlayer().sendMessage(Message.getBuilder()
                .prefix()
                .of("ATM").color(ColorCode.GRAY).advance()
                .space()
                .of(String.valueOf(nearestATM.getValue().getID())).color(ColorCode.AQUA).bold().advance()
                .space()
                .of("ist").color(ColorCode.GRAY).advance()
                .space()
                .of(Math.round(nearestATM.getKey()) + "m").color(ColorCode.AQUA).bold().advance()
                .space()
                .of("entfernt.").color(ColorCode.GRAY).advance()
                .space()
                .of("➡ Navi")
                    .color(ColorCode.RED)
                    .clickEvent(ClickEvent.Action.RUN_COMMAND, nearestATM.getValue().getNaviCommand())
                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder()
                            .of("Route anzeigen").color(ColorCode.RED).advance()
                            .createComponent())
                    .advance()
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