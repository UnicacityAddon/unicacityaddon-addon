package com.rettichlp.unicacityaddon.commands.api.activity;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.ScreenshotBuilder;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand(prefix = "payequip", usage = "[id] [price]")
public class PayEquipCommand extends UnicacityCommand {
    /**
     * TODO: Lösung überlegen
     * Idea A) Generelle Speicherung des Equips in einer API Schnittstelle
     */
    private final List<String> typeOptions = Arrays.asList("blacklist", "ausraub", "menschenhandel", "transport", "autoverkauf");
    private final UnicacityAddon unicacityAddon;

    public PayEquipCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        new Thread(() -> {

            if (arguments.length < 2) {
                sendUsage();
                return;
            }

            if (!typeOptions.contains(arguments[0])) {
                p.sendErrorMessage("Dieser Aktivitätstyp existiert nicht.");
                return;
            }

            String type = arguments[0];
            int value = Integer.parseInt(arguments[1]);
            String screenshot;

            if (arguments.length == 3) {
                screenshot = arguments[2];
            } else {
                try {
                    File file = this.unicacityAddon.fileService().getNewImageFile();
                    ScreenshotBuilder.getBuilder(unicacityAddon).file(file).save();
                    screenshot = this.unicacityAddon.utilService().imageUpload().uploadToLink(file);
                } catch (IOException e) {
                    this.unicacityAddon.logger().warn(e.getMessage());
                }

            }

            //TODO: API Abfrage senden
            //this.unicacityAddon.api().sendBannerAddRequest(type, value, date, screenshot);
            p.sendMessage(Message.getBuilder().of("Du hast deine Aktivität").color(ColorCode.GRAY).advance()
                    .of("erfolgreich").color(ColorCode.GREEN).advance()
                    .of("eingetragen.").advance().createComponent());
        }).start();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, typeOptions)
                .build();
    }
}