package com.rettichlp.UnicacityAddon;

import com.google.gson.Gson;
import com.rettichlp.UnicacityAddon.base.config.ConfigSettings;
import com.rettichlp.UnicacityAddon.base.faction.FactionHandler;
import com.rettichlp.UnicacityAddon.base.io.FileManager;
import com.rettichlp.UnicacityAddon.base.json.balance.Offlinedata;
import com.rettichlp.UnicacityAddon.base.module.UCModuleHandler;
import com.rettichlp.UnicacityAddon.base.registry.KeyBindRegistry;
import com.rettichlp.UnicacityAddon.commands.ACallCommand;
import com.rettichlp.UnicacityAddon.commands.ASMSCommand;
import com.rettichlp.UnicacityAddon.commands.NaviCommand;
import com.rettichlp.UnicacityAddon.commands.NearestATMCommand;
import com.rettichlp.UnicacityAddon.commands.NearestJobCommand;
import com.rettichlp.UnicacityAddon.commands.TriggerEventCommand;
import com.rettichlp.UnicacityAddon.commands.faction.ReinforcementCommand;
import com.rettichlp.UnicacityAddon.commands.faction.ShareLocationCommand;
import com.rettichlp.UnicacityAddon.commands.faction.badfaction.ASetBlacklistCommand;
import com.rettichlp.UnicacityAddon.commands.faction.badfaction.EigenbedarfCommand;
import com.rettichlp.UnicacityAddon.commands.faction.badfaction.SchmarzmarktLocationsCommand;
import com.rettichlp.UnicacityAddon.commands.faction.polizei.ASUCommand;
import com.rettichlp.UnicacityAddon.commands.faction.polizei.ModifyWantedsCommand;
import com.rettichlp.UnicacityAddon.commands.faction.rettungsdienst.ARezeptAnnehmenCommand;
import com.rettichlp.UnicacityAddon.commands.faction.rettungsdienst.ARezeptCommand;
import com.rettichlp.UnicacityAddon.commands.faction.terroristen.ExplosiveBeltCommand;
import com.rettichlp.UnicacityAddon.events.BombTimerEventHandler;
import com.rettichlp.UnicacityAddon.events.CarEventHandler;
import com.rettichlp.UnicacityAddon.events.HotkeyEventHandler;
import com.rettichlp.UnicacityAddon.events.JoinEventHandler;
import com.rettichlp.UnicacityAddon.events.MobileEventHandler;
import com.rettichlp.UnicacityAddon.events.MoneyEventHandler;
import com.rettichlp.UnicacityAddon.events.NameTagEventHandler;
import com.rettichlp.UnicacityAddon.events.PaydayEventHandler;
import com.rettichlp.UnicacityAddon.events.TabListEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.BlacklistEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.ContractEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.EmergencyServiceEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.FDoorEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.ReinforcementEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.ShareLocationEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.badfaction.AutomatedCalculationOf25;
import com.rettichlp.UnicacityAddon.events.faction.badfaction.FBIHackEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.badfaction.PlantTimerEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.polizei.WantedEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.rettungsdienst.MedicationEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.terroristen.ExplosiveBeltTimerEvent;
import com.rettichlp.UnicacityAddon.events.job.ADropEventHandler;
import com.rettichlp.UnicacityAddon.events.job.FishermanEventHandler;
import com.rettichlp.UnicacityAddon.events.job.InstantDropstoneEventHandler;
import com.rettichlp.UnicacityAddon.events.team.ReportAcceptEventHandler;
import com.rettichlp.UnicacityAddon.modules.BankMoneyModule;
import com.rettichlp.UnicacityAddon.modules.BombTimerModule;
import com.rettichlp.UnicacityAddon.modules.CarOpenModule;
import com.rettichlp.UnicacityAddon.modules.CashMoneyModule;
import com.rettichlp.UnicacityAddon.modules.EmergencyServiceModule;
import com.rettichlp.UnicacityAddon.modules.ExplosiveBeltTimerModule;
import com.rettichlp.UnicacityAddon.modules.FBIHackModule;
import com.rettichlp.UnicacityAddon.modules.JobMoneyModule;
import com.rettichlp.UnicacityAddon.modules.PaydayModule;
import com.rettichlp.UnicacityAddon.modules.PlantFertilizeTimerModule;
import com.rettichlp.UnicacityAddon.modules.PlantWaterTimerModule;
import net.labymod.api.LabyModAddon;
import net.labymod.ingamegui.ModuleCategoryRegistry;
import net.labymod.main.LabyMod;
import net.labymod.settings.elements.SettingsElement;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author RettichLP
 */
public class UnicacityAddon extends LabyModAddon {

    public static final String VERSION = "1.2.0-dev";
    public static final Minecraft MINECRAFT = Minecraft.getMinecraft();
    public static final LabyMod LABYMOD = LabyMod.getInstance();
    public static UnicacityAddon ADDON;

