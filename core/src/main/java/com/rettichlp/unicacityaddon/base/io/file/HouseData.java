package com.rettichlp.unicacityaddon.base.io.file;

import com.rettichlp.unicacityaddon.base.enums.faction.DrugPurity;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import lombok.Getter;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.event.HoverEvent;

import java.util.HashMap;
import java.util.Map;

import static com.rettichlp.unicacityaddon.base.io.api.API.mapOf;

/**
 * @author RettichLP
 */
public class HouseData {

    private final int houseNumber;
    @Getter
    private int houseBank;

    private final Map<DrugType, Map<DrugPurity, Integer>> storageMap;

    public HouseData(int houseNumber) {
        this.houseNumber = houseNumber;

        houseBank = 0;
        storageMap = new HashMap<>();
        storageMap.put(DrugType.COCAINE, mapOf(
                DrugPurity.BEST, 0,
                DrugPurity.GOOD, 0,
                DrugPurity.MEDIUM, 0,
                DrugPurity.BAD, 0));
        storageMap.put(DrugType.MARIJUANA, mapOf(
                DrugPurity.BEST, 0,
                DrugPurity.GOOD, 0,
                DrugPurity.MEDIUM, 0,
                DrugPurity.BAD, 0));
        storageMap.put(DrugType.METH, mapOf(
                DrugPurity.BEST, 0,
                DrugPurity.GOOD, 0,
                DrugPurity.MEDIUM, 0,
                DrugPurity.BAD, 0));
        storageMap.put(DrugType.HUSTENSAFT, mapOf(
                DrugPurity.BEST, 0,
                DrugPurity.GOOD, 0,
                DrugPurity.MEDIUM, 0,
                DrugPurity.BAD, 0));
        storageMap.put(DrugType.SCHMERZMITTEL, mapOf(
                DrugPurity.BEST, 0,
                DrugPurity.GOOD, 0,
                DrugPurity.MEDIUM, 0,
                DrugPurity.BAD, 0));
        storageMap.put(DrugType.ANTIBIOTIKA, mapOf(
                DrugPurity.BEST, 0,
                DrugPurity.GOOD, 0,
                DrugPurity.MEDIUM, 0,
                DrugPurity.BAD, 0));
    }

    public HouseData setHouseBank(int houseBank) {
        this.houseBank = houseBank;
        return this;
    }

    public Component getBankComponent() {
        return Message.getBuilder()
                .of("»").color(ColorCode.GRAY).advance().space()
                .of(houseNumber + ":").color(ColorCode.DARK_AQUA).advance().space()
                .of(houseBank + "$").color(ColorCode.AQUA).advance()
                .createComponent();
    }

    public Component getStorageComponent() {
        Message.Builder hoverMessageBuilder = Message.getBuilder()
                .of("Drogenlager:").color(ColorCode.DARK_AQUA).bold().advance().newline();

        storageMap.forEach((drugType, drugPurityIntegerMap) -> {
            if (drugType.isLegal() && drugPurityIntegerMap.get(DrugPurity.BEST) > 0) {
                hoverMessageBuilder
                        .of(drugType.getDrugName() + ":").color(ColorCode.BLUE).advance().space()
                        .of(drugPurityIntegerMap.get(DrugPurity.BEST) + "g").color(ColorCode.AQUA).advance()
                        .newline();
            } else if (drugPurityIntegerMap.values().stream().reduce(0, Integer::sum) > 0) {
                hoverMessageBuilder
                        .of(drugType.getDrugName() + ":").color(ColorCode.BLUE).advance().space()
                        .newline();
                drugPurityIntegerMap.forEach((drugPurity, integer) -> {
                    if (integer > 0) {
                        hoverMessageBuilder
                                .space()
                                .of("➡").color(ColorCode.GRAY).advance().space()
                                .of(drugPurity.getPurityString() + ":").color(ColorCode.DARK_AQUA).advance().space()
                                .of(integer + "g").color(ColorCode.AQUA).advance()
                                .newline();
                    }
                });
            }
        });

        int drugStorageComplete = storageMap.values().stream()
                .map(drugPurityIntegerMap -> drugPurityIntegerMap.values().stream().reduce(0, Integer::sum))
                .reduce(0, Integer::sum);

        return Message.getBuilder()
                .of("»").color(ColorCode.GRAY).advance().space()
                .of(houseNumber + ":").color(ColorCode.DARK_AQUA)
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessageBuilder.createComponent())
                        .advance().space()
                .of("(").color(ColorCode.GRAY)
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessageBuilder.createComponent())
                        .advance()
                .of(String.valueOf(drugStorageComplete)).color(drugStorageComplete == 100 ? ColorCode.RED : ColorCode.GREEN)
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessageBuilder.createComponent())
                        .advance()
                .of("/").color(ColorCode.GRAY)
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessageBuilder.createComponent())
                        .advance()
                .of("100").color(ColorCode.AQUA)
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessageBuilder.createComponent())
                        .advance()
                .of(")").color(ColorCode.GRAY)
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessageBuilder.createComponent())
                        .advance()
                .createComponent();
    }

    public HouseData addToStorage(DrugType drugType, DrugPurity drugPurity, int amount) {
        Map<DrugPurity, Integer> drugPurityIntegerMap = storageMap.get(drugType);
        int oldAmount = drugPurityIntegerMap.get(drugPurity);
        drugPurityIntegerMap.put(drugPurity, oldAmount + amount);
        storageMap.put(drugType, drugPurityIntegerMap);
        return this;
    }

    public HouseData removeFromStorage(DrugType drugType, DrugPurity drugPurity, int amount) {
        Map<DrugPurity, Integer> drugPurityIntegerMap = storageMap.get(drugType);
        int oldAmount = drugPurityIntegerMap.get(drugPurity);
        drugPurityIntegerMap.put(drugPurity, oldAmount != 0 ? oldAmount - amount : 0);
        storageMap.put(drugType, drugPurityIntegerMap);
        return this;
    }
}