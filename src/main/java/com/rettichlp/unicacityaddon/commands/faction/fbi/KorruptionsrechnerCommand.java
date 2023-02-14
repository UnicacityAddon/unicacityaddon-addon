package com.rettichlp.unicacityaddon.commands.faction.fbi;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.ForgeUtils;
import com.rettichlp.unicacityaddon.events.faction.state.WantedEventHandler;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * @author Gelegenheitscode
 */

@UCCommand
public class KorruptionsrechnerCommand implements IClientCommand {

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public String getName() {
        return "korruptionsrechner";
    }

    @Override
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/korruptionsrechner [Spieler]";
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();
        if (args.length < 1) {
            p.sendSyntaxMessage(getUsage(sender));
            return;
        }
        String target = args[0];

        WantedEventHandler.Wanted wanted = WantedEventHandler.WANTED_MAP.get(target);
        if (wanted == null) {
            p.sendErrorMessage("Du hast /wanteds noch nicht ausgeführt!");
            return;
        }
        int Betrag;
        int Drugs;
        int MethDrugs0;
        int MethDrugs1;
        int MethDrugs2;
        Betrag = wanted.getAmount() * 150;
        Drugs = Betrag / 55;
        MethDrugs0 = Betrag / 110;
        MethDrugs1 = Betrag / 100;
        MethDrugs2 = Betrag / 50;
        p.sendMessage(Message.getBuilder()
                .of("»").color(ColorCode.GRAY).advance().space()
                .of("Korruptionspreise für").color(ColorCode.DARK_AQUA).advance().space()
                .of(target).color(ColorCode.DARK_AQUA).advance()
                .of(":").color(ColorCode.DARK_GRAY).advance().space()
                .createComponent());
        p.sendMessage(Message.getBuilder()
                .of("▶").color(ColorCode.DARK_GRAY).advance().space()
                .of("Geld").color(ColorCode.DARK_AQUA).advance()
                .of(":").color(ColorCode.DARK_GRAY).advance().space()
                .of(Betrag + "$").color(ColorCode.AQUA).advance()
                .createComponent());
        p.sendMessage(Message.getBuilder()
                .of("▶").color(ColorCode.DARK_GRAY).advance().space()
                .of("Drogen").color(ColorCode.DARK_AQUA).advance()
                .of(":").color(ColorCode.DARK_GRAY).advance().space()
                .of(Drugs + "g").color(ColorCode.AQUA).advance()
                .createComponent());
        p.sendMessage(Message.getBuilder()
                .of("▶").color(ColorCode.DARK_GRAY).advance().space()
                .of("Meth").color(ColorCode.DARK_AQUA).advance()
                .of(":").color(ColorCode.DARK_GRAY).advance().space()
                .of(MethDrugs0 + "g").color(ColorCode.AQUA).advance().space()
                .of("|").color(ColorCode.DARK_GRAY).advance().space()
                .of(MethDrugs1 + "g").color(ColorCode.AQUA).advance().space()
                .of("|").color(ColorCode.DARK_GRAY).advance().space()
                .of(MethDrugs2 + "g").color(ColorCode.AQUA).advance()
                .createComponent());

    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return TabCompletionBuilder.getBuilder(args)
                .addToAllFromIndex(2, ForgeUtils.getOnlinePlayers())
                .build();
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(@Nonnull ICommand o) {
        return 0;
    }
}
