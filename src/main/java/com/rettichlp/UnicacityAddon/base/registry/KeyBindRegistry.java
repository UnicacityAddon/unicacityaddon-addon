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

    private static final String KEY_CATEGORY = "UnicacityAddon";

    public static KeyBinding addonScreenshot;

    public static void registerKeyBinds() {
        JsonObject config = UnicacityAddon.ADDON.getConfig();

        int hotkeyAddonScreenshot = config.has("HOTKEY_SCREENSHOT") ? config.get("HOTKEY_SCREENSHOT").getAsInt() : Keyboard.KEY_NONE;

        addonScreenshot = new KeyBinding("Screenshot mit Upload", hotkeyAddonScreenshot, KEY_CATEGORY);

        ClientRegistry.registerKeyBinding(addonScreenshot);
    }
}
