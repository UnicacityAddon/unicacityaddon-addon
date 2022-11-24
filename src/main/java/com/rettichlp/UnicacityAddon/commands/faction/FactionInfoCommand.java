package com.rettichlp.UnicacityAddon.commands.faction;

import com.google.common.util.concurrent.Uninterruptibles;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.api.request.TabCompletionBuilder;
import com.rettichlp.UnicacityAddon.base.faction.Faction;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.events.faction.FactionInfoEventHandler;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/faction/CheckActiveMembersCommand.java">UCUtils by paulzhng</a>
 */
@UCCommand
public class FactionInfoCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "checkactivemembers";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/checkactivemembers [Fraktion]";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return Collections.singletonList("cam");
    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();
        List<Faction> chosenFactions = new ArrayList<>();

        for (String s : args) {
            Faction faction = Faction.getFactionByFactionKey(s);
            if (faction == null) continue;
            chosenFactions.add(faction);
        }

        if (chosenFactions.isEmpty()) chosenFactions.addAll(Arrays.asList(Faction.values()));

        p.sendMessage(Message.getBuilder().of("Aktive Spieler in den Fraktionen:").color(ColorCode.DARK_AQUA).bold().advance().createComponent());

        Thread thread = new Thread(() -> chosenFactions.forEach(faction -> {
            if (faction.equals(Faction.NULL)) return;
            Map<Boolean, Integer> members = getMembers(faction);
            int activeMembers = members.get(true);
            int inactiveMembers = members.get(false);
            p.sendMessage(Message.getBuilder()
                    .of("  » ").color(ColorCode.GRAY).advance()
                    .of(faction.getDisplayName())
                    .color(ColorCode.AQUA)
                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder()
                            .of("/memberinfo " + faction.getFactionKey()).color(ColorCode.RED).advance()
                            .createComponent())
                    .clickEvent(ClickEvent.Action.RUN_COMMAND, "/memberinfo " + faction.getFactionKey())
                    .advance()
                    .space()
                    .of(String.valueOf(activeMembers)).color(ColorCode.GREEN).advance()
                    .of("/").color(ColorCode.DARK_GRAY).advance()
                    .of(String.valueOf(activeMembers + inactiveMembers)).color(ColorCode.GRAY).advance()
                    .createComponent());
        }));
        thread.start();
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return TabCompletionBuilder.getBuilder(args)
                .addAtIndex(1, Arrays.stream(Faction.values()).map(Faction::getFactionKey).sorted().collect(Collectors.toList()))
                .build();
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
    }

    private Map<Boolean, Integer> getMembers(Faction faction) {
        FactionInfoEventHandler.future = new CompletableFuture<>();
        AbstractionLayer.getPlayer().sendChatMessage("/memberinfo " + faction.getFactionKey());

        try {
            return Uninterruptibles.getUninterruptibly(FactionInfoEventHandler.future);
        } catch (ExecutionException e) {
            throw new IllegalStateException(e);
        } finally {
            FactionInfoEventHandler.future = null;
        }
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