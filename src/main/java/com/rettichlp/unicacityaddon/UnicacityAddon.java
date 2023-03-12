package com.rettichlp.unicacityaddon;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.api.TokenManager;
import com.rettichlp.unicacityaddon.base.config.Config;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.CommandRegistry;
import com.rettichlp.unicacityaddon.base.registry.EventRegistry;
import com.rettichlp.unicacityaddon.base.registry.KeyBindRegistry;
import com.rettichlp.unicacityaddon.base.registry.ModuleRegistry;
import com.rettichlp.unicacityaddon.base.teamspeak.TSClientQuery;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.utils.UpdateUtils;
import com.rettichlp.unicacityaddon.events.RenderTagEventHandler;
import com.rettichlp.unicacityaddon.events.TabListEventHandler;
import com.rettichlp.unicacityaddon.events.chatlog.ChatLogReceiveChatEventHandler;
import com.rettichlp.unicacityaddon.events.chatlog.ChatLogSendChatEventHandler;
import net.labymod.api.LabyModAddon;
import net.labymod.ingamegui.ModuleCategoryRegistry;
import net.labymod.settings.elements.SettingsElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * @author RettichLP
 */
@Mod(name = "UnicacityAddon", modid = "unicacityaddon", version = UnicacityAddon.VERSION, clientSideOnly = true, acceptedMinecraftVersions = "[1.12,1.12.2]")
public class UnicacityAddon extends LabyModAddon {

    public static final String VERSION = "1.9.0";
    public static final Minecraft MINECRAFT = Minecraft.getMinecraft();
    public static UnicacityAddon ADDON;
    public static final Logger LOGGER = LogManager.getLogger();
    public static ASMDataTable asmDataTable;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        UpdateUtils.modFile = e.getSourceFile();
        asmDataTable = e.getAsmData();
        CommandRegistry.register();
        EventRegistry.register();
    }

    @Override
    public void onEnable() {
        ADDON = this;
        ModuleCategoryRegistry.loadCategory(ModuleRegistry.UNICACITY);
        ModuleRegistry.register();

        // LabyModEvents -> https://docs.labymod.net/pages/create-addons/labymod_events/
        ADDON.getApi().getEventManager().register(new TabListEventHandler());
        ADDON.getApi().getEventManager().register(new RenderTagEventHandler());
        ADDON.getApi().getEventManager().register(new ChatLogSendChatEventHandler());
        ADDON.getApi().getEventManager().register(new ChatLogReceiveChatEventHandler());

        TokenManager.createToken();
        Syncer.syncAll();

        new Thread(TSClientQuery::getInstance).start();
    }

    @Override
    public void loadConfig() {
        KeyBindRegistry.registerKeyBinds();
        FileManager.loadData();
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
        Config.createConfig(this, list);
    }

    public static boolean isUnicacity() {
        if (MINECRAFT.world == null)
            return false;

        ServerData serverData = MINECRAFT.getCurrentServerData();
        if (serverData == null)
            return false;

        String ip = serverData.serverIP;
        if (ip.contains(":"))
            ip = ip.split(":")[0]; // strip unused port

        return ip.toLowerCase().endsWith("unicacity.de");
    }

    public static void debug(String debugMessage) {
        AbstractionLayer.getPlayer().sendMessage(Message.getBuilder()
                .of("[").color(ColorCode.DARK_GRAY).advance()
                .of("DEBUG").color(ColorCode.YELLOW).advance()
                .of("]").color(ColorCode.DARK_GRAY).advance().space()
                .add(debugMessage)
                .createComponent());
    }
}