package com.rettichlp.unicacityaddon.v1_12_2;

import com.rettichlp.unicacityaddon.controller.SoundController;
import net.labymod.api.models.Implements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.SoundEvent;

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
    public void playBankRobStartedSound() {
        new Thread(() -> {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(this.create("block.note.chime"), 0.6F));

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(this.create("block.note.chime"), 0.9F));

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(this.create("block.note.chime"), 0.6F));
        }).start();
    }

    @Override
    public void playBombPlantedSound() {
        new Thread(() -> {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(this.create("block.note.chime"), 1.0F));

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(this.create("block.note.chime"), 0.8F));

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(this.create("block.note.chime"), 1.4F));
        }).start();
    }

    @Override
    public void playContractSetSound() {
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(this.create("block.note.chime"), 0.5F));
    }

    @Override
    public void playContractFulfilledSound() {
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(this.create("block.note.chime"), 1.0F));
    }

    @Override
    public void playMobileCallSound() {
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(this.create("record.cat"), 1.0F));
    }

    @Override
    public void playMobileSMSSound() {
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(this.create("entity.sheep.ambient"), 1.0F));
    }

    @Override
    public void playReportSound() {
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(this.create("block.note.pling"), 1.5F));
    }

    @Override
    public void playServiceSound() {
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(this.create("block.note.chime"), 1.0F));
    }

    @Override
    public void playTankWarningSound() {
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(this.create("block.note.harp"), 1.0F));
    }

    @Override
    public void playTimerExpiredSound() {
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(this.create("ui.toast.challenge_complete"), 1.0F));
    }

    @Override
    public void playTeamSpeakSupportSound() {
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(this.create("block.note.pling"), 1.5F));
    }

    @Override
    public void playTeamSpeakPublicitySound() {
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(this.create("block.note.pling"), 1.0F));
    }

    private SoundEvent create(String path) {
        return new SoundEvent(new net.minecraft.util.ResourceLocation(path));
    }
}
