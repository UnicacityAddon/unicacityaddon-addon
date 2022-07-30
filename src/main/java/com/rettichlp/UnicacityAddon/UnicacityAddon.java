package com.rettichlp.UnicacityAddon;

import com.rettichlp.UnicacityAddon.base.config.ConfigSettings;
import com.rettichlp.UnicacityAddon.base.faction.FactionHandler;
import com.rettichlp.UnicacityAddon.base.module.UCModuleHandler;
import com.rettichlp.UnicacityAddon.commands.ACallCommand;
import com.rettichlp.UnicacityAddon.commands.ASMSCommand;
import com.rettichlp.UnicacityAddon.commands.NearestATMCommand;
import com.rettichlp.UnicacityAddon.commands.NearestJobCommand;
import com.rettichlp.UnicacityAddon.commands.TriggerEventCommand;
import com.rettichlp.UnicacityAddon.commands.faction.ASetBlacklistCommand;
import com.rettichlp.UnicacityAddon.commands.faction.ReinforcementCommand;
import com.rettichlp.UnicacityAddon.commands.faction.ShareLocationCommand;
import com.rettichlp.UnicacityAddon.commands.faction.polizei.ASUCommand;
import com.rettichlp.UnicacityAddon.commands.faction.polizei.ModifyWantedsCommand;
import com.rettichlp.UnicacityAddon.commands.faction.rettungsdienst.ARezeptAcceptCommand;
import com.rettichlp.UnicacityAddon.commands.faction.rettungsdienst.ARezeptGiveCommand;
import com.rettichlp.UnicacityAddon.commands.faction.terroristen.ExplosiveBeltCommand;
import com.rettichlp.UnicacityAddon.events.ATMInfoEventHandler;
import com.rettichlp.UnicacityAddon.events.BombTimerEventHandler;
import com.rettichlp.UnicacityAddon.events.CarEventHandler;
import com.rettichlp.UnicacityAddon.events.MobileEventHandler;
import com.rettichlp.UnicacityAddon.events.NameTagEventHandler;
import com.rettichlp.UnicacityAddon.events.SalaryCountEventHandler;
import com.rettichlp.UnicacityAddon.events.TabListEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.BlacklistEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.ContractEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.EmergencyServiceEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.ReinforcementEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.ShareLocationEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.polizei.WantedEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.rettungsdienst.MedicationEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.terroristen.ExplosiveBeltTimerEvent;

import java.util.List;

import com.rettichlp.UnicacityAddon.modules.BombTimerModule;
import com.rettichlp.UnicacityAddon.modules.CarOpenModule;
import com.rettichlp.UnicacityAddon.modules.EmergencyServiceModule;
import com.rettichlp.UnicacityAddon.modules.ExplosiveBeltTimerModule;
import com.rettichlp.UnicacityAddon.modules.JobSalaryModule;
import net.labymod.api.LabyModAddon;
import net.labymod.ingamegui.ModuleCategoryRegistry;
import net.labymod.main.LabyMod;
import net.labymod.settings.elements.SettingsElement;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;

/**
 * @author RettichLP
 */
public class UnicacityAddon extends LabyModAddon {

    public static final String VERSION = "1.1.0-dev";
    public static final Minecraft MINECRAFT = Minecraft.getMinecraft();
    public static final LabyMod LABYMOD = LabyMod.getInstance();
    public static UnicacityAddon ADDON;

    @Override
    public void onEnable() {
        ADDON = this;

        //UCCommandHandler.registerCommands();
        // ForgeCommands
        ClientCommandHandler.instance.registerCommand(new ACallCommand());
        ClientCommandHandler.instance.registerCommand(new ARezeptAcceptCommand());
        ClientCommandHandler.instance.registerCommand(new ARezeptGiveCommand());
        ClientCommandHandler.instance.registerCommand(new ASetBlacklistCommand());
        ClientCommandHandler.instance.registerCommand(new ASMSCommand());
        ClientCommandHandler.instance.registerCommand(new ASUCommand());
        ClientCommandHandler.instance.registerCommand(new ExplosiveBeltCommand());
        ClientCommandHandler.instance.registerCommand(new ModifyWantedsCommand());
        ClientCommandHandler.instance.registerCommand(new NearestATMCommand());
        ClientCommandHandler.instance.registerCommand(new NearestJobCommand());
        ClientCommandHandler.instance.registerCommand(new ReinforcementCommand());
        ClientCommandHandler.instance.registerCommand(new ShareLocationCommand());
        ClientCommandHandler.instance.registerCommand(new TriggerEventCommand());

        // ForgeEvents -> https://docs.labymod.net/pages/create-addons/forge_events/
        ADDON.getApi().registerForgeListener(new ATMInfoEventHandler());
        ADDON.getApi().registerForgeListener(new BlacklistEventHandler());
        ADDON.getApi().registerForgeListener(new BombTimerEventHandler());
        ADDON.getApi().registerForgeListener(new CarEventHandler());
        ADDON.getApi().registerForgeListener(new ContractEventHandler());
        ADDON.getApi().registerForgeListener(new EmergencyServiceEventHandler());
        ADDON.getApi().registerForgeListener(new ExplosiveBeltTimerEvent());
        ADDON.getApi().registerForgeListener(new SalaryCountEventHandler());
        ADDON.getApi().registerForgeListener(new MedicationEventHandler());
        ADDON.getApi().registerForgeListener(new MobileEventHandler());
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
        ADDON.getApi().registerModule(new ExplosiveBeltTimerModule());
        ADDON.getApi().registerModule(new JobSalaryModule());
    }

    @Override
    public void loadConfig() {
        FactionHandler.getPlayerFactionMap();
        FactionHandler.getPlayerRankMap();

        // Update nametags
        NameTagEventHandler.refreshAllDisplayNames();
        // Update tablist
        TabListEventHandler.refreshTablist();
        // Update blacklist
        BlacklistEventHandler.refreshBlacklistReasons();
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
        ConfigSettings.createConfig(this, list);
    }
}