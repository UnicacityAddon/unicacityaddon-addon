package com.rettichlp.unicacityaddon.base.utils;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.client.network.NetworkPlayerInfo;

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
        ClientPacketListener clientPacketListener = UnicacityAddon.ADDON.labyAPI().minecraft().getClientPacketListener();
        if (clientPacketListener == null)
            return Collections.emptyList();

        Collection<NetworkPlayerInfo> networkPlayerInfoCollection = clientPacketListener.getNetworkPlayerInfos();

        return networkPlayerInfoCollection.stream()
                .map(networkPlayerInfo -> networkPlayerInfo.profile().getUsername())
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
//        String shutdownCommand;
//
//        FileManager.saveData();
//
//        if (SystemUtils.IS_OS_AIX) {
//            shutdownCommand = "shutdown -Fh now";
//        } else if (SystemUtils.IS_OS_SOLARIS || SystemUtils.IS_OS_SUN_OS) {
//            shutdownCommand = "shutdown -y -i5 -gnow";
//        } else if (SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_UNIX) {
//            shutdownCommand = "shutdown -h now";
//        } else if (SystemUtils.IS_OS_HP_UX) {
//            shutdownCommand = "shutdown -hy now";
//        } else if (SystemUtils.IS_OS_IRIX) {
//            shutdownCommand = "shutdown -y -g now";
//        } else if (SystemUtils.IS_OS_WINDOWS) {
//            shutdownCommand = "shutdown -s -t 0";
//        } else {
//            return;
//        }
//
//        try {
//            Runtime.getRuntime().exec(shutdownCommand);
//        } catch (IOException e) {
//            UnicacityAddon.LOGGER.throwing(e);
//        }
    }
}