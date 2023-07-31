package com.rettichlp.unicacityaddon.commands.api.activity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.io.api.APIResponseException;
import com.rettichlp.unicacityaddon.base.registry.UnicacityCommand;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.services.utils.MathUtils;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dimiikou
 */
@UCCommand(prefix = "payequip", usage = "[Id]")
public class PayEquipCommand extends UnicacityCommand {

    public static Map.Entry<String, Integer> payEquipMap = new AbstractMap.SimpleEntry<>("0", 0);

    private final UnicacityAddon unicacityAddon;

    public PayEquipCommand(UnicacityAddon unicacityAddon, UCCommand ucCommand) {
        super(unicacityAddon, ucCommand);
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length < 1 || !MathUtils.isInteger(arguments[0])) {
            sendUsage();
            return true;
        }

        new Thread(() -> {
            try {
                String response = this.unicacityAddon.webService().sendRequest("https://lemilieu.de/api/equip/get?member=" + p.getShortUniqueId());

                JsonArray jsonArray = new JsonParser().parse(response).getAsJsonArray();
                jsonArray.forEach(jsonElement -> {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    String id = jsonObject.get("id").getAsString();
                    if (id.equals(arguments[0])) {
                        payEquipMap = new AbstractMap.SimpleEntry<>(id, Integer.parseInt(jsonObject.get("price").getAsString()));
                    }
                });

                if (payEquipMap.getValue() > 0) {
                    p.sendServerMessage("/fbank einzahlen " + payEquipMap.getValue());
                } else {
                    p.sendErrorMessage("Du kannst für die ID kein Equip einzahlen.");
                }
            } catch (APIResponseException e) {
                this.unicacityAddon.logger().warn(e.getMessage());
            }
        }).start();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}