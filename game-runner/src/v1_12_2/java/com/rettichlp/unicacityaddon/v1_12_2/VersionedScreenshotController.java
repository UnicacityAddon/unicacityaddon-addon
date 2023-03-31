package com.rettichlp.unicacityaddon.v1_12_2;

import com.rettichlp.unicacityaddon.controller.ScreenshotController;
import net.labymod.api.models.Implements;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;

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
//        if (file != null) {
//            try {
//                Minecraft minecraft = Minecraft.getMinecraft();
//                Framebuffer framebuffer = ReflectionUtils.getValue(minecraft, Framebuffer.class);
//                assert framebuffer != null;
//                BufferedImage image = ScreenShotHelper.createScreenshot(minecraft.displayWidth, minecraft.displayHeight, framebuffer);
//                ImageIO.write(image, "jpg", file);
                return file;
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return null;
    }
}
