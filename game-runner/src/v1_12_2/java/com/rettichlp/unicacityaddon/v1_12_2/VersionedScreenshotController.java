package com.rettichlp.unicacityaddon.v1_12_2;

import com.rettichlp.unicacityaddon.controller.ScreenshotController;
import net.labymod.api.Laby;
import net.labymod.api.models.Implements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ScreenShotHelper;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.image.BufferedImage;
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
            Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
                try {
                    Minecraft minecraft = Minecraft.getMinecraft();

                    Framebuffer framebuffer = minecraft.getFramebuffer();
                    BufferedImage bufferedImage = ScreenShotHelper.createScreenshot(minecraft.displayWidth, minecraft.displayHeight, framebuffer);

                    ImageIO.write(bufferedImage, "jpg", file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return file;
    }
}
