package com.rettichlp.unicacityaddon.commands.faction;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.io.api.APIResponseException;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * @author Dimiikou
 */
@UCCommand(prefix = "equiplist", usage = "(reset)")
public class EquipListCommand extends UnicacityCommand {

    private final UnicacityAddon unicacityAddon;

    public EquipListCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    /**
     * Quote: "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]" - Minecraft Crash Report, 09.10.2022
     */
    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length == 1 && arguments[0].equalsIgnoreCase("reset")) {
            this.unicacityAddon.fileService().data().setEquipMap(new HashMap<>());
            p.sendInfoMessage("Equipliste gelöscht.");
        } else if (p.getFaction().equals(Faction.LEMILIEU)) {
            equipListLeMilieu(p);
        } else {
            equipList(p);
        }

        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments)
                .addAtIndex(1, "reset")
                .build();
    }

    private void equipList(AddonPlayer p) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("da", "DK"));
        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("Equip:").color(ColorCode.DARK_AQUA).bold().advance()
                .createComponent());

        this.unicacityAddon.fileService().data().getEquipMap().forEach((equip, integer) -> {
            String equipName = equip != null ? equip.getEquipName() : "unbekanntes Equip";
            int equipPrice = equip != null ? equip.getPrice(this.unicacityAddon.configuration()) : 0;
            p.sendMessage(Message.getBuilder()
                    .of("» " + integer + "x " + equipName + ":").color(ColorCode.GRAY).advance().space()
                    .of(numberFormat.format(equipPrice) + "$").color(ColorCode.AQUA).advance()
                    .createComponent());
        });

        int totalAmount = this.unicacityAddon.fileService().data().getEquipMap().entrySet().stream()
                .map(equipIntegerEntry -> equipIntegerEntry.getKey().getPrice(this.unicacityAddon.configuration()) * equipIntegerEntry.getValue())
                .reduce(0, Integer::sum);

        p.sendMessage(Message.getBuilder()
                .of("»").color(ColorCode.GRAY).advance().space()
                .of(numberFormat.format(totalAmount) + "$").color(ColorCode.AQUA).bold().advance()
                .createComponent());

        p.sendEmptyMessage();
    }

    private void equipListLeMilieu(AddonPlayer p) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("da", "DK"));
        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("Equip:").color(ColorCode.DARK_AQUA).bold().advance()
                .createComponent());

        new Thread(() -> {
            try {
                String response = this.unicacityAddon.webService().sendRequest("https://lemilieu.de/api/equip/get?member=" + p.getShortUniqueId());

                JsonArray jsonArray = new JsonParser().parse(response).getAsJsonArray();
                jsonArray.forEach(jsonElement -> {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    String payed = jsonObject.get("payed").getAsString();

                    if (payed.equals("false")) {
                        String id = jsonObject.get("id").getAsString();
                        String price = jsonObject.get("price").getAsString();
                        String item = jsonObject.get("item").getAsString();

                        p.sendMessage(Message.getBuilder()
                                .of("» ID " + id + ": " + item + " -").color(ColorCode.GRAY).advance().space()
                                .of(numberFormat.format(Integer.parseInt(price)) + "$").color(ColorCode.AQUA).advance()
                                .createComponent());
                    }
                });

                p.sendEmptyMessage();

            } catch (APIResponseException e) {
                this.unicacityAddon.logger().warn(e.getMessage());
            }
        }).start();
    }
}