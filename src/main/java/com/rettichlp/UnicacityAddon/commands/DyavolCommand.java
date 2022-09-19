package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
import com.rettichlp.UnicacityAddon.modules.CashMoneyModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dimiikou
 */
@UCCommand
public class DyavolCommand extends CommandBase {

    private static List<String> members = Arrays.asList("bf22fbfe-44d5-40c7-98be-36f57a8c2c51",
            "408cdfa4-68a0-4dda-8895-e6dba076a8a2",
            "b8a10ec5-faa2-473c-b1c5-4cbe56953ecd",
            "6e49e42e-efca-4d93-89f9-f395b887809e",
            "758571d0-723a-49a9-b506-2b74482f97de",
            "01a24cc2-b89a-48ae-8ede-749abef61bca",
            "fa27e0d0-b08d-47ea-95dd-aa523ee8d2ad");

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

        List<String> membersCopy = members;

        if (connection == null) return;
        connection.getPlayerInfoMap().forEach(networkPlayerInfo -> {
            boolean online = members.contains(networkPlayerInfo.getGameProfile().getId()) ? true : false;
            if (online) {
                Message.getBuilder().of(networkPlayerInfo.getGameProfile().getName()).color(ColorCode.GREEN).advance()
                        .sendTo(p.getPlayer());
                membersCopy.remove(networkPlayerInfo.getGameProfile().getId());
            }
        });

        for (int i = 0; i < membersCopy.size(); i++)
            Message.getBuilder().of("").color(ColorCode.RED).advance()
                    .sendTo(p.getPlayer());
    }

}
