package com.rettichlp.UnicacityAddon.base.utils;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Fuzzlemann
 */
public class ForgeUtils {

    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)§[0-9A-FK-OR]");
    private static final Pattern STRIP_PREFIX_PATTERN = Pattern.compile("\\[[0-9A-Za-z]+]");

    public static List<String> getOnlinePlayers() {
        Minecraft minecraft = UnicacityAddon.MINECRAFT;
        NetHandlerPlayClient connection = minecraft.getConnection();

        if (connection == null) return Collections.emptyList();

        Collection<NetworkPlayerInfo> playerInfoList = connection.getPlayerInfoMap();

        return playerInfoList.stream()
                .map(playerInfo -> playerInfo.getGameProfile().getName())
                .map(ForgeUtils::stripColor)
                .map(ForgeUtils::stripPrefix)
                .sorted()
                .collect(Collectors.toList());
    }

    public static String stripColor(String string) {
        return STRIP_COLOR_PATTERN.matcher(string).replaceAll("");
    }

    public static String stripPrefix(String string) {
        return STRIP_PREFIX_PATTERN.matcher(string).replaceAll("");
    }
}