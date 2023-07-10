package com.rettichlp.unicacityaddon.base.builder;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.api.response.Success;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugPurity;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import lombok.NoArgsConstructor;

/**
 * @author RettichLP
 */
@NoArgsConstructor
public class ActivityCheckBuilder {

    public static Builder getBuilder(UnicacityAddon unicacityAddon) {
        return new Builder(unicacityAddon);
    }

    public static class Builder {

        private Activity activity;
        private String type;
        private String value;
        private DrugType drugType;
        private DrugPurity drugPurity;
        private long date;
        private String screenshot;

        private final UnicacityAddon unicacityAddon;

        public Builder(UnicacityAddon unicacityAddon) {
            this.unicacityAddon = unicacityAddon;
        }

        public Builder activity(Activity activity) {
            this.activity = activity;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public Builder drugType(DrugType drugType) {
            this.drugType = drugType;
            return this;
        }

        public Builder drugPurity(DrugPurity drugPurity) {
            this.drugPurity = drugPurity;
            return this;
        }

        public Builder date(long date) {
            this.date = date;
            return this;
        }

        public Builder screenshot(String screenshot) {
            this.screenshot = screenshot;
            return this;
        }

        public Success send() {
            return this.unicacityAddon.api().sendActivityCheckActivity(
                    this.activity,
                    this.type.replace(" ", "-"),
                    this.value.replace(" ", "-"),
                    this.drugType,
                    this.drugPurity,
                    this.date,
                    this.screenshot.replace(" ", "-"));
        }
    }

    public enum Activity {

        MONEY, DRUG, ROLEPLAY, EQUIP_ADD, EQUIP_EDIT
    }
}