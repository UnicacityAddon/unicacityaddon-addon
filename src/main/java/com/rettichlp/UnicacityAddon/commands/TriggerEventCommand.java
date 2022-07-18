package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TriggerEventCommand extends CommandBase {

    @Override public String getName() {
        return "triggerevent";
    }

    @Override public String getUsage(ICommandSender sender) {
        return "/triggerevent [TriggerableEvent]";
    }

    @Override public List<String> getAliases() {
        return Arrays.asList("tevent");
    }

    @Override public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        UPlayer p = AbstractionLayer.getPlayer();

        if (args.length == 1) {
            TriggerableEvent triggerableEvent = TriggerableEvent.valueOf(args[0]);
            p.sendMessageAsString(triggerableEvent.getTriggerMesage());
        } else Message.getBuilder()
                .error()
                .space()
                .of("Syntax: " + getUsage(sender)).color(ColorCode.GRAY).advance()
                .sendTo(p.getPlayer());
    }

    @Override public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, Arrays.asList(TriggerableEvent.values()));
        } else {
            return Collections.emptyList();
        }
    }

    private enum TriggerableEvent {
        BOMB_PLACED("News: ACHTUNG! Es wurde eine Bombe in der Nähe von NAVIPUNKT gefunden!"),
        BOMB_REMOVED("News: Die Bombe konnte erfolgreich entschärft werden!"),
        CAR_OPEN("[Car] Du hast deinen AUTONAME aufgeschlossen."),
        CAR_CLOSED("[Car] Du hast deinen AUTONAME abgeschlossen.");

        private final String triggerMesage;

        TriggerableEvent(String triggerMesage) {
            this.triggerMesage = triggerMesage;
        }

        public String getTriggerMesage() {
            return triggerMesage;
        }
    }
}