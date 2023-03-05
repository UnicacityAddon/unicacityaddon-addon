package com.rettichlp.unicacityaddon.commands.faction.badfaction;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
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
import java.util.Map;


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

        for (Map.Entry<String, String> blackMarket : BLACK_MARKET_LIST) {
            String coords = blackMarket.getValue();

            p.sendMessage(Message.getBuilder()
                    .of("» ").color(ColorCode.DARK_GRAY).advance()
                    .of(blackMarket.getKey()).color(ColorCode.GRAY).advance().space()
                    .of("-").color(ColorCode.DARK_GRAY).advance().space()
                    .of("Route anzeigen").color(ColorCode.RED).bold()
                    .clickEvent(ClickEvent.Action.RUN_COMMAND, "/navi " + coords)
                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of(coords).color(ColorCode.GRAY).advance().createComponent())
                    .advance().createComponent());

        }
    }

    private static final List<Map.Entry<String, String>> BLACK_MARKET_LIST = Lists.newArrayList(
            Maps.immutableEntry("Psychiatrie", "1689/66/-390"),
            Maps.immutableEntry("Hafen (Chinatown)", "1172/69/-464"),
            Maps.immutableEntry("Haus 472 (Chinatown)", "1205/69/-118"),
            Maps.immutableEntry("Mex U-Bahn", "-92/52/-33"),
            Maps.immutableEntry("Kino (Ruine)", "743/69/315"),
            Maps.immutableEntry("Fußballplatz (Gang)", "-468/69/425"),
            Maps.immutableEntry("SH Park (Höhle)", "64/67/347"),
            Maps.immutableEntry("Alcatraz", "1154/83/695"),
            Maps.immutableEntry("Flughafen Las Unicas", "1694/69/557"),
            Maps.immutableEntry("Shishabar", "-136/74/-74"),
            Maps.immutableEntry("Freibad", "-269/69/-521")
    );

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public int compareTo(@Nonnull ICommand o) {
        return 0;
    }
}