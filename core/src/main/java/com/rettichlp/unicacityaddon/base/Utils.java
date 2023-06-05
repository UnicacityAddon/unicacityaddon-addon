package com.rettichlp.unicacityaddon.base;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.teamspeak.TSUtils;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.CommandUtils;
import com.rettichlp.unicacityaddon.base.utils.ListUtils;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.resources.ResourceLocation;
import org.apache.commons.lang3.SystemUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    private final String VERSION = "2.0.0-alpha.4";
    private final Icon ICON = Icon.texture(ResourceLocation.create("unicacityaddon", "textures/uc.png")).resolution(64, 64);

    public final CommandUtils commandUtils;
    private final ListUtils listUtils;
    private final TextUtils textUtils;
    private final TSUtils tsUtils;

    private final UnicacityAddon unicacityAddon;

    public Utils(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
        this.commandUtils = new CommandUtils(unicacityAddon);
        this.listUtils = new ListUtils();
        this.textUtils = new TextUtils();
        this.tsUtils = new TSUtils(unicacityAddon);
    }

    public String version() {
        return VERSION;
    }

    public Icon icon() {
        return ICON;
    }

    public CommandUtils commandUtils() {
        return commandUtils;
    }

    public ListUtils listUtils() {
        return listUtils;
    }

    public TextUtils textUtils() {
        return textUtils;
    }

    public TSUtils tsUtils() {
        return tsUtils;
    }

    public boolean isUnicacity() {
        if (this.unicacityAddon.labyAPI().minecraft().isIngame()) {
            ServerData serverData = this.unicacityAddon.labyAPI().serverController().getCurrentServerData();
            return serverData != null && serverData.address().matches("unicacity.de", 25565, true);
        }
        return false;
    }

    public void debug(String debugMessage) {
        this.unicacityAddon.player().sendMessage(Message.getBuilder()
                .of("[").color(ColorCode.DARK_GRAY).advance()
                .of("DEBUG").color(ColorCode.YELLOW).advance()
                .of("]").color(ColorCode.DARK_GRAY).advance().space()
                .add(debugMessage)
                .createComponent());
    }

    public List<String> getOnlinePlayers() {
        ClientPacketListener clientPacketListener = this.unicacityAddon.labyAPI().minecraft().getClientPacketListener();
        if (clientPacketListener == null)
            return Collections.emptyList();

        Collection<NetworkPlayerInfo> networkPlayerInfoCollection = clientPacketListener.getNetworkPlayerInfos();

        return networkPlayerInfoCollection.stream()
                .map(networkPlayerInfo -> networkPlayerInfo.profile().getUsername())
                .map(this.textUtils::stripColor)
                .map(this.textUtils::stripPrefix)
                .sorted()
                .collect(Collectors.toList());
    }

    public void shutdownPC() {
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
            this.unicacityAddon.logger().warn(e.getMessage());
        }
    }
}
