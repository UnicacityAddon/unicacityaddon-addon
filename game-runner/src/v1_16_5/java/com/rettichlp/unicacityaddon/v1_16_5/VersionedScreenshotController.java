package com.rettichlp.unicacityaddon.v1_16_5;

import com.mojang.blaze3d.platform.NativeImage;
import com.rettichlp.unicacityaddon.controller.ScreenshotController;
import net.labymod.api.Laby;
import net.labymod.api.models.Implements;
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

            Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
                try (NativeImage nativeImage = Screenshot.takeScreenshot(minecraft.getWindow().getWidth(), minecraft.getWindow().getHeight(), minecraft.getMainRenderTarget())) {
                    nativeImage.writeToFile(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return file;
    }
}
