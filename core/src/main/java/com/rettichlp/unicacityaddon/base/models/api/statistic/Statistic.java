package com.rettichlp.unicacityaddon.base.models.api.statistic;

import com.rettichlp.unicacityaddon.base.models.ResponseSchema;

/**
 * @author RettichLP
 */
public class Statistic extends ResponseSchema {

    private final String name;
    private final String uuid;
    private final UnicacityAddon unicacityaddon;
    private final GamePlay gameplay;
    private final Faction faction;

    public Statistic(String name, String uuid, UnicacityAddon unicacityaddon, GamePlay gameplay, Faction faction) {
        this.name = name;
        this.uuid = uuid;
        this.unicacityaddon = unicacityaddon;
        this.gameplay = gameplay;
        this.faction = faction;
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    public UnicacityAddon getUnicacityaddon() {
        return unicacityaddon;
    }

    public GamePlay getGameplay() {
        return gameplay;
    }

    public Faction getFaction() {
        return faction;
    }
}