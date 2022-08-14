package com.rettichlp.UnicacityAddon.base.registry;

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
import net.labymod.api.LabyModAddon;

public class EventRegistry {

    public static void register(LabyModAddon addon) {
        // ForgeEvents -> https://docs.labymod.net/pages/create-addons/forge_events/
        addon.getApi().registerForgeListener(new ABuyEventHandler());
        addon.getApi().registerForgeListener(new AEquipEventHandler());
        addon.getApi().registerForgeListener(new AFbankEinzahlenEventHandler());
        addon.getApi().registerForgeListener(new AutomatedCalculationOf25EventHandler());
        addon.getApi().registerForgeListener(new ADropEventHandler());
        addon.getApi().registerForgeListener(new ADropMoneyEventHandler());
        addon.getApi().registerForgeListener(new MoneyEventHandler());
        addon.getApi().registerForgeListener(new BlacklistEventHandler());
        addon.getApi().registerForgeListener(new BlacklistInfoEventHandler());
        addon.getApi().registerForgeListener(new BombTimerEventHandler());
        addon.getApi().registerForgeListener(new CarEventHandler());
        addon.getApi().registerForgeListener(new ContractEventHandler());
        addon.getApi().registerForgeListener(new EmergencyServiceEventHandler());
        addon.getApi().registerForgeListener(new ExplosiveBeltTimerEventHandler());
        addon.getApi().registerForgeListener(new FactionInfoEventHandler());
        addon.getApi().registerForgeListener(new FBIHackEventHandler());
        addon.getApi().registerForgeListener(new FDoorEventHandler());
        addon.getApi().registerForgeListener(new FishermanEventHandler());
        addon.getApi().registerForgeListener(new GiftEigenbedarfEventHandler());
        addon.getApi().registerForgeListener(new HotkeyEventHandler());
        addon.getApi().registerForgeListener(new HouseRenterEventHandler());
        addon.getApi().registerForgeListener(new HQMessageEventHandler());
        addon.getApi().registerForgeListener(new InstantDropstoneEventHandler());
        addon.getApi().registerForgeListener(new JoinEventHandler());
        addon.getApi().registerForgeListener(new KarmaMessageEventHandler());
        addon.getApi().registerForgeListener(new MedicationEventHandler());
        addon.getApi().registerForgeListener(new MobileEventHandler());
        addon.getApi().registerForgeListener(new ModifyBlacklistEventHandler());
        addon.getApi().registerForgeListener(new NameTagEventHandler());
        addon.getApi().registerForgeListener(new PayDayEventHandler());
        addon.getApi().registerForgeListener(new PlantTimerEventHandler());
        addon.getApi().registerForgeListener(new ReinforcementEventHandler());
        addon.getApi().registerForgeListener(new ReportAcceptEventHandler());
        addon.getApi().registerForgeListener(new ShareLocationEventHandler());
        addon.getApi().registerForgeListener(new WantedEventHandler());
        addon.getApi().registerForgeListener(new WeaponClickEventHandler());

        // LabyModEvents -> https://docs.labymod.net/pages/create-addons/labymod_events/
        addon.getApi().getEventManager().register(new TabListEventHandler());
    }

    /*
    public static void register(ASMDataTable asmDataTable) {
        // ForgeEvents -> https://docs.labymod.net/pages/create-addons/forge_events/
        asmDataTable.getAll(UCEvent.class.getCanonicalName()).forEach(asmData -> {
            try {
                Class<?> clazz = Class.forName(asmData.getClassName());
                UnicacityAddon.ADDON.getApi().registerForgeListener(clazz.newInstance());
                System.out.println("UCEvent: " + clazz.getSimpleName());
            } catch (ClassNotFoundException e) {
                // TODO: 14.08.2022
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                // TODO: 14.08.2022
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                // TODO: 14.08.2022
                throw new RuntimeException(e);
            }
        });
    }

    // LabyModEvents -> https://docs.labymod.net/pages/create-addons/labymod_events/
    UnicacityAddon.ADDON.getApi().getEventManager().register(new TabListEventHandler());
    */
}