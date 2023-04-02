package com.rettichlp.unicacityaddon.v1_16_5;

import com.mojang.blaze3d.platform.NativeImage;
import com.rettichlp.unicacityaddon.controller.ScreenshotController;
import net.labymod.api.models.Implements;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;

/**
 * @author RettichLP
 */
@Singleton
@Implements(ScreenshotController.class)
public class VersionedScreenshotController extends ScreenshotController {

    @Inject
    public VersionedScreenshotController() {
    }

    @Override
    public File createScreenshot(File file) {
        if (file != null) {
            Minecraft minecraft = Minecraft.getInstance();

            NativeImage nativeImage = Screenshot.takeScreenshot(minecraft.getWindow().getWidth(), minecraft.getWindow().getHeight(), minecraft.getMainRenderTarget());
            Util.ioPool().execute(() -> {
                try {
                    nativeImage.writeToFile(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    nativeImage.close();
                }
            });
        }
        return file;
    }
}
