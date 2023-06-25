package com.rettichlp.unicacityaddon.controller;

import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * @author RettichLP
 */
@Nullable
@Referenceable
public abstract class ScreenshotController {

    public abstract File createScreenshot(File file);
}
