package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.client.component.Component;
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

    public static int amountLeft;

    private final UnicacityAddon unicacityAddon;

    public ABuyListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onItemStackTooltip(ItemStackTooltipEvent e) {
        boolean isPurchasableItem = isPurchasableItem(e.getTooltipLines());

        int slotIndex = -1;
        int i = 0;
        while (isPurchasableItem && slotIndex < 0 && i < 50) {
            if (this.unicacityAddon.player().getInventory().itemStackAt(i).equals(e.itemStack())) {
                slotIndex = i;
            }
            i++;
        }

        if (this.unicacityAddon.configuration().hotkeySetting().aBuy().get().isPressed()) {
            this.unicacityAddon.aBuyController().startBuy(this.unicacityAddon, slotIndex);
        }
    }

    private boolean isPurchasableItem(@NotNull List<Component> tooltipLines) {
        Pattern purchasableItemIdentificator = Pattern.compile("\\d+\\$");

        return tooltipLines.stream()
                .anyMatch(component -> purchasableItemIdentificator.matcher(component.toString()).find());
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        if (amountLeft == 0)
            return;

        String msg = e.chatMessage().getPlainText();
        if (!PatternHandler.BUY_INTERRUPTED_PATTERN.matcher(msg).find())
            return;

        amountLeft = 0;
    }
}