package com.rettichlp.unicacityaddon.events.faction.rettungsdienst;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.faction.rettungsdienst.ARezeptAnnehmenCommand;
import com.rettichlp.unicacityaddon.commands.faction.rettungsdienst.ARezeptCommand;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author RettichLP
 */
@UCEvent
public class MedicationEventHandler {

    private static final Timer TIMER = new Timer();
    private static long lastExecution;

    @SubscribeEvent
    public void onRecipeAcceptFeedback(ClientChatReceivedEvent e) {
        if (ARezeptAnnehmenCommand.amount < 1) return; //checks if there is an active recipe-accept-process

        String msg = e.getMessage().getUnformattedText();
        if (!PatternHandler.RECIPE_ACCEPT_PATTERN.matcher(msg).find()) return;

        long timeSinceLastExecution = System.currentTimeMillis() - lastExecution;
        long delay = 0;

        if (timeSinceLastExecution < 1000) delay = 1000 - timeSinceLastExecution;

        TIMER.schedule(new TimerTask() {
            @Override
            public void run() {
                acceptRecipe();
            }
        }, delay);
    }

    public static void acceptRecipe() {
        --ARezeptAnnehmenCommand.amount;
        lastExecution = System.currentTimeMillis();
        AbstractionLayer.getPlayer().acceptOffer();
    }

    @SubscribeEvent
    public void onRecipeGiveFeedback(ClientChatReceivedEvent e) {
        if (ARezeptCommand.amount < 1)
            return; //checks if there is an active recipe-give-process

        String msg = e.getMessage().getUnformattedText();
        if (!PatternHandler.RECIPE_GIVE_PATTERN.matcher(msg).find())
            return;

        long timeSinceLastExecution = System.currentTimeMillis() - lastExecution;
        long delay = 0;

        if (timeSinceLastExecution < 1000) delay = 1000 - timeSinceLastExecution;

        TIMER.schedule(new TimerTask() {
            @Override
            public void run() {
                giveRecipe();
            }
        }, delay);
    }

    public static void giveRecipe() {
        --ARezeptCommand.amount;
        lastExecution = System.currentTimeMillis();
        AbstractionLayer.getPlayer().sellMedication(ARezeptCommand.target, ARezeptCommand.medication);
    }
}