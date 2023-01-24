package com.rettichlp.unicacityaddon.base.manager;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.models.HouseData;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;

/**
 * @author RettichLP
 */
public class HouseDataManager {

    public static HouseData getHouseData(int houseNumber) {
        HouseData houseData = FileManager.DATA.getHouseDataMap().getOrDefault(houseNumber, new HouseData(houseNumber));
        FileManager.DATA.getHouseDataMap().put(houseNumber, houseData);
        return houseData;
    }

    public static void updateHouseData(int houseNumber, HouseData houseData) {
        FileManager.DATA.getHouseDataMap().put(houseNumber, houseData);
    }

    public static void deleteHouseData(int houseNumber) {
        FileManager.DATA.getHouseDataMap().remove(houseNumber);
    }

    public static void sendAllHouseBankMessage() {
        UPlayer p = AbstractionLayer.getPlayer();

        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("Hauskassen:").color(ColorCode.DARK_AQUA).bold().advance()
                .createComponent());
        FileManager.DATA.getHouseDataMap().values().forEach(houseData -> p.sendMessage(houseData.getBankITextComponent()));
        p.sendEmptyMessage();
    }

    public static void sendAllDrugStorageMessage() {
        UPlayer p = AbstractionLayer.getPlayer();

        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("Drogenlager:").color(ColorCode.DARK_AQUA).bold().advance()
                .createComponent());
        FileManager.DATA.getHouseDataMap().values().forEach(houseData -> p.sendMessage(houseData.getStorageITextComponent()));
        p.sendEmptyMessage();
    }
}