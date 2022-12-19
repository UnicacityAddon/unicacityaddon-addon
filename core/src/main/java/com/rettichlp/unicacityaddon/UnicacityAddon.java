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
import com.rettichlp.unicacityaddon.commands.ExamplePingCommand;
import com.rettichlp.unicacityaddon.listener.ExampleGameTickListener;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.models.addon.annotation.AddonListener;
import net.labymod.api.util.logging.Logging;

@Singleton
@AddonListener
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


//        UpdateUtils.modFile = e.getSourceFile();
//        asmDataTable = e.getAsmData();
        CommandRegistry.register();
        EventRegistry.register();

//        ModuleCategoryRegistry.loadCategory(ModuleRegistry.UNICACITY);
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
    protected Class<DefaultUnicacityAddonConfiguration> configurationClass() {
        return DefaultUnicacityAddonConfiguration.class;
    }

    public static boolean isUnicacity() {
        if (MINECRAFT.clientWorld() == null)
            return false;

        ServerData serverData = ADDON.labyAPI().serverController().getCurrentServerData();
        if (serverData == null)
            return false;

        return serverData.address().matches("unicacity", 25565, true);
    }
}