package com.rettichlp.unicacityaddon.controller;

import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.Nullable;

/**
 * @author RettichLP
 */
@Nullable
@Referenceable
public abstract class SoundController {

    public abstract void playBankRobStartedSound();

    public abstract void playBombPlantedSound();

    public abstract void playContractSetSound();

    public abstract void playContractFulfilledSound();

    public abstract void playMobileCallSound();

    public abstract void playMobileSMSSound();

    public abstract void playReportSound();

    public abstract void playServiceSound();

    public abstract void playTankWarningSound();

    public abstract void playTimerExpiredSound();

    public abstract void playTeamSpeakSupportSound();

    public abstract void playTeamSpeakPublicitySound();
}
