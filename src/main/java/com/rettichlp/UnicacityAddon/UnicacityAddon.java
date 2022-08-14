package com.rettichlp.UnicacityAddon;

import com.rettichlp.UnicacityAddon.base.config.ConfigSettings;
import com.rettichlp.UnicacityAddon.base.faction.FactionHandler;
import com.rettichlp.UnicacityAddon.base.io.FileManager;
import com.rettichlp.UnicacityAddon.base.registry.CommandRegistry;
import com.rettichlp.UnicacityAddon.base.registry.KeyBindRegistry;
import com.rettichlp.UnicacityAddon.base.registry.ModuleRegistry;
import com.rettichlp.UnicacityAddon.events.ABuyEventHandler;
import com.rettichlp.UnicacityAddon.events.AEquipEventHandler;
import com.rettichlp.UnicacityAddon.events.BombTimerEventHandler;
import com.rettichlp.UnicacityAddon.events.CarEventHandler;
import com.rettichlp.UnicacityAddon.events.FactionInfoEventHandler;
import com.rettichlp.UnicacityAddon.events.HotkeyEventHandler;
import com.rettichlp.UnicacityAddon.events.HouseRenterEventHandler;
import com.rettichlp.UnicacityAddon.events.JoinEventHandler;
import com.rettichlp.UnicacityAddon.events.KarmaMessageEventHandler;
import com.rettichlp.UnicacityAddon.events.MobileEventHandler;
import com.rettichlp.UnicacityAddon.events.MoneyEventHandler;
import com.rettichlp.UnicacityAddon.events.NameTagEventHandler;
import com.rettichlp.UnicacityAddon.events.PayDayEventHandler;
import com.rettichlp.UnicacityAddon.events.TabListEventHandler;
import com.rettichlp.UnicacityAddon.events.WeaponClickEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.AFbankEinzahlenEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.BlacklistEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.ContractEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.EmergencyServiceEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.FDoorEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.ReinforcementEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.ShareLocationEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.badfaction.AutomatedCalculationOf25EventHandler;
import com.rettichlp.UnicacityAddon.events.faction.badfaction.BlacklistInfoEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.badfaction.FBIHackEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.badfaction.GiftEigenbedarfEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.badfaction.ModifyBlacklistEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.badfaction.PlantTimerEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.polizei.HQMessageEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.polizei.WantedEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.rettungsdienst.MedicationEventHandler;
import com.rettichlp.UnicacityAddon.events.faction.terroristen.ExplosiveBeltTimerEventHandler;
import com.rettichlp.UnicacityAddon.events.job.ADropEventHandler;
import com.rettichlp.UnicacityAddon.events.job.ADropMoneyEventHandler;
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
import com.rettichlp.UnicacityAddon.modules.PayDayModule;
import com.rettichlp.UnicacityAddon.modules.PlantFertilizeTimerModule;
import com.rettichlp.UnicacityAddon.modules.PlantWaterTimerModule;
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
        CommandRegistry.register(asmDataTable);
    }

    @Override
    public void onEnable() {
        ADDON = this;

        ADDON.getApi().registerForgeListener(new ABuyEventHandler());
        ADDON.getApi().registerForgeListener(new AEquipEventHandler());
        ADDON.getApi().registerForgeListener(new AFbankEinzahlenEventHandler());
        ADDON.getApi().registerForgeListener(new AutomatedCalculationOf25EventHandler());
        ADDON.getApi().registerForgeListener(new ADropEventHandler());
        ADDON.getApi().registerForgeListener(new ADropMoneyEventHandler());
        ADDON.getApi().registerForgeListener(new MoneyEventHandler());
        ADDON.getApi().registerForgeListener(new BlacklistEventHandler());
        ADDON.getApi().registerForgeListener(new BlacklistInfoEventHandler());
        ADDON.getApi().registerForgeListener(new BombTimerEventHandler());
        ADDON.getApi().registerForgeListener(new CarEventHandler());
        ADDON.getApi().registerForgeListener(new ContractEventHandler());
        ADDON.getApi().registerForgeListener(new EmergencyServiceEventHandler());
        ADDON.getApi().registerForgeListener(new ExplosiveBeltTimerEventHandler());
        ADDON.getApi().registerForgeListener(new FactionInfoEventHandler());
        ADDON.getApi().registerForgeListener(new FBIHackEventHandler());
        ADDON.getApi().registerForgeListener(new FDoorEventHandler());
        ADDON.getApi().registerForgeListener(new FishermanEventHandler());
        ADDON.getApi().registerForgeListener(new GiftEigenbedarfEventHandler());
        ADDON.getApi().registerForgeListener(new HotkeyEventHandler());
        ADDON.getApi().registerForgeListener(new HouseRenterEventHandler());
        ADDON.getApi().registerForgeListener(new HQMessageEventHandler());
        ADDON.getApi().registerForgeListener(new InstantDropstoneEventHandler());
        ADDON.getApi().registerForgeListener(new JoinEventHandler());
        ADDON.getApi().registerForgeListener(new KarmaMessageEventHandler());
        ADDON.getApi().registerForgeListener(new MedicationEventHandler());
        ADDON.getApi().registerForgeListener(new MobileEventHandler());
        ADDON.getApi().registerForgeListener(new ModifyBlacklistEventHandler());
        ADDON.getApi().registerForgeListener(new NameTagEventHandler());
        ADDON.getApi().registerForgeListener(new PayDayEventHandler());
        ADDON.getApi().registerForgeListener(new PlantTimerEventHandler());
        ADDON.getApi().registerForgeListener(new ReinforcementEventHandler());
        ADDON.getApi().registerForgeListener(new ReportAcceptEventHandler());
        ADDON.getApi().registerForgeListener(new ShareLocationEventHandler());
        ADDON.getApi().registerForgeListener(new WantedEventHandler());
        ADDON.getApi().registerForgeListener(new WeaponClickEventHandler());

        ADDON.getApi().getEventManager().register(new TabListEventHandler());

        ADDON.getApi().registerModule(new BankMoneyModule());
        ADDON.getApi().registerModule(new BombTimerModule());
        ADDON.getApi().registerModule(new CarOpenModule());
        ADDON.getApi().registerModule(new CashMoneyModule());
        ADDON.getApi().registerModule(new EmergencyServiceModule());
        ADDON.getApi().registerModule(new ExplosiveBeltTimerModule());
        ADDON.getApi().registerModule(new FBIHackModule());
        ADDON.getApi().registerModule(new JobMoneyModule());
        ADDON.getApi().registerModule(new PayDayModule());
        ADDON.getApi().registerModule(new PlantFertilizeTimerModule());
        ADDON.getApi().registerModule(new PlantWaterTimerModule());

        ModuleCategoryRegistry.loadCategory(ModuleRegistry.UNICACITY);

        //EventRegistry.register(this);
        //ModuleRegistry.register(this);
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