package com.rettichlp.UnicacityAddon.base.registry;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class KeyBindRegistry {

    private static final String KEY_CATEGORY = "key.categories.unicacityAddon";

    public static KeyBinding imgurscreenshot;

    public static void registerKeyBinds() {
        imgurscreenshot = new KeyBinding("key.imgurscreenshot", Keyboard.KEY_NONE, KEY_CATEGORY);

        ClientRegistry.registerKeyBinding(imgurscreenshot);
    }
}
