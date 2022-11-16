package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.io.FileManager;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCEvent;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.modules.CarOpenModule;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class CarEventHandler {

    private static final List<Integer> sentTankWarnings = new ArrayList<>();

    @SubscribeEvent
    public boolean onClientChatReceived(ClientChatReceivedEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        String msg = e.getMessage().getUnformattedText();

        if (PatternHandler.CAR_OPEN_PATTERN.matcher(msg).find()) {
            CarOpenModule.info = ColorCode.GREEN.getCode() + "offen";
            FileManager.saveData();
            return false;
        }

        if (PatternHandler.CAR_CLOSE_PATTERN.matcher(msg).find()) {
            CarOpenModule.info = ColorCode.RED.getCode() + "zu";
            FileManager.saveData();
            return false;
        }

        Matcher carPositionMatcher = PatternHandler.CAR_POSITION_PATTERN.matcher(msg);
        if (carPositionMatcher.find() && ConfigElements.getEventCarFind()) {
            p.setNaviRoute(Integer.parseInt(carPositionMatcher.group(1)), Integer.parseInt(carPositionMatcher.group(2)), Integer.parseInt(carPositionMatcher.group(3)));
        }
        return false;
    }

    public static void checkTank(Scoreboard scoreboard) {
        UPlayer p = AbstractionLayer.getPlayer();
        Score tankScore = scoreboard.getScores().stream()
                .filter(score -> score.getPlayerName().equals(ColorCode.GREEN.getCode() + "Tank" + ColorCode.DARK_GRAY.getCode() + ":"))
                .findFirst()
                .orElse(null);

        if (tankScore == null) return;

        int tank = tankScore.getScorePoints();
        switch (tank) {
            case 100:
                sentTankWarnings.clear();
                break;
            case 15:
            case 10:
            case 5:
                if (!sentTankWarnings.contains(tank)) {
                    p.sendInfoMessage("Dein Tank hat noch " + tank + " Liter.");
                    p.playSound("block.note.harp");
                    sentTankWarnings.add(tank);
                }
                break;
        }
    }
}