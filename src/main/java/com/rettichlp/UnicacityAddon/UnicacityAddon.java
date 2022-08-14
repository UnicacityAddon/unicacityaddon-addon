package com.rettichlp.UnicacityAddon;

import com.rettichlp.UnicacityAddon.base.config.ConfigSettings;
import com.rettichlp.UnicacityAddon.base.faction.FactionHandler;
import com.rettichlp.UnicacityAddon.base.io.FileManager;
import com.rettichlp.UnicacityAddon.base.registry.CommandRegistry;
import com.rettichlp.UnicacityAddon.base.registry.EventRegistry;
import com.rettichlp.UnicacityAddon.base.registry.KeyBindRegistry;
import com.rettichlp.UnicacityAddon.base.registry.ModuleRegistry;
import com.rettichlp.UnicacityAddon.events.faction.BlacklistEventHandler;
import net.labymod.api.LabyModAddon;
import net.labymod.ingamegui.ModuleCategoryRegistry;
import net.labymod.main.LabyMod;
import net.labymod.settings.elements.SettingsElement;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.List;

/**
 * @author RettichLP
 */
@Mod(name = "UnicacityAddon", modid = "unicacityaddon", version = UnicacityAddon.VERSION, clientSideOnly = true, acceptedMinecraftVersions = "[1.12,1.12.2]")
public class UnicacityAddon extends LabyModAddon {

    public static final String VERSION = "1.3.0-dev";
    public static final Minecraft MINECRAFT = Minecraft.getMinecraft();
    public static final LabyMod LABYMOD = LabyMod.getInstance();
    public static UnicacityAddon ADDON;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        ASMDataTable asmDataTable = e.getAsmData();

        ModuleCategoryRegistry.loadCategory(ModuleRegistry.UNICACITY);
        ModuleRegistry.register(asmDataTable);
        CommandRegistry.register(asmDataTable);
        EventRegistry.register(asmDataTable);
    }

    @Override
    public void onEnable() {
        ADDON = this;
    }

    @Override
    public void loadConfig() {
        FactionHandler.getPlayerFactionMap();
        FactionHandler.getPlayerRankMap();

        // Update blacklist
        BlacklistEventHandler.refreshBlacklistReasons();
        // Register keybindings
        KeyBindRegistry.registerKeyBinds();

        FileManager.loadData();
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
        ConfigSettings.createConfig(this, list);
    }
}