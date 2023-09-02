package com.rettichlp.unicacityaddon.base.services;

import com.google.common.reflect.ClassPath;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.services.utils.CommandUtils;
import com.rettichlp.unicacityaddon.base.services.utils.ImageUploadUtils;
import com.rettichlp.unicacityaddon.base.services.utils.ListUtils;
import com.rettichlp.unicacityaddon.base.services.utils.TextUtils;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.resources.ResourceLocation;
import org.apache.commons.lang3.SystemUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 */
@Accessors(fluent = true)
@Getter
public class UtilService {

    private final Icon icon = Icon.texture(ResourceLocation.create("unicacityaddon", "textures/icon.png")).resolution(64, 64);

    private final CommandUtils command;
    private final ImageUploadUtils imageUpload;
    private final ListUtils list;
    private final TextUtils text;

    private final UnicacityAddon unicacityAddon;

    public UtilService(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
        this.command = new CommandUtils(unicacityAddon);
        this.imageUpload = new ImageUploadUtils();
        this.list = new ListUtils();
        this.text = new TextUtils();
    }

    @SuppressWarnings("SameReturnValue")
    public String version() {
        return "2.4.0-dev";
    }

    public boolean isUnicacity() {
        if (Laby.labyAPI().minecraft().isIngame()) {
            ServerData serverData = Laby.labyAPI().serverController().getCurrentServerData();
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
        ClientPacketListener clientPacketListener = Laby.labyAPI().minecraft().getClientPacketListener();
        if (clientPacketListener == null)
            return Collections.emptyList();

        Collection<NetworkPlayerInfo> networkPlayerInfoCollection = clientPacketListener.getNetworkPlayerInfos();

        return networkPlayerInfoCollection.stream()
                .map(networkPlayerInfo -> networkPlayerInfo.profile().getUsername())
                .map(this.text::stripColor)
                .map(this.text::stripPrefix)
                .sorted()
                .collect(Collectors.toList());
    }

    public Set<Class<?>> getAllClassesFromPackage(String packageName) {
        try {
            return ClassPath.from(UnicacityAddon.class.getClassLoader())
                    .getTopLevelClassesRecursive(packageName)
                    .stream()
                    .map(ClassPath.ClassInfo::load)
                    .collect(Collectors.toSet());
        } catch (IOException exception) {
            this.unicacityAddon.logger().error(exception.getMessage());
        }
        return new HashSet<>();
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

    /**
     * Replaces the addon api token with "TOKEN"
     *
     * @param message Message which needs to be checked
     * @return Message without addon api token
     */
    public String messageWithHiddenToken(String message) {
        return Optional.ofNullable(this.unicacityAddon.api().getToken())
                .map(s -> message.replace(s, "TOKEN"))
                .orElse("Message cannot be displayed because it contains a token.");
    }
}