    @Override
    public void onEnable() {
        ADDON = this;

        //CommandRegistry.registerCommands(); TODO
        //ModuleRegistry.registerModules(); TODO
        //EventRegistry.registerEvents(); TODO

        // ForgeCommands - TODO remove later
        ClientCommandHandler.instance.registerCommand(new ACallCommand());
        ClientCommandHandler.instance.registerCommand(new ARezeptAnnehmenCommand());
        ClientCommandHandler.instance.registerCommand(new ARezeptCommand());
        ClientCommandHandler.instance.registerCommand(new ASetBlacklistCommand());
        ClientCommandHandler.instance.registerCommand(new ASMSCommand());
        ClientCommandHandler.instance.registerCommand(new ASUCommand());
        ClientCommandHandler.instance.registerCommand(new EigenbedarfCommand());
        ClientCommandHandler.instance.registerCommand(new ExplosiveBeltCommand());
        ClientCommandHandler.instance.registerCommand(new ModifyWantedsCommand());
        ClientCommandHandler.instance.registerCommand(new NaviCommand());
        ClientCommandHandler.instance.registerCommand(new NearestATMCommand());
        ClientCommandHandler.instance.registerCommand(new NearestJobCommand());
        ClientCommandHandler.instance.registerCommand(new ReinforcementCommand());
        ClientCommandHandler.instance.registerCommand(new SchmarzmarktLocationsCommand());
        ClientCommandHandler.instance.registerCommand(new ShareLocationCommand());
        ClientCommandHandler.instance.registerCommand(new TriggerEventCommand());

        // ForgeEvents -> https://docs.labymod.net/pages/create-addons/forge_events/ - TODO remove later
        ADDON.getApi().registerForgeListener(new AutomatedCalculationOf25());
        ADDON.getApi().registerForgeListener(new ADropEventHandler());
        ADDON.getApi().registerForgeListener(new MoneyEventHandler());
        ADDON.getApi().registerForgeListener(new BlacklistEventHandler());
        ADDON.getApi().registerForgeListener(new BombTimerEventHandler());
        ADDON.getApi().registerForgeListener(new CarEventHandler());
        ADDON.getApi().registerForgeListener(new ContractEventHandler());
        ADDON.getApi().registerForgeListener(new EmergencyServiceEventHandler());
        ADDON.getApi().registerForgeListener(new ExplosiveBeltTimerEvent());
        ADDON.getApi().registerForgeListener(new FBIHackEventHandler());
        ADDON.getApi().registerForgeListener(new FDoorEventHandler());
        ADDON.getApi().registerForgeListener(new FishermanEventHandler());
        ADDON.getApi().registerForgeListener(new HotkeyEventHandler());
        ADDON.getApi().registerForgeListener(new InstantDropstoneEventHandler());
        ADDON.getApi().registerForgeListener(new JoinEventHandler());
        ADDON.getApi().registerForgeListener(new MedicationEventHandler());
        ADDON.getApi().registerForgeListener(new MobileEventHandler());
        ADDON.getApi().registerForgeListener(new NameTagEventHandler());
        ADDON.getApi().registerForgeListener(new PaydayEventHandler());
        ADDON.getApi().registerForgeListener(new PlantTimerEventHandler());
        ADDON.getApi().registerForgeListener(new ReinforcementEventHandler());
        ADDON.getApi().registerForgeListener(new ReportAcceptEventHandler());
        ADDON.getApi().registerForgeListener(new ShareLocationEventHandler());
        ADDON.getApi().registerForgeListener(new WantedEventHandler());

        // LabyModEvents -> https://docs.labymod.net/pages/create-addons/labymod_events/ - TODO remove later
        ADDON.getApi().getEventManager().register(new TabListEventHandler());

        // Modules -> https://docs.labymod.net/pages/create-addons/module_system/ - TODO remove later
        ModuleCategoryRegistry.loadCategory(UCModuleHandler.UNICACITY);
        ADDON.getApi().registerModule(new BankMoneyModule());
        ADDON.getApi().registerModule(new BombTimerModule());
        ADDON.getApi().registerModule(new CarOpenModule());
        ADDON.getApi().registerModule(new CashMoneyModule());
        ADDON.getApi().registerModule(new EmergencyServiceModule());
        ADDON.getApi().registerModule(new ExplosiveBeltTimerModule());
        ADDON.getApi().registerModule(new FBIHackModule());
        ADDON.getApi().registerModule(new JobMoneyModule());
        ADDON.getApi().registerModule(new PaydayModule());
        ADDON.getApi().registerModule(new PlantFertilizeTimerModule());
        ADDON.getApi().registerModule(new PlantWaterTimerModule());
    }

    @Override
    public void loadConfig() {
        FactionHandler.getPlayerFactionMap();
        FactionHandler.getPlayerRankMap();

        // Update blacklist
        BlacklistEventHandler.refreshBlacklistReasons();
        // Register keybindings
        KeyBindRegistry.registerKeyBinds();

        loadBalance();
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
        ConfigSettings.createConfig(this, list);
    }

    public static void loadBalance() {
        try {
            File offlineDataFile = FileManager.getOfflineDataFile();
            if (offlineDataFile == null) return;
            Gson g = new Gson();
            String jsonData = FileUtils.readFileToString(offlineDataFile, StandardCharsets.UTF_8.toString());

            if (jsonData.isEmpty()) {
                BankMoneyModule.setBalance(0);
                CashMoneyModule.setBalance(0);
                JobMoneyModule.setBalance(0);
                PaydayModule.setTime(0);
                return;
            }

            Offlinedata offlineData = g.fromJson(jsonData, Offlinedata.class);
            BankMoneyModule.bankBalance = offlineData.getBankBalance();
            CashMoneyModule.cashBalance = offlineData.getCashBalance();
            JobMoneyModule.jobBalance = offlineData.getJobBalance();
            PaydayModule.currentTime = offlineData.getPaydayTime();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveData() {
        try {
            File offlineDataFile = FileManager.getOfflineDataFile();
            if (offlineDataFile == null) return;
            Gson g = new Gson();
            Offlinedata offlinedata = new Offlinedata();
            offlinedata.setBankBalance(BankMoneyModule.bankBalance);
            offlinedata.setCashBalance(CashMoneyModule.cashBalance);
            offlinedata.setJobBalance(JobMoneyModule.jobBalance);
            offlinedata.setPaydayTime(PaydayModule.currentTime);
            FileUtils.writeStringToFile(offlineDataFile, g.toJson(offlinedata), StandardCharsets.UTF_8.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}