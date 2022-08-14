package com.rettichlp.UnicacityAddon.base.registry;

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
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;

public class ModuleRegistry {

    public static final ModuleCategory UNICACITY = new ModuleCategory("Unicacity", true, new ControlElement.IconData(Material.DIAMOND));

    public static void register(LabyModAddon addon) {
        // Modules -> https://docs.labymod.net/pages/create-addons/module_system/
        addon.getApi().registerModule(new BankMoneyModule());
        addon.getApi().registerModule(new BombTimerModule());
        addon.getApi().registerModule(new CarOpenModule());
        addon.getApi().registerModule(new CashMoneyModule());
        addon.getApi().registerModule(new EmergencyServiceModule());
        addon.getApi().registerModule(new ExplosiveBeltTimerModule());
        addon.getApi().registerModule(new FBIHackModule());
        addon.getApi().registerModule(new JobMoneyModule());
        addon.getApi().registerModule(new PayDayModule());
        addon.getApi().registerModule(new PlantFertilizeTimerModule());
        addon.getApi().registerModule(new PlantWaterTimerModule());
    }

    /*
    public static void register(ASMDataTable asmDataTable) {
        // Modules -> https://docs.labymod.net/pages/create-addons/module_system/
        asmDataTable.getAll(UCModule.class.getCanonicalName()).forEach(asmData -> {
            try {
                Class<?> clazz = Class.forName(asmData.getClassName());
                UnicacityAddon.ADDON.getApi().registerModule((Module) clazz.newInstance());
                System.out.println("UCModule: " + clazz.getSimpleName());
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
    */
}