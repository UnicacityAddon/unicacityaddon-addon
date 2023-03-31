package com.rettichlp.unicacityaddon.commands.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugPurity;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import net.labymod.api.client.chat.command.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author RettichLP
 */
@UCCommand
public class DropDrugAllCommand extends Command {

    private static final Map<DrugType, Map<DrugPurity, Integer>> drugInventoryMap = new HashMap<>();
    private static boolean cocaineCheck = true;
    private static boolean marihuanaCheck = true;
    private static boolean methCheck = true;
    private static boolean active = false;
    private static int lastWindowId = 0;

    private static final String usage = "/dbankdropall bzw. /asservatenkammerdropall";

    private UnicacityAddon unicacityAddon;

    public DropDrugAllCommand(UnicacityAddon unicacityAddon) {
        super("dbankdropall", "dda", "asservatenkammerdropall", "ada");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length > 0 && arguments[0].equalsIgnoreCase("reset")) {
            this.unicacityAddon.data().setDrugInventoryMap(new HashMap<>());
            return true;
        }

        active = cocaineCheck = marihuanaCheck = methCheck = true;
        p.sendServerMessage("/inv");
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "reset")
                .build();
    }

    public static void process() {
//        GuiScreen guiScreen = UnicacityAddon.MINECRAFT.currentScreen;
//        if (guiScreen instanceof GuiChest && active) {
//            GuiContainer guiContainer = (GuiContainer) guiScreen;
//
//            int windowId = guiContainer.inventorySlots.windowId;
//            if (windowId == lastWindowId)
//                return;
//
//            lastWindowId = windowId;
//
//            if (cocaineCheck) {
//                cocaineCheck = false;
//                // select cocaine to check drug purity
//                UnicacityAddon.MINECRAFT.playerController.windowClick(windowId, 0, 0, ClickType.PICKUP, UnicacityAddon.MINECRAFT.player);
//            } else if (marihuanaCheck) {
//                marihuanaCheck = false;
//                // select marihuana to check drug purity
//                UnicacityAddon.MINECRAFT.playerController.windowClick(windowId, 1, 0, ClickType.PICKUP, UnicacityAddon.MINECRAFT.player);
//            } else if (methCheck) {
//                methCheck = false;
//                // select meth to check drug purity
//                UnicacityAddon.MINECRAFT.playerController.windowClick(windowId, 2, 0, ClickType.PICKUP, UnicacityAddon.MINECRAFT.player);
//            } else {
//                guiContainer.inventorySlots.getInventory().stream()
//                        .filter(itemStack -> !itemStack.isEmpty() && !itemStack.getDisplayName().contains("Kokain") && !itemStack.getDisplayName().contains("Marihuana") && !itemStack.getDisplayName().contains("Methamphetamin"))
//                        .forEach(itemStack -> {
//                            NBTTagCompound nbtTagCompound = itemStack.getSubCompound("display");
//                            if (nbtTagCompound != null) {
//                                String lore = nbtTagCompound.getTagList("Lore", Constants.NBT.TAG_STRING).getStringTagAt(0);
//
//                                Matcher loreMatcher = Pattern.compile("» (?<amount>\\d)(g| Pillen| Flaschen| Päckchen| Stück| Kisten)").matcher(lore);
//                                if (loreMatcher.find()) {
//                                    int amount = Integer.parseInt(loreMatcher.group("amount"));
//                                    DrugType drugType = DrugType.getDrugType(itemStack.getDisplayName().substring(2));
//
//                                    if (drugType != null) {
//                                        Map<DrugPurity, Integer> drugPurityMap = drugInventoryMap.getOrDefault(drugType, new HashMap<>());
//                                        drugPurityMap.put(DrugPurity.BEST, amount);
//                                        drugInventoryMap.put(drugType, drugPurityMap);
//                                    }
//                                }
//                            }
//                        });
//
//                active = false;
//                dropCommandExecution();
//            }
//        } else if (guiScreen instanceof GuiHopper && active) {
//            GuiHopper guiHopper = (GuiHopper) guiScreen;
//
//            int windowId = guiHopper.inventorySlots.windowId;
//            if (windowId == lastWindowId)
//                return;
//
//            lastWindowId = windowId;
//
//            guiHopper.inventorySlots.getInventory().stream()
//                    .filter(itemStack -> !itemStack.isEmpty())
//                    .forEach(itemStack -> {
//                        NBTTagCompound nbtTagCompound = itemStack.getSubCompound("display");
//                        if (nbtTagCompound != null) {
//                            String drugPurityNbt = nbtTagCompound.getTagList("Lore", Constants.NBT.TAG_STRING).getStringTagAt(1);
//                            String amountNbt = nbtTagCompound.getTagList("Lore", Constants.NBT.TAG_STRING).getStringTagAt(2);
//
//                            Matcher loreMatcher = Pattern.compile("» (?<amount>\\d+)g").matcher(amountNbt);
//                            if (loreMatcher.find()) {
//                                int amount = Integer.parseInt(loreMatcher.group("amount"));
//                                DrugType drugType = DrugType.getDrugType(itemStack.getDisplayName().substring(4));
//                                DrugPurity drugPurity = DrugPurity.getDrugPurity(drugPurityNbt.split(" ")[0].substring(2));
//
//                                if (drugType != null) {
//                                    Map<DrugPurity, Integer> drugPurityMap = drugInventoryMap.getOrDefault(drugType, new HashMap<>());
//                                    drugPurityMap.put(drugPurity, amount);
//                                    drugInventoryMap.put(drugType, drugPurityMap);
//                                }
//                            }
//                        }
//                    });
//
//            // go back to inventory container
//            UnicacityAddon.MINECRAFT.playerController.windowClick(guiHopper.inventorySlots.windowId, 4, 0, ClickType.PICKUP, UnicacityAddon.MINECRAFT.player);
//        }
    }
// TODO: 31.03.2023  
//    private static void dropCommandExecution() {
//        AddonPlayer p = this.unicacityAddon.player();
////        p.getPlayer().closeScreen();
//
//        List<String> commandQueue = new ArrayList<>();
//        drugInventoryMap.entrySet().stream()
//                .filter(drugTypeMapEntry -> drugTypeMapEntry.getKey().equals(DrugType.COCAINE) || drugTypeMapEntry.getKey().equals(DrugType.MARIJUANA) || drugTypeMapEntry.getKey().equals(DrugType.METH) || drugTypeMapEntry.getKey().equals(DrugType.LSD))
//                .forEach(drugTypeMapEntry -> drugTypeMapEntry.getValue().forEach((drugPurity, integer) -> {
//                    if (integer > 0) {
//                        String type = p.getFaction().equals(Faction.FBI) ? "asservatenkammer" : "dbank";
//                        commandQueue.add("/" + type + " drop " + drugTypeMapEntry.getKey().getShortName() + " " + integer + " " + drugPurity.getPurity());
//                    }
//                }));
//
//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                if (commandQueue.isEmpty()) {
//                    timer.cancel();
//                } else {
//                    p.sendServerMessage(commandQueue.get(0));
//                    commandQueue.remove(0);
//                }
//            }
//        }, 0, TimeUnit.SECONDS.toMillis(1));
//    }
}