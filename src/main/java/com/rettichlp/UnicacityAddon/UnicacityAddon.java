package com.rettichlp.UnicacityAddon;

import com.rettichlp.UnicacityAddon.base.command.UCCommandHandler;
import com.rettichlp.UnicacityAddon.base.config.ConfigSettings;
import com.rettichlp.UnicacityAddon.base.faction.FactionHandler;
import com.rettichlp.UnicacityAddon.base.module.UCModuleHandler;
import com.rettichlp.UnicacityAddon.events.ATMInfoEventHandler;
import com.rettichlp.UnicacityAddon.events.BombTimerEventHandler;
import com.rettichlp.UnicacityAddon.events.CarOpenEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.ReinforcementEvent;
import com.rettichlp.UnicacityAddon.modules.BombTimerModule;
import com.rettichlp.UnicacityAddon.modules.CarOpenModule;
import net.labymod.api.LabyModAddon;
import net.labymod.ingamegui.ModuleCategoryRegistry;
import net.labymod.main.LabyMod;
import net.labymod.settings.elements.SettingsElement;
import net.minecraft.client.Minecraft;

import java.util.List;

/**
 * @author RettichLP
 */
public class UnicacityAddon extends LabyModAddon {

    public static final String VERSION = "1.0.0";
    public static final Minecraft MINECRAFT = Minecraft.getMinecraft();
    public static UnicacityAddon ADDON;
    public static LabyMod LABYMOD = LabyMod.getInstance();

    @Override
    public void onEnable() {
        ADDON = this;

        // ModuleCategoryRegistry.loadCategory(UCModuleHandler.UNICACITY);
        // UCModuleHandler.registerModules();
        // UCEventHandler.registerEvents();

        // ForgeEvents -> https://docs.labymod.net/pages/create-addons/forge_events/
        ADDON.getApi().registerForgeListener(new BombTimerEventHandler());

        // LabymodEvents -> https://docs.labymod.net/pages/create-addons/labymod_events/
        ADDON.getApi().getEventManager().register(new ATMInfoEventHandler());
        ADDON.getApi().getEventManager().register(new BombTimerEventHandler());
        ADDON.getApi().getEventManager().register(new CarOpenEventHandler());
        ADDON.getApi().getEventManager().register(new UCCommandHandler());
        ADDON.getApi().getEventManager().register(new ReinforcementEvent());

        // Modules -> https://docs.labymod.net/pages/create-addons/module_system/
        ModuleCategoryRegistry.loadCategory(UCModuleHandler.UNICACITY);
        ADDON.getApi().registerModule(new BombTimerModule());
        ADDON.getApi().registerModule(new CarOpenModule());

        // Commands müssen in UCCommandHandler ab Zeile 70 registriert werden!
    }

    @Override
    public void loadConfig() {
        FactionHandler.getPlayerFactionMap();
        FactionHandler.getPlayerRankMap();
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
        ConfigSettings.createConfig(this, list);
    }
}