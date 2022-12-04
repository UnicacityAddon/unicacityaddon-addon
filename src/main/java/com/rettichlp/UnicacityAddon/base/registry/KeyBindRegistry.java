package com.rettichlp.UnicacityAddon.base.registry;

import com.google.gson.JsonObject;
import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.io.FileManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.apache.commons.io.FileUtils;
import org.lwjgl.input.Keyboard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
    public static KeyBinding aBuy;
    public static KeyBinding publicChannelJoin;
    public static KeyBinding freinforcement;
    public static KeyBinding dreinforcement;

    public static void registerKeyBinds() {
        fixOptionsFile();

        if (executed) return;
        JsonObject config = UnicacityAddon.ADDON.getConfig();

        int hotkeyAddonScreenshot = config.has("HOTKEY_SCREENSHOT") ? config.get("HOTKEY_SCREENSHOT").getAsInt() : Keyboard.KEY_NONE;
        int hotkeyAdFreigeben = config.has("HOTKEY_AD_FREIGEBEN") ? config.get("HOTKEY_AD_FREIGEBEN").getAsInt() : Keyboard.KEY_NONE;
        int hotkeyAdBlockieren = config.has("HOTKEY_AD_BLOCKIEREN") ? config.get("HOTKEY_AD_BLOCKIEREN").getAsInt() : Keyboard.KEY_NONE;
        int hotkeyAcceptReport = config.has("HOTKEY_REPORT_ACCEPT") ? config.get("HOTKEY_REPORT_ACCEPT").getAsInt() : Keyboard.KEY_NONE;
        int hotkeyCancelReport = config.has("HOTKEY_REPORT_CANCEL") ? config.get("HOTKEY_REPORT_CANCEL").getAsInt() : Keyboard.KEY_NONE;
        int hotkeyADuty = config.has("HOTKEY_ADUTY") ? config.get("HOTKEY_ADUTY").getAsInt() : Keyboard.KEY_NONE;
        int hotkeyADutySilent = config.has("HOTKEY_ADUTY_SILENT") ? config.get("HOTKEY_ADUTY_SILENT").getAsInt() : Keyboard.KEY_NONE;
        int hotkeyABuy = config.has("HOTKEY_ABUY") ? config.get("HOTKEY_ABUY").getAsInt() : Keyboard.KEY_NONE;
        int hotkeyPublicChannelJoin = config.has("HOTKEY_PUBLICCHANNELJOIN") ? config.get("HOTKEY_PUBLICCHANNELJOIN").getAsInt() : Keyboard.KEY_NONE;
        int hotkeyFReinforcement = config.has("HOTKEY_FREINFORCEMENT") ? config.get("HOTKEY_FREINFORCEMENT").getAsInt() : Keyboard.KEY_NONE;
        int hotkeyDReinforcement = config.has("HOTKEY_DREINFORCEMENT") ? config.get("HOTKEY_DREINFORCEMENT").getAsInt() : Keyboard.KEY_NONE;

        addonScreenshot = new KeyBinding("Screenshot with upload", hotkeyAddonScreenshot, KEY_CATEGORY);
        adFreigeben = new KeyBinding("Accept ad", hotkeyAdFreigeben, KEY_CATEGORY);
        adBlockieren = new KeyBinding("Decline ad", hotkeyAdBlockieren, KEY_CATEGORY);
        acceptReport = new KeyBinding("Accept report", hotkeyAcceptReport, KEY_CATEGORY);
        cancelReport = new KeyBinding("End report (+farewell)", hotkeyCancelReport, KEY_CATEGORY);
        aDuty = new KeyBinding("ADuty", hotkeyADuty, KEY_CATEGORY);
        aDutySilent = new KeyBinding("ADuty (silent)", hotkeyADutySilent, KEY_CATEGORY);
        aBuy = new KeyBinding("ABuy", hotkeyABuy, KEY_CATEGORY);
        publicChannelJoin = new KeyBinding("Public channel join", hotkeyPublicChannelJoin, KEY_CATEGORY);
        freinforcement = new KeyBinding("Reinforcement (faction)", hotkeyFReinforcement, KEY_CATEGORY);
        dreinforcement = new KeyBinding("Reinforcement (alliance faction)", hotkeyDReinforcement, KEY_CATEGORY);

        ClientRegistry.registerKeyBinding(addonScreenshot);
        ClientRegistry.registerKeyBinding(adFreigeben);
        ClientRegistry.registerKeyBinding(adBlockieren);
        ClientRegistry.registerKeyBinding(acceptReport);
        ClientRegistry.registerKeyBinding(cancelReport);
        ClientRegistry.registerKeyBinding(aDuty);
        ClientRegistry.registerKeyBinding(aDutySilent);
        ClientRegistry.registerKeyBinding(aBuy);
        ClientRegistry.registerKeyBinding(publicChannelJoin);
        ClientRegistry.registerKeyBinding(freinforcement);
        ClientRegistry.registerKeyBinding(dreinforcement);
        executed = true;
    }

    private static void fixOptionsFile() {
        File optionsFile = FileManager.getOptionsFile();
        StringBuilder fixedOptionFileStringBuilder = new StringBuilder();

        try {
            assert optionsFile != null;
            try (BufferedReader br = new BufferedReader(new FileReader(optionsFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.toLowerCase().contains("ä") && !line.toLowerCase().contains("ö") && !line.toLowerCase().contains("ü") && !line.toLowerCase().contains("ß")) {
                        fixedOptionFileStringBuilder.append(line).append("\n");
                    }
                }

                FileUtils.writeStringToFile(optionsFile, fixedOptionFileStringBuilder.toString(), StandardCharsets.UTF_8.toString());
            }
        } catch (IOException e) {
            UnicacityAddon.LOGGER.catching(e);
        }
    }
}