package com.rettichlp.unicacityaddon.base.registry;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SoundRegistry {

    public static final SoundEvent BOMB_SOUND = register("bomb_sound");
    public static final SoundEvent CONTRACT_SET_SOUND = register("contract_set_sound");
    public static final SoundEvent CONTRACT_FULFILLED_SOUND = register("contract_fulfilled_sound");
    public static final SoundEvent REPORT_SOUND = register("report_sound");
    public static final SoundEvent SERVICE_SOUND = register("service_sound");

    private static SoundEvent register(String name) {
        ResourceLocation resourceLocation = new ResourceLocation("unicacityaddon", name);
        return new SoundEvent(resourceLocation);
    }
}