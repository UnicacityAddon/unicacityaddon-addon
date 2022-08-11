package com.rettichlp.UnicacityAddon.events;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.registry.KeyBindRegistry;
import com.rettichlp.UnicacityAddon.base.text.PatternHandler;
import com.rettichlp.UnicacityAddon.base.utils.MathUtils;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/ABuyCommand.java">UCUtils by paulzhng</a>
 */
public class ABuyEventHandler {

    private static final Pattern BUY_INTERRUPTED_PATTERN = Pattern.compile("^Verkäufer: (Tut (uns|mir) Leid|Verzeihung), unser Lager ist derzeit leer\\.$" +
            "|^Verkäufer: Dieses Produkt kostet \\d+\\$\\.$");
    private static final Timer TIMER = new Timer();
    private static int delay = 10;
    private static long lastBuy;
    private static int amountLeft;
    private static int slotIndex;

    @SubscribeEvent
    public void onKeyboardClickEvent(GuiScreenEvent.KeyboardInputEvent.Post e) {
        String amountString = ConfigElements.getEventABuyAmount();
        String delayAmount = ConfigElements.getEventABuyDelay();
        if (!MathUtils.isInteger(amountString) || !MathUtils.isInteger(delayAmount)) return;
        int amount = Integer.parseInt(amountString);
        if (amount == 0) return;

        delay = Integer.parseInt(delayAmount);

        if (!Keyboard.isKeyDown(KeyBindRegistry.aBuy.getKeyCode())) return;

        if (!(e.getGui() instanceof GuiContainer)) return;
        GuiContainer inv = (GuiContainer) e.getGui();

        Slot slot = inv.getSlotUnderMouse();
        if (slot == null) return;

        ItemStack is = slot.inventory.getStackInSlot(slot.getSlotIndex());
        NBTTagCompound nbt = is.getTagCompound();
        if (nbt == null) return;

        NBTTagCompound display = nbt.getCompoundTag("display");
        String lore = display.getTagList("Lore", Constants.NBT.TAG_STRING).getStringTagAt(0);
        if (!lore.startsWith("§c") || !lore.endsWith("$")) return;

        slotIndex = slot.getSlotIndex();
        amountLeft = amount;

        buy();
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent e) {
        if (amountLeft == 0) return;
        if (!(e.getGui() instanceof GuiContainer)) return;

        TIMER.schedule(new TimerTask() {
            @Override
            public void run() {
                if (System.currentTimeMillis() - lastBuy > TimeUnit.SECONDS.toMillis(2)) {
                    amountLeft = 0;
                    slotIndex = 0;
                    return;
                }

                buy();
                if (amountLeft == 0) {
                    slotIndex = 0;
                }
            }
        }, delay);
    }

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent e) {
        if (amountLeft == 0) return;

        String message = e.getMessage().getUnformattedText();
        if (!BUY_INTERRUPTED_PATTERN.matcher(message).find()) return;

        amountLeft = 0;
        slotIndex = 0;
    }

    private void buy() {
        UPlayer p = AbstractionLayer.getPlayer();

        --amountLeft;
        lastBuy = System.currentTimeMillis();

        Container container = p.getOpenContainer();
        UnicacityAddon.MINECRAFT.playerController.windowClick(container.windowId, slotIndex, 0, ClickType.PICKUP, UnicacityAddon.MINECRAFT.player);

        container.detectAndSendChanges();
        p.getInventoryContainer().detectAndSendChanges();
    }
}