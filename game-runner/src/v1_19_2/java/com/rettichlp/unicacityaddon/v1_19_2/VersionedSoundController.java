package com.rettichlp.unicacityaddon.v1_19_2;

import com.rettichlp.unicacityaddon.controller.SoundController;
import net.labymod.api.models.Implements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author RettichLP
 */
@Singleton
@Implements(SoundController.class)
public class VersionedSoundController extends SoundController {

    @Inject
    public VersionedSoundController() {
    }

    @Override
    public void playBombPlantedSound() {

    }

    @Override
    public void playBombRemovedSound() {

    }

    @Override
    public void playContractSetSound() {

    }

    @Override
    public void playContractFulfilledSound() {

    }

    @Override
    public void playMobileCallSound() {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.MUSIC_DISC_CAT, 1.0F));
    }

    @Override
    public void playMobileSMSSound() {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.SHEEP_AMBIENT, 1.0F));
    }

    @Override
    public void playReportSound() {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.NOTE_BLOCK_PLING, 1.5F));
    }

    @Override
    public void playServiceSound() {

    }

    @Override
    public void playTankWarningSound() {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.NOTE_BLOCK_HARP, 1.0F));
    }

    @Override
    public void playTimerExpiredSound() {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 1.0F));
    }

    @Override
    public void playTSNotificationSupportChannelSound() {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.NOTE_BLOCK_PLING, 1.5F));
    }

    @Override
    public void playTSNotificationPublicChannelSound() {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.NOTE_BLOCK_PLING, 1.0F));
    }
}
