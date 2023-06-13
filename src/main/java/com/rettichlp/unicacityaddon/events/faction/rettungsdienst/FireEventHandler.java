package com.rettichlp.unicacityaddon.events.faction.rettungsdienst;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author RettichLP
 */
@Deprecated // deactivated
public class FireEventHandler {

    /**
     * Quote: "Meine Öffi-Nachricht geht nicht... oh... ich habe den Imgur-Link eingefügt..." - [UC]laaurin_, 02.10.2022
     */
    @SubscribeEvent
    public void onClientChatReceive(ClientChatReceivedEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        String msg = e.getMessage().getUnformattedText();

        if (!msg.equals("Fehler: Es brennt noch!"))
            return;

        World world = UnicacityAddon.MINECRAFT.world;
        BlockPos pos = p.getPosition();
        BlockPos blockPos1 = new BlockPos(pos.getX() - 30, pos.getY() - 30, pos.getZ() - 30);
        BlockPos blockPos2 = new BlockPos(pos.getX() + 30, pos.getY() + 30, pos.getZ() + 30);

        List<BlockPos> fireBlocks = StreamSupport.stream(BlockPos.getAllInBox(blockPos1, blockPos2).spliterator(), false)
                .collect(Collectors.toList())
                .stream()
                .filter(blockPos -> world.getBlockState(blockPos).getMaterial().equals(Material.FIRE))
                .collect(Collectors.toList());

        if (fireBlocks.isEmpty())
            return;

        p.sendMessage(Message.getBuilder()
                .of("Feuer in der Nähe:").color(ColorCode.DARK_RED).advance()
                .createComponent());

        fireBlocks.forEach(blockPos -> p.sendMessage(Message.getBuilder()
                .of("»").color(ColorCode.GRAY).advance().space()
                .of("X: " + blockPos.getX() + " | Y: " + blockPos.getY() + " | Z: " + blockPos.getZ()).color(ColorCode.RED).advance().space()
                .of("[➤]").color(ColorCode.GREEN)
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Route anzeigen").color(ColorCode.RED).advance().createComponent())
                        .clickEvent(ClickEvent.Action.RUN_COMMAND, "/navi " + blockPos.getX() + "/" + blockPos.getY() + "/" + blockPos.getZ())
                .advance()
                .createComponent()));
    }
}