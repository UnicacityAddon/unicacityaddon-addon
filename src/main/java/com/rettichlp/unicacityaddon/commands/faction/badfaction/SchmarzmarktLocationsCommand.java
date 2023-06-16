package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;


/**
 * @author Dimiikou
 */
@UCCommand
public class SchmarzmarktLocationsCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "schwarzmarktlocations";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/schwarzmarktlocations";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return Arrays.asList("schwarzmarktlocs", "smarktlocs");
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

        Message.getBuilder()
                .of("» ").color(ColorCode.DARK_GRAY).advance()
                .of("Positionen aller möglichen Schwarzmärkte").color(ColorCode.GRAY).advance()
                .sendTo(p.getPlayer());

        Syncer.getBlackMarketLocationList().forEach(blackMarketLocation -> {
            String naviCommand = "/navi " + blackMarketLocation.getX() + "/" + blackMarketLocation.getY() + "/" + blackMarketLocation.getZ();
            p.sendMessage(Message.getBuilder()
                    .of("» ").color(ColorCode.DARK_GRAY).advance()
                    .of(blackMarketLocation.getName()).color(ColorCode.GRAY).advance().space()
                    .of("-").color(ColorCode.DARK_GRAY).advance().space()
                    .of("Route anzeigen").color(ColorCode.RED).bold()
                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Route anzeigen").color(ColorCode.RED).advance().createComponent())
                    .clickEvent(ClickEvent.Action.RUN_COMMAND, naviCommand)
                    .advance().createComponent());
        });
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
