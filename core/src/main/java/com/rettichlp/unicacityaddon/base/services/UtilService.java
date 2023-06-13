package com.rettichlp.unicacityaddon.base.services;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.services.utils.CommandUtils;
import com.rettichlp.unicacityaddon.base.services.utils.ListUtils;
import com.rettichlp.unicacityaddon.base.services.utils.TeamSpeakUtils;
import com.rettichlp.unicacityaddon.base.services.utils.TextUtils;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
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

public class UtilService {

    private final Icon ICON = Icon.texture(ResourceLocation.create("unicacityaddon", "textures/uc.png")).resolution(64, 64);

    public final CommandUtils commandUtils;
    private final ListUtils listUtils;
    private final TeamSpeakUtils teamSpeakUtils;
    private final TextUtils textUtils;

    private final UnicacityAddon unicacityAddon;

    public UtilService(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
        this.commandUtils = new CommandUtils(unicacityAddon);
        this.listUtils = new ListUtils();
        this.teamSpeakUtils = new TeamSpeakUtils(unicacityAddon);
        this.textUtils = new TextUtils();
    }

    public String version() {
        return "2.0.0-alpha.4";
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

    public TeamSpeakUtils teamSpeakUtils() {
        return teamSpeakUtils;
    }

    public TextUtils textUtils() {
        return textUtils;
    }

    public boolean isUnicacity() {
        if (this.unicacityAddon.labyAPI().minecraft().isIngame()) {
            ServerData serverData = this.unicacityAddon.labyAPI().serverController().getCurrentServerData();
            return serverData != null && serverData.address().matches("unicacity.de", 25565, true);
        }
        return false;
    }

    public void debug(String debugMessage) {
        if (this.unicacityAddon.configuration().debug().get()) {
            this.unicacityAddon.player().sendMessage(Message.getBuilder()
                    .of("[").color(ColorCode.DARK_GRAY).advance()
                    .of("DEBUG").color(ColorCode.YELLOW).advance()
                    .of("]").color(ColorCode.DARK_GRAY).advance().space()
                    .add(debugMessage)
                    .createComponent());
        }
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