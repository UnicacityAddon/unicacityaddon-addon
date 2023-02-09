package com.rettichlp.unicacityaddon.events;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.Timer;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/ABuyCommand.java">UCUtils by paulzhng</a>
 */
@UCEvent
public class ABuyEventHandler {

    private static final Timer TIMER = new Timer();
    private static long lastBuy;
    private static int amountLeft;
    private static int slotIndex;

    private final UnicacityAddon unicacityAddon;

    public ABuyEventHandler(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    //    @Subscribe
//    public void onKeyboardClickEvent(GuiScreenEvent.KeyboardInputEvent.Post e) {
//        int amount = ABuyCommand.amount;
//        if (amount == 0)
//            return;
//
//        if (!Keyboard.isKeyDown(KeyBindRegistry.aBuy.getKeyCode()))
//            return;
//
//        if (!(e.getGui() instanceof GuiContainer))
//            return;
//        GuiContainer inv = (GuiContainer) e.getGui();
//
//        Slot slot = inv.getSlotUnderMouse();
//        if (slot == null)
//            return;
//
//        ItemStack is = slot.inventory.getStackInSlot(slot.getSlotIndex());
//        NBTTagCompound nbt = is.getTagCompound();
//        if (nbt == null)
//            return;
//
//        NBTTagCompound display = nbt.getCompoundTag("display");
//        String lore = display.getTagList("Lore", Constants.NBT.TAG_STRING).getStringTagAt(0);
//        if (!lore.startsWith("§c") || !lore.endsWith("$"))
//            return;
//
//        slotIndex = slot.getSlotIndex();
//        amountLeft = amount;
//
//        buy();
//    }

//    @Subscribe
//    public void onGuiOpen(GuiOpenEvent e) {
//        if (amountLeft == 0)
//            return;
//        if (!(e.getGui() instanceof GuiContainer))
//            return;
//
//        TIMER.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                if (System.currentTimeMillis() - lastBuy > TimeUnit.SECONDS.toMillis(2)) {
//                    amountLeft = 0;
//                    slotIndex = 0;
//                    return;
//                }
//
//                buy();
//                if (amountLeft == 0) {
//                    slotIndex = 0;
//                }
//            }
//        }, ABuyCommand.delay);
//    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        if (amountLeft == 0)
            return;

        String message = e.chatMessage().getPlainText();
        if (!PatternHandler.BUY_INTERRUPTED_PATTERN.matcher(message).find())
            return;

        amountLeft = 0;
        slotIndex = 0;
    }

//    private void buy() {
//        --amountLeft;
//        lastBuy = System.currentTimeMillis();
//
//        Container container = p.getOpenContainer();
//        unicacityAddon.MINECRAFT.playerController.windowClick(container.windowId, slotIndex, 0, ClickType.PICKUP, unicacityAddon.MINECRAFT.player);
//
//        container.detectAndSendChanges();
//        p.getInventoryContainer().detectAndSendChanges();
//    }
}