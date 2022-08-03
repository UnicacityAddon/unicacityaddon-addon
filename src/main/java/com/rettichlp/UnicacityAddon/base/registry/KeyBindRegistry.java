package com.rettichlp.UnicacityAddon.base.registry;

import com.google.gson.JsonObject;
import com.rettichlp.UnicacityAddon.UnicacityAddon;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/keybind/KeyBindRegistry.java">UCUtils by paulzhng</a>
 */
public class KeyBindRegistry {

    private static boolean executed  = false; // execute once, to prevent multiple registration of same key
    private static final String KEY_CATEGORY = "UnicacityAddon";

    public static KeyBinding addonScreenshot;
    public static KeyBinding adFreigeben;
    public static KeyBinding adBlockieren;
    public static KeyBinding acceptReport;
    public static KeyBinding cancelReport;
    public static KeyBinding aDuty;
    public static KeyBinding aDutySilent;

    public static void registerKeyBinds() {
        if (executed) return;
        JsonObject config = UnicacityAddon.ADDON.getConfig();

        int hotkeyAddonScreenshot = config.has("HOTKEY_SCREENSHOT") ? config.get("HOTKEY_SCREENSHOT").getAsInt() : Keyboard.KEY_NONE;
        int hotkeyAdFreigeben = config.has("HOTKEY_AD_FREIGEBEN") ? config.get("HOTKEY_AD_FREIGEBEN").getAsInt() : Keyboard.KEY_NONE;
        int hotkeyAdBlockieren = config.has("HOTKEY_AD_BLOCKIEREN") ? config.get("HOTKEY_AD_BLOCKIEREN").getAsInt() : Keyboard.KEY_NONE;
        int hotkeyAcceptReport = config.has("HOTKEY_REPORT_ACCEPT") ? config.get("HOTKEY_REPORT_ACCEPT").getAsInt() : Keyboard.KEY_NONE;
        int hotkeyCancelReport = config.has("HOTKEY_REPORT_CANCEL") ? config.get("HOTKEY_REPORT_CANCEL").getAsInt() : Keyboard.KEY_NONE;
        int hotkeyADuty = config.has("HOTKEY_ADUTY") ? config.get("HOTKEY_ADUTY").getAsInt() : Keyboard.KEY_NONE;
        int hotkeyADutySilent = config.has("HOTKEY_ADUTY_SILENT") ? config.get("HOTKEY_ADUTY_SILENT").getAsInt() : Keyboard.KEY_NONE;

        addonScreenshot = new KeyBinding("Screenshot mit Upload", hotkeyAddonScreenshot, KEY_CATEGORY);
        adFreigeben = new KeyBinding("Werbung freigeben", hotkeyAdFreigeben, KEY_CATEGORY);
        adBlockieren = new KeyBinding("Werbung blockieren", hotkeyAdBlockieren, KEY_CATEGORY);
        acceptReport = new KeyBinding("Report annehmen", hotkeyAcceptReport, KEY_CATEGORY);
        cancelReport = new KeyBinding("Report beenden (+Verabschiedung)", hotkeyCancelReport, KEY_CATEGORY);
        aDuty = new KeyBinding("ADuty", hotkeyADuty, KEY_CATEGORY);
        aDutySilent = new KeyBinding("ADuty (silent)", hotkeyADutySilent, KEY_CATEGORY);

        ClientRegistry.registerKeyBinding(addonScreenshot);
        ClientRegistry.registerKeyBinding(adFreigeben);
        ClientRegistry.registerKeyBinding(adBlockieren);
        ClientRegistry.registerKeyBinding(acceptReport);
        ClientRegistry.registerKeyBinding(cancelReport);
        ClientRegistry.registerKeyBinding(aDuty);
        ClientRegistry.registerKeyBinding(aDutySilent);

        executed = true;
    }
}
