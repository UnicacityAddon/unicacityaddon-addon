package com.rettichlp.unicacityaddon;

import com.google.inject.Singleton;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.api.TokenManager;
import com.rettichlp.unicacityaddon.base.api.checks.BroadcastChecker;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.CommandRegistry;
import com.rettichlp.unicacityaddon.base.registry.EventRegistry;
import com.rettichlp.unicacityaddon.base.registry.KeyBindRegistry;
import com.rettichlp.unicacityaddon.base.registry.ModuleRegistry;
import com.rettichlp.unicacityaddon.base.teamspeak.TSClientQuery;
import com.rettichlp.unicacityaddon.base.utils.UpdateUtils;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.Minecraft;
import net.labymod.api.loader.LabyModLoader;
import net.labymod.api.models.addon.annotation.AddonListener;
import com.rettichlp.unicacityaddon.commands.ExamplePingCommand;
import com.rettichlp.unicacityaddon.listener.ExampleGameTickListener;
import net.labymod.api.models.version.Version;
import net.labymod.api.util.logging.Logging;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

@Singleton
@AddonListener
@Mod(name = "UnicacityAddon", modid = "unicacityaddon", version = UnicacityAddon.VERSION, clientSideOnly = true, acceptedMinecraftVersions = "[1.12,1.12.2]")
public class UnicacityAddon extends LabyAddon<ExampleConfiguration> {

    public static final String VERSION = "2.0.0-dev";
    public static Minecraft MINECRAFT;
    public static UnicacityAddon ADDON;
    public static Logging LOGGER;

    @Override
    protected void enable() {
        MINECRAFT = labyAPI().minecraft();
        ADDON = this;
        LOGGER = this.logger();


        UpdateUtils.modFile = e.getSourceFile();
        asmDataTable = e.getAsmData();
        CommandRegistry.register();
        EventRegistry.register();

        ModuleCategoryRegistry.loadCategory(ModuleRegistry.UNICACITY);
        ModuleRegistry.register();

        this.registerListener(ExampleGameTickListener.class);
        this.registerCommand(ExamplePingCommand.class);

        BroadcastChecker.start();
        LOGGER.info("Started BroadcastChecker");
        TokenManager.createToken();
        LOGGER.info("Created API Token");
        Syncer.syncAll();

        new Thread(TSClientQuery::getInstance).start();
        LOGGER.info("Started TSClientQuery");

        KeyBindRegistry.registerKeyBinds();
        FileManager.loadData();



        LOGGER.info("Enabled the Addon");
    }

    @Override
    protected Class<ExampleConfiguration> configurationClass() {
        return ExampleConfiguration.class;
    }

//    TODO: 08.12.2022 Verify unicacity server
//    public static boolean isUnicacity() {
//        if (MINECRAFT.clientWorld() == null) return false;
//
//        ServerData serverData = MINECRAFT.clientWorld().n   .getNetworkPlayerInfo("").getMinecraftInfo().   MINECRAFT. MINECRAFT.clientPacketListener().getNetworkPlayerInfo() .ip .getCurrentServerData();
//        if (serverData == null) return false;
//
//        String ip = serverData.serverIP;
//        if (ip.contains(":")) ip = ip.split(":")[0]; // strip unused port
//
//        return ip.toLowerCase().endsWith("unicacity.de");
//    }
}