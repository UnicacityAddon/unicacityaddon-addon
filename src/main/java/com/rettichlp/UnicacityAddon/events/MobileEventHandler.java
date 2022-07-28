package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import java.util.regex.Matcher;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.ContainerChest;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class MobileEventHandler {

    public static int lastCheckedNumber = 0;
    public static boolean hasCommunications = false;
    private boolean connected;
    private boolean activeCommunicationsCheck;

    /**
     * If the user has set a password for their account, <code>/mobile</code> cannot be listed until the account is unlocked.
     * As a result, <code>hasCommunications</code> remains false. To avoid this, the check is carried out again when the message
     * came that the account was unlocked.
     */
    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        Matcher accountUnlockedMatcher = PatternHandler.ACCOUNT_UNLOCKED_PATTERN.matcher(msg);
        if (accountUnlockedMatcher.find()) {
            activeCommunicationsCheck = true;
            AbstractionLayer.getPlayer().sendChatMessage("/mobile");
        }

        Matcher communicationsRemoveMatcher = PatternHandler.COMMUNICATIONS_REMOVE_PATTERN.matcher(msg);
        if (communicationsRemoveMatcher.find()) {
            hasCommunications = false;
            return;
        }

        Matcher communicationsGetMatcher = PatternHandler.COMMUNICATIONS_GET_PATTERN.matcher(msg);
        if (communicationsGetMatcher.find()) {
            hasCommunications = true;
            return;
        }

        Matcher numberMatcher = PatternHandler.NUMBER_PATTERN.matcher(msg);
        if (numberMatcher.find()) {
            lastCheckedNumber = Integer.parseInt(numberMatcher.group(1));
            e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onJoin(FMLNetworkEvent.ClientConnectedToServerEvent e) {
        connected = true;
    }

    @SubscribeEvent
    public void onJoinWorld(EntityJoinWorldEvent e) {
        if (!connected) return;
        connected = false;
        activeCommunicationsCheck = true;
        AbstractionLayer.getPlayer().sendChatMessage("/mobile");
    }

    /**
     * The GuiOpenEvent is called before a Gui is opened. If we don't want the gui to open, we can cancel the event.<br>
     * Problem: Since the content of the gui is only set after opening and we haven't opened the gui, the gui items are
     * placed in the inventory on the client side.<br>
     * Solution: We close the inventory after opening it (and setting the items).
     */
    @SubscribeEvent
    public void onGuiOpen(GuiContainerEvent.DrawForeground e) {
        if (!(e.getGuiContainer().inventorySlots instanceof ContainerChest)) return;
        ContainerChest containerChest = (ContainerChest) e.getGuiContainer().inventorySlots;

        if (!containerChest.getLowerChestInventory().getDisplayName().getUnformattedText().equals("§6Telefon")) return;

        hasCommunications = true;

        if (activeCommunicationsCheck) {
            activeCommunicationsCheck = false;
            Minecraft.getMinecraft().player.closeScreen();
        }
    }
}