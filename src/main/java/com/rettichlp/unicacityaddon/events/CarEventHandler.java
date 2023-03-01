package com.rettichlp.unicacityaddon.events;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.config.ConfigElements;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiContainerEvent;
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
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        String msg = e.getMessage().getUnformattedText();

        if (PatternHandler.CAR_OPEN_PATTERN.matcher(msg).find()) {
            FileManager.DATA.setCarOpen(true);
            return;
        }

        if (PatternHandler.CAR_CLOSE_PATTERN.matcher(msg).find()) {
            FileManager.DATA.setCarOpen(false);
            return;
        }

        Matcher carPositionMatcher = PatternHandler.CAR_POSITION_PATTERN.matcher(msg);
        if (carPositionMatcher.find() && ConfigElements.getEventCarFind()) {
            p.setNaviRoute(Integer.parseInt(carPositionMatcher.group(1)), Integer.parseInt(carPositionMatcher.group(2)), Integer.parseInt(carPositionMatcher.group(3)));
            return;
        }

        Matcher carTicketMatcher = PatternHandler.CAR_TICKET_PATTERN.matcher(msg);
        if (carTicketMatcher.find()) {
            int fine = Integer.parseInt(carTicketMatcher.group(2));

            if (FileManager.DATA.getCashBalance() >= fine) {
                e.setMessage(Message.getBuilder()
                        .of("[").color(ColorCode.DARK_GRAY).advance()
                        .of("Car").color(ColorCode.GOLD).advance()
                        .of("]").color(ColorCode.DARK_GRAY).advance().space()
                        .of("Dein Fahrzeug hat ein Strafzettel").color(ColorCode.GRAY).advance().space()
                        .of("[").color(ColorCode.DARK_GRAY).advance()
                        .of(carTicketMatcher.group(1)).color(ColorCode.GOLD).advance().space()
                        .of("|").color(ColorCode.GRAY).advance().space()
                        .of(String.valueOf(fine)).color(ColorCode.GOLD).advance()
                        .of("]").color(ColorCode.DARK_GRAY).advance().space()
                        .of("[").color(ColorCode.DARK_GRAY).advance()
                        .of("Bezahlen").color(ColorCode.GREEN)
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of(fine + "$ bezahlen").color(ColorCode.RED).advance().createComponent())
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, "/payticket " + fine)
                                .advance()
                        .of("]").color(ColorCode.DARK_GRAY).advance()
                        .createComponent());
            }

            return;
        }

        Matcher checkKFZMatcher = PatternHandler.CAR_CHECK_KFZ_PATTERN.matcher(msg);
        if (checkKFZMatcher.find()) {
            String name = checkKFZMatcher.group(1);
            if (name == null)
                name = checkKFZMatcher.group(2);
            p.sendChatMessage("/memberinfo " + name);
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiContainerEvent.DrawForeground e) {
        if (e.getGuiContainer().inventorySlots instanceof ContainerChest) {
            ContainerChest containerChest = (ContainerChest) e.getGuiContainer().inventorySlots;

            if (containerChest.getLowerChestInventory().getDisplayName().getUnformattedText().equals("§6CarControl")) {
                int numberOfCars = (int) containerChest.getInventory().stream()
                        .filter(itemStack -> !itemStack.isEmpty() && itemStack.getDisplayName().startsWith(ColorCode.GOLD.getCode()))
                        .map(ItemStack::getItem)
                        .filter(item -> item.equals(Item.getItemById(328)) || item.equals(Item.getItemById(331)) || item.equals(Item.getItemById(388)))
                        .count();

                if (numberOfCars == 1) {
                    UnicacityAddon.MINECRAFT.playerController.windowClick(containerChest.windowId, 0, 0, ClickType.PICKUP, UnicacityAddon.MINECRAFT.player);
                    Minecraft.getMinecraft().player.closeScreen();
                }
            }
        }
    }

    public static void checkTank(Scoreboard scoreboard) {
        UPlayer p = AbstractionLayer.getPlayer();
        Score tankScore = scoreboard.getScores().stream()
                .filter(score -> score.getPlayerName().equals(ColorCode.GREEN.getCode() + "Tank" + ColorCode.DARK_GRAY.getCode() + ":"))
                .findFirst()
                .orElse(null);

        if (tankScore == null)
            return;

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