package com.rettichlp.UnicacityAddon;

import com.rettichlp.UnicacityAddon.base.config.Config;
import com.rettichlp.UnicacityAddon.base.faction.FactionHandler;
import com.rettichlp.UnicacityAddon.base.io.FileManager;
import com.rettichlp.UnicacityAddon.base.registry.CommandRegistry;
import com.rettichlp.UnicacityAddon.base.registry.EventRegistry;
import com.rettichlp.UnicacityAddon.base.registry.KeyBindRegistry;
import com.rettichlp.UnicacityAddon.base.registry.ModuleRegistry;
import com.rettichlp.UnicacityAddon.events.TabListEventHandler;
import com.rettichlp.UnicacityAddon.modules.BankMoneyModule;
import com.rettichlp.UnicacityAddon.modules.BombTimerModule;
import com.rettichlp.UnicacityAddon.modules.CarOpenModule;
import com.rettichlp.UnicacityAddon.modules.CashMoneyModule;
import com.rettichlp.UnicacityAddon.modules.EmergencyServiceModule;
import com.rettichlp.UnicacityAddon.modules.ExplosiveBeltTimerModule;
import com.rettichlp.UnicacityAddon.modules.FBIHackModule;
import com.rettichlp.UnicacityAddon.modules.JobModule;
import com.rettichlp.UnicacityAddon.modules.PayDayModule;
import com.rettichlp.UnicacityAddon.modules.PlantFertilizeTimerModule;
import com.rettichlp.UnicacityAddon.modules.PlantWaterTimerModule;
import net.labymod.api.LabyModAddon;
import net.labymod.ingamegui.ModuleCategoryRegistry;
import net.labymod.settings.elements.SettingsElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.List;

/**
 * @author RettichLP
 */
@Mod(name = "UnicacityAddon", modid = "unicacityaddon", version = UnicacityAddon.VERSION, clientSideOnly = true, acceptedMinecraftVersions = "[1.12,1.12.2]")
public class UnicacityAddon extends LabyModAddon {

    public static final String VERSION = "1.3.1";
    public static final Minecraft MINECRAFT = Minecraft.getMinecraft();
    public static UnicacityAddon ADDON;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        ASMDataTable asmDataTable = e.getAsmData();
        CommandRegistry.register(asmDataTable);
        EventRegistry.register(asmDataTable);
    }

    @Override
    public void onEnable() {
        ADDON = this;
        ModuleCategoryRegistry.loadCategory(ModuleRegistry.UNICACITY);

        // LabyModEvents -> https://docs.labymod.net/pages/create-addons/labymod_events/
        ADDON.getApi().getEventManager().register(new TabListEventHandler());

        // Modules -> https://docs.labymod.net/pages/create-addons/module_system/
        // not automation, because ADDON cannot be provided at preInit
        ADDON.getApi().registerModule(new BankMoneyModule());
        ADDON.getApi().registerModule(new BombTimerModule());
        ADDON.getApi().registerModule(new CarOpenModule());
        ADDON.getApi().registerModule(new CashMoneyModule());
        ADDON.getApi().registerModule(new EmergencyServiceModule());
        ADDON.getApi().registerModule(new ExplosiveBeltTimerModule());
        ADDON.getApi().registerModule(new FBIHackModule());
        ADDON.getApi().registerModule(new JobModule());
        ADDON.getApi().registerModule(new PayDayModule());
        ADDON.getApi().registerModule(new PlantFertilizeTimerModule());
        ADDON.getApi().registerModule(new PlantWaterTimerModule());

        // FactionHandler sync player data
        FactionHandler.syncPlayerFactions();
        FactionHandler.syncPlayerRanks();
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
        if (MINECRAFT.world == null) return false;

        ServerData serverData = MINECRAFT.getCurrentServerData();
        if (serverData == null) return false;

        String ip = serverData.serverIP;
        if (ip.contains(":")) ip = ip.split(":")[0]; // strip unused port

        return ip.equalsIgnoreCase("server.unicacity.de") || ip.equalsIgnoreCase("unicacity.de");
    }
}