package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import net.labymod.api.event.client.world.ItemStackTooltipEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/ABuyCommand.java">UCUtils by paulzhng</a>
 */
@UCEvent
public class ABuyListener {

    public static int amountLeft = 0;
    public static int slotNumber = -1;

    private ItemStack lastHoveredItemStack;

    private final UnicacityAddon unicacityAddon;

    public ABuyListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onItemStackTooltip(ItemStackTooltipEvent e) {
        ItemStack itemStack = e.itemStack();
        if (!itemStack.equals(lastHoveredItemStack) && isPurchasableItem(e.getTooltipLines()) && amountLeft == 0) {
            lastHoveredItemStack = itemStack;
            slotNumber = this.unicacityAddon.guiController().getSlotNumberByDisplayName(TextUtils.plain(e.itemStack().getDisplayName()));
        }
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();
        if (PatternHandler.BUY_INTERRUPTED_PATTERN.matcher(msg).find()) {
            amountLeft = 0;
        }
    }

    private boolean isPurchasableItem(@NotNull List<Component> tooltipLines) {
        return tooltipLines.stream()
                .anyMatch(component -> Pattern.compile("\\d+\\$").matcher(component.toString()).find());
    }
}