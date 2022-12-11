package com.rettichlp.unicacityaddon.base.utils;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import org.apache.commons.lang3.SystemUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/utils/ForgeUtils.java">UCUtils by paulzhng</a>
 */
public class ForgeUtils {

    public static List<String> getOnlinePlayers() {
        Minecraft minecraft = UnicacityAddon.MINECRAFT;
        NetHandlerPlayClient connection = minecraft.getConnection();

        if (connection == null)
            return Collections.emptyList();

        Collection<NetworkPlayerInfo> playerInfoList = connection.getPlayerInfoMap();

        return playerInfoList.stream()
                .map(playerInfo -> playerInfo.getGameProfile().getName())
                .map(ForgeUtils::stripColor)
                .map(ForgeUtils::stripPrefix)
                .sorted()
                .collect(Collectors.toList());
    }

    public static String stripColor(String string) {
        return PatternHandler.STRIP_COLOR_PATTERN.matcher(string).replaceAll("");
    }

    public static String stripPrefix(String string) {
        return PatternHandler.STRIP_PREFIX_PATTERN.matcher(string).replaceAll("");
    }

    public static <T> T getMostMatching(Iterable<T> list, String input, Function<T, String> toStringFunction) {
        input = input.toLowerCase();

        int delta = Integer.MAX_VALUE;
        T found = null;
        for (T t : list) {
            String string = toStringFunction.apply(t).toLowerCase();
            if (!string.startsWith(input))
                continue;

            int curDelta = Math.abs(string.length() - input.length());
            if (curDelta < delta) {
                found = t;
                delta = curDelta;
            }

            if (curDelta == 0)
                break;
        }

        return found;
    }

    public static void shutdownPC() {
        String shutdownCommand;

        if (SystemUtils.IS_OS_AIX) {
            shutdownCommand = "shutdown -Fh now";
        } else if (SystemUtils.IS_OS_SOLARIS || SystemUtils.IS_OS_SUN_OS) {
            shutdownCommand = "shutdown -y -i5 -gnow";
        } else if (SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_UNIX) {
            shutdownCommand = "shutdown -h now";
        } else if (SystemUtils.IS_OS_HP_UX) {
            shutdownCommand = "shutdown -hy now";
        } else if (SystemUtils.IS_OS_IRIX) {
            shutdownCommand = "shutdown -y -g now";
        } else if (SystemUtils.IS_OS_WINDOWS) {
            shutdownCommand = "shutdown -s -t 0";
        } else {
            return;
        }

        try {
            Runtime.getRuntime().exec(shutdownCommand);
        } catch (IOException e) {
            //Logger.LOGGER.catchin(e); TODO
        }
    }
}