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

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Dimiikou
 */
@UCCommand(prefix = "payequip", usage = "[Id]")
public class PayEquipCommand extends UnicacityCommand {

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
                String uuid = Optional.ofNullable(p.getUniqueId())
                        .map(u -> u.toString().replace("-", ""))
                        .orElse("");

                String response = this.unicacityAddon.webService().sendRequest("https://lemilieu.de/api/equip/get?member=" + uuid);

                AtomicInteger toPay = new AtomicInteger();
                JsonArray jsonArray = new JsonParser().parse(response).getAsJsonArray();
                jsonArray.forEach(jsonElement -> {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    if (jsonObject.get("id").getAsString().equals(arguments[0])) {
                        toPay.set(Integer.parseInt(jsonObject.get("price").getAsString()));
                    }
                });

                if (toPay.get() > 0) {
                    p.sendServerMessage("/fbank einzahlen " + toPay.get());
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