package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Dimiikou
 */
@UCCommand
public class DyavolCommand extends CommandBase {

    private static final Map<String, String> members = new HashMap<>();

    @Override
    @Nonnull
    public String getName() {
        return "dyavol";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/dyavol";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        Minecraft minecraft = UnicacityAddon.MINECRAFT;
        NetHandlerPlayClient connection = minecraft.getConnection();

        members.put("bf22fbfe-44d5-40c7-98be-36f57a8c2c51", ColorCode.RED.getCode() + "Spielername");
        members.put("408cdfa4-68a0-4dda-8895-e6dba076a8a2", ColorCode.RED.getCode() + "Spielername");
        members.put("b8a10ec5-faa2-473c-b1c5-4cbe56953ecd", ColorCode.RED.getCode() + "Spielername");
        members.put("6e49e42e-efca-4d93-89f9-f395b887809e", ColorCode.RED.getCode() + "Spielername");
        members.put("758571d0-723a-49a9-b506-2b74482f97de", ColorCode.RED.getCode() + "Spielername");
        members.put("01a24cc2-b89a-48ae-8ede-749abef61bca", ColorCode.RED.getCode() + "Spielername");
        members.put("fa27e0d0-b08d-47ea-95dd-aa523ee8d2ad", ColorCode.RED.getCode() + "Spielername");

        if (connection == null) return;

        p.sendMessage(Message.getBuilder()
                .of("D'yavol:").color(ColorCode.DARK_RED).bold().advance()
                .createComponent());

        connection.getPlayerInfoMap().forEach(networkPlayerInfo -> {
            UUID uuid = networkPlayerInfo.getGameProfile().getId();
            String member = members.get(uuid.toString());
            if (member == null) return;

            members.put(uuid.toString(), ColorCode.GREEN.getCode() + member.replace(ColorCode.RED.getCode(), ""));
        });

        members.values().forEach(s -> p.sendMessage(Message.getBuilder()
                .of("»").color(ColorCode.GRAY).advance().space()
                .of(s).color(s.startsWith(ColorCode.GREEN.getCode()) ? ColorCode.GREEN : ColorCode.RED).advance().space()
                .createComponent()));
    }
}