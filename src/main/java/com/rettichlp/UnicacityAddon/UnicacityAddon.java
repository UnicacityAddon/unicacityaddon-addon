package com.rettichlp.UnicacityAddon;

import com.rettichlp.UnicacityAddon.base.config.ConfigSettings;
import com.rettichlp.UnicacityAddon.base.faction.FactionHandler;
import com.rettichlp.UnicacityAddon.base.module.UCModuleHandler;
import com.rettichlp.UnicacityAddon.commands.NearestATMCommand;
import com.rettichlp.UnicacityAddon.commands.NearestJobCommand;
import com.rettichlp.UnicacityAddon.commands.TriggerEventCommand;
import com.rettichlp.UnicacityAddon.commands.faction.ReinforcementCommand;
import com.rettichlp.UnicacityAddon.commands.faction.ShareLocationCommand;
import com.rettichlp.UnicacityAddon.commands.faction.police.ASUCommand;
import com.rettichlp.UnicacityAddon.commands.faction.polizei.ASUCommand;
import com.rettichlp.UnicacityAddon.commands.faction.polizei.ModifyWantedsCommand;
import com.rettichlp.UnicacityAddon.commands.faction.rettungsdienst.ARezeptAcceptCommand;
import com.rettichlp.UnicacityAddon.commands.faction.rettungsdienst.ARezeptGiveCommand;
import com.rettichlp.UnicacityAddon.events.ATMInfoEventHandler;
import com.rettichlp.UnicacityAddon.events.BombTimerEventHandler;
import com.rettichlp.UnicacityAddon.events.CarOpenEventHandler;
import com.rettichlp.UnicacityAddon.events.NameTagEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.ShareLocationEventHandler;
import com.rettichlp.UnicacityAddon.events.TabListEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.EmergencyServiceEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.ReinforcementEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.polizei.WantedEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.rettungsdienst.MedicationEventHandler;
import com.rettichlp.UnicacityAddon.modules.BombTimerModule;
import com.rettichlp.UnicacityAddon.modules.CarOpenModule;
import com.rettichlp.UnicacityAddon.modules.EmergencyServiceModule;
import net.labymod.api.LabyModAddon;
import net.labymod.ingamegui.ModuleCategoryRegistry;
import net.labymod.main.LabyMod;
import net.labymod.settings.elements.SettingsElement;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;

import java.util.List;

/**
 * @author RettichLP
 */
public class UnicacityAddon extends LabyModAddon {

    public static final String VERSION = "1.0.0";
    public static final Minecraft MINECRAFT = Minecraft.getMinecraft();
    public static final LabyMod LABYMOD = LabyMod.getInstance();
    public static UnicacityAddon ADDON;

    @Override
    public void onEnable() {
        ADDON = this;

        //UCCommandHandler.registerCommands();
        // ForgeCommands
        ClientCommandHandler.instance.registerCommand(new ARezeptAcceptCommand());
        ClientCommandHandler.instance.registerCommand(new ARezeptGiveCommand());
        ClientCommandHandler.instance.registerCommand(new ASUCommand());
        ClientCommandHandler.instance.registerCommand(new ModifyWantedsCommand());
        ClientCommandHandler.instance.registerCommand(new NearestATMCommand());
        ClientCommandHandler.instance.registerCommand(new NearestJobCommand());
        ClientCommandHandler.instance.registerCommand(new ReinforcementCommand());
        ClientCommandHandler.instance.registerCommand(new ShareLocationCommand());
        ClientCommandHandler.instance.registerCommand(new TriggerEventCommand());

        // ForgeEvents -> https://docs.labymod.net/pages/create-addons/forge_events/
        ADDON.getApi().registerForgeListener(new ATMInfoEventHandler());
        ADDON.getApi().registerForgeListener(new BombTimerEventHandler());
        ADDON.getApi().registerForgeListener(new CarOpenEventHandler());
        ADDON.getApi().registerForgeListener(new EmergencyServiceEventHandler());
        ADDON.getApi().registerForgeListener(new MedicationEventHandler());
        ADDON.getApi().registerForgeListener(new NameTagEventHandler());
        ADDON.getApi().registerForgeListener(new ReinforcementEventHandler());
        ADDON.getApi().registerForgeListener(new ShareLocationEventHandler());
        ADDON.getApi().registerForgeListener(new WantedEventHandler());

        // LabyModEvents -> https://docs.labymod.net/pages/create-addons/labymod_events/
        ADDON.getApi().getEventManager().register(new TabListEventHandler());

        // Modules -> https://docs.labymod.net/pages/create-addons/module_system/
        ModuleCategoryRegistry.loadCategory(UCModuleHandler.UNICACITY);
        ADDON.getApi().registerModule(new BombTimerModule());
        ADDON.getApi().registerModule(new CarOpenModule());
        ADDON.getApi().registerModule(new EmergencyServiceModule());
    }

    @Override
    public void loadConfig() {
        FactionHandler.getPlayerFactionMap();
        FactionHandler.getPlayerRankMap();

        // Update nametags
        NameTagEventHandler.refreshAllDisplayNames();
        // Update tablist
        TabListEventHandler.refreshTablist();
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
        ConfigSettings.createConfig(this, list);
    }
}