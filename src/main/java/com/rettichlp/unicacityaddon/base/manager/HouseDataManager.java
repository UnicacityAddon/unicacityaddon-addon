package com.rettichlp.unicacityaddon.base.manager;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.models.HouseData;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;

import java.util.Map;

/**
 * @author RettichLP
 */
public class HouseDataManager {

    public static Map<Integer, HouseData> HOUSE_DATA;

    public static HouseData getHouseData(int houseNumber) {
        if (!HOUSE_DATA.containsKey(houseNumber)) {
            HOUSE_DATA.put(houseNumber, new HouseData(houseNumber));
            FileManager.saveData();
        }
        return HOUSE_DATA.get(houseNumber);
    }

    public static void saveHouseData(int houseNumber, HouseData houseData) {
        HOUSE_DATA.put(houseNumber, houseData);
        FileManager.saveData();
    }

    public static void deleteHouseData(int houseNumber) {
        HOUSE_DATA.remove(houseNumber);
        FileManager.saveData();
    }

    public static void sendAllHouseBankMessage() {
        UPlayer p = AbstractionLayer.getPlayer();

        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("Hauskassen:").color(ColorCode.DARK_AQUA).bold().advance()
                .createComponent());
        HOUSE_DATA.values().forEach(houseData -> p.sendMessage(houseData.getBankITextComponent()));
        p.sendEmptyMessage();
    }

    public static void sendAllDrugStorageMessage() {
        UPlayer p = AbstractionLayer.getPlayer();

        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("Drogenlager:").color(ColorCode.DARK_AQUA).bold().advance()
                .createComponent());
        HOUSE_DATA.values().forEach(houseData -> p.sendMessage(houseData.getStorageITextComponent()));
        p.sendEmptyMessage();
    }
}