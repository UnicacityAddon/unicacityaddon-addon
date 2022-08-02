package com.rettichlp.UnicacityAddon.events.faction.rettungsdienst;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.commands.faction.rettungsdienst.ARezeptAcceptCommand;
import com.rettichlp.UnicacityAddon.commands.faction.rettungsdienst.ARezeptGiveCommand;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author RettichLP
 */
public class MedicationEventHandler {

    private static final Timer TIMER = new Timer();
    private static long lastExecution;

    @SubscribeEvent
    public void onRecipeAcceptFeedback(ClientChatReceivedEvent e) {
        if (ARezeptAcceptCommand.amount < 1) return; //checks if there is an active recipe-accept-process

        String msg = e.getMessage().getUnformattedText();
        if (!PatternHandler.RECIPE_ACCEPT_PATTERN.matcher(msg).find()) return;

        long timeSinceLastExecution = System.currentTimeMillis() - lastExecution;
        long delay = 0;

        if (timeSinceLastExecution < 750) delay = 750 - timeSinceLastExecution;

        TIMER.schedule(new TimerTask() {
            @Override
            public void run() {
                acceptRecipe();
            }
        }, delay);
    }

    private void acceptRecipe() {
        --ARezeptAcceptCommand.amount;
        lastExecution = System.currentTimeMillis();
        AbstractionLayer.getPlayer().acceptOffer();
    }

    @SubscribeEvent public void onRecipeGiveFeedback(ClientChatReceivedEvent e) {
        if (ARezeptGiveCommand.amount < 1)
            return; //checks if there is an active recipe-give-process

        String msg = e.getMessage().getUnformattedText();
        if (!PatternHandler.RECIPE_GIVE_PATTERN.matcher(msg).find())
            return;

        long timeSinceLastExecution = System.currentTimeMillis() - lastExecution;
        long delay = 0;

        if (timeSinceLastExecution < 750) delay = 750 - timeSinceLastExecution;

        TIMER.schedule(new TimerTask() {
            @Override public void run() {
                giveRecipe();
            }
        }, delay);
    }

    private void giveRecipe() {
        --ARezeptGiveCommand.amount;
        lastExecution = System.currentTimeMillis();
        AbstractionLayer.getPlayer().sellMedication(ARezeptGiveCommand.target, ARezeptGiveCommand.medication);
    }
}