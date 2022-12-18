package com.rettichlp.unicacityaddon.base.enums.location;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.events.faction.EmergencyServiceEventHandler;
import net.labymod.api.util.math.vector.FloatVector3;

public enum ServiceCallBox {

    ALTSTADT("Altstadt", 267, 69, 603),
    CASINO("Casino [Chinatown]", 1242, 69, -318),
    CFK_WESTSIDE("CFK [Westside]", -357, 69, 364),
    DISCO("Disco", -253, 60, -25),
    FLUGHAFEN("Flughafen", -217, 64, 633),
    KRANKENHAUS("Krankenhaus", 246, 69, 257),
    MEXICAN("Mexican", 280, 69, -81),
    OBRIEN("O'Brien", 728, 69, 538),
    STADTHALLE("Stadthalle", 99, 69, 134),
    UNICA17("Unica17", 611, 69, 87);

    private final String locationName;
    private final int x;
    private final int y;
    private final int z;

    ServiceCallBox(String locationName, int x, int y, int z) {
        this.locationName = locationName;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getLocationName() {
        return locationName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public FloatVector3 getFloatVector3() {
        return new FloatVector3(x, y, z);
    }

    public long getDistance(FloatVector3 floatVector3) {
        return Math.round(floatVector3.distance(new FloatVector3(x, y, z)));
    }

    public static ServiceCallBox getServiceCallBoxByLocationName(String s) {
        for (ServiceCallBox serviceCallBox : ServiceCallBox.values()) {
            if (serviceCallBox.getLocationName().equals(s)) return serviceCallBox;
        }
        return null;
    }

    public String getNaviCommand() {
        // only send message in faction chat, if messages is clicked and not during generation process
        if (!EmergencyServiceEventHandler.messageCreationActive) {
            EmergencyServiceEventHandler.messageCreationActive = true;
            AbstractionLayer.getPlayer().sendChatMessage("/f ➡ Unterwegs zur Notrufsäule (" + locationName + ")");
        }
        return "/navi " + x + "/" + y + "/" + z;
    }
}