package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.enums.faction.Equip;
import com.rettichlp.unicacityaddon.base.events.HotkeyEvent;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.Arrays;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 * @author Dimiikou
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/ABuyCommand.java">UCUtils by paulzhng</a>
 */
@UCEvent
public class EquipShopListener {

    public static int aBuyAmount = 10;
    public static int aEquipAmount = 10;
    public static int period = 150;

    private int amountLeft = 0;
    private int slotNumber = -1;

    private final UnicacityAddon unicacityAddon;

    public EquipShopListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();

        Matcher buyInterruptedMatcher = PatternHandler.BUY_INTERRUPTED_PATTERN.matcher(msg);
        Matcher equipInterruptedMatcher = PatternHandler.EQUIP_INTERRUPTED_PATTERN.matcher(msg);
        if (buyInterruptedMatcher.find() || equipInterruptedMatcher.find()) {
            this.amountLeft = 0;
            return;
        }

        Matcher equipMatcher = PatternHandler.EQUIP_PATTERN.matcher(msg);
        if (equipMatcher.find()) {
            String equipString = equipMatcher.group("equipName");

            Optional<Equip> equipOptional = Arrays.stream(Equip.values())
                    .filter(eq -> eq.getMessageName().equalsIgnoreCase(equipString))
                    .findAny();

            if (equipOptional.isPresent()) {
                this.unicacityAddon.fileService().data().addEquipToEquipMap(equipOptional.get());
            } else {
                this.unicacityAddon.player().sendErrorMessage("Equip wurde nicht gefunden.");
            }
            return;
        }

        Matcher trackerMatcher = PatternHandler.TRACKER_PATTERN.matcher(msg);
        if (trackerMatcher.find()) {
            this.unicacityAddon.fileService().data().addEquipToEquipMap(Equip.TRACKER);
        }
    }

    @Subscribe
    public void onHotkey(HotkeyEvent e) {
        AddonPlayer p = this.unicacityAddon.player();

        if (e.getKey().equals(e.hotkeyConfiguration().aBuy().get())) {
            this.amountLeft = aBuyAmount;
            this.slotNumber = ScreenRenderListener.lastHoveredSlotNumber;

            if (this.slotNumber >= 0) {
                boolean lastHoveredSlotItemDisplayNameIsFertilizerOrWater = ScreenRenderListener.lastHoveredSlotItemDisplayName.equals("Dünger") || ScreenRenderListener.lastHoveredSlotItemDisplayName.equals("Wasser");

                new Timer().scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        if (EquipShopListener.this.amountLeft > 0) {
                            EquipShopListener.this.unicacityAddon.guiController().inventoryClick(slotNumber);
                            EquipShopListener.this.amountLeft--;

                            // If the last hovered item was "Dünger" or "Wasser" run "buy"-command
                            // It isn't executed by Unicacity -> Bug: https://forum.unicacity.de/index.php?thread/109374-blumenladen-kaufverhalten-f%C3%BCr-d%C3%BCnger-und-wasser/
                            if (EquipShopListener.this.amountLeft > 0 && lastHoveredSlotItemDisplayNameIsFertilizerOrWater) {
                                p.sendServerMessage("/buy");
                            }
                        } else {
                            this.cancel();
                            EquipShopListener.this.slotNumber = -1;
                        }
                    }
                }, 0, lastHoveredSlotItemDisplayNameIsFertilizerOrWater ? 1000 : period);
            }
        } else if (e.getKey().equals(e.hotkeyConfiguration().aEquip().get())) {
            this.amountLeft = aEquipAmount;
            this.slotNumber = ScreenRenderListener.lastHoveredSlotNumber;

            if (this.slotNumber >= 0) {
                new Timer().scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        if (EquipShopListener.this.amountLeft > 0) {
                            EquipShopListener.this.unicacityAddon.guiController().inventoryClick(EquipShopListener.this.slotNumber);
                            EquipShopListener.this.amountLeft--;

                            if (EquipShopListener.this.amountLeft > 0) {
                                p.sendServerMessage("/equip");
                            }
                        } else {
                            this.cancel();
                            EquipShopListener.this.slotNumber = -1;
                        }
                    }
                }, 0, 1000);
            }
        }
    }
}