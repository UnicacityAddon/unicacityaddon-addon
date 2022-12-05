package com.rettichlp.unicacityaddon.events;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.mobile.ACallCommand;
import com.rettichlp.unicacityaddon.commands.mobile.ASMSCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.ContainerChest;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class MobileEventHandler {

    public static int lastCheckedNumber = 0;
    public static boolean isActive = false;
    public static boolean hasCommunications = false;
    public static boolean muted = false;
    public static boolean activeCommunicationsCheck;
    public static List<String> blockedPlayerList = new ArrayList<>();
    private boolean blockNextMessage = false;
    private boolean whitelistSound = false;

    /**
     * If the user has set a password for their account, <code>/mobile</code> cannot be listed until the account is unlocked.
     * As a result, <code>hasCommunications</code> remains false. To avoid this, the check is carried out again when the message
     * came that the account was unlocked.<br><br>
     *
     * Quote: "Du hast richtig gedacht aber es einfach falsch verstanden." - Dimiikou, 04.10.2022
     */
    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();
        UPlayer p = AbstractionLayer.getPlayer();

        // blocks next SMS message (because SMS messages has two independent message parts)
        if (blockNextMessage && msg.matches("^(?:\\[UC])*\\w+: .*$")) {
            blockNextMessage = false;
            e.setCanceled(true);
        }

        Matcher communicationsRemoveMatcher = PatternHandler.MOBILE_REMOVE_PATTERN.matcher(msg);
        if (communicationsRemoveMatcher.find()) {
            hasCommunications = false;
            return;
        }

        Matcher communicationsGetMatcher = PatternHandler.MOBILE_GET_PATTERN.matcher(msg);
        if (communicationsGetMatcher.find()) {
            hasCommunications = true;
            return;
        }

        Matcher numberMatcher = PatternHandler.MOBILE_NUMBER_PATTERN.matcher(msg);
        if (numberMatcher.find()) {
            lastCheckedNumber = Integer.parseInt(numberMatcher.group(1));
            if (ACallCommand.isActive || ASMSCommand.isActive || isActive) {
                e.setCanceled(true);
                ACallCommand.isActive = ASMSCommand.isActive = isActive = false;
            }
        }

        Matcher mobileCallMatcher = PatternHandler.MOBILE_CALL_PATTERN.matcher(msg);
        if (mobileCallMatcher.find()) {
            if (blockedPlayerList.contains(mobileCallMatcher.group(1))) {
                e.setCanceled(true);
                return;
            }
            if (!muted) {
                whitelistSound = true;
                p.playSound("record.cat");
            }
        }

        Matcher mobileSmsMatcher = PatternHandler.MOBILE_SMS_PATTERN.matcher(msg);
        if (mobileSmsMatcher.find()) {
            String playerName = mobileSmsMatcher.group(1);
            if (!AccountEventHandler.isAfk) AbstractionLayer.getPlayer().sendChatMessage("/nummer " + playerName);
            isActive = true;
            if (blockedPlayerList.contains(playerName)) {
                blockNextMessage = true;
                e.setCanceled(true);
                return;
            }
            if (!muted) {
                whitelistSound = true;
                p.playSound("entity.sheep.ambient");
            }
        }
    }

    /**
     * The GuiOpenEvent is called before a Gui is opened. If we don't want the gui to open, we can cancel the event.<br>
     * Problem: Since the content of the gui is only set after opening, and we haven't opened the gui, the gui items are
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

    /**
     * To handle muted and blocked mobile settings, cancel all mobile sounds and send sound on client side if trigger message is received
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onPlaySound(PlaySoundEvent e) {
        //The event.result starts off equal to event.sound, but could have been altered or set to null by another mod
        if (e.getResult() != null) {
            String name = e.getName();
            if ((name.equals("record.cat") || name.equals("record.stal") || name.equals("entity.sheep.ambient")) && !whitelistSound) {
                e.setResult(null);
                e.setResultSound(null);
                UnicacityAddon.LOGGER.info("Sound event cancelled: " + name);
            } else whitelistSound = false;
        }
    }
}