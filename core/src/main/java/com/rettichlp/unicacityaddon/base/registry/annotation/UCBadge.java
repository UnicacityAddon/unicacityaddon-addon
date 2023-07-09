package com.rettichlp.unicacityaddon.base.registry.annotation;

import net.labymod.api.client.entity.player.badge.PositionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author RettichLP
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface UCBadge {

    /**
     * Name of the {@link net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer}
     *
     * @return Id
     */
    String name();

    /**
     * {@link PositionType} of the {@link net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer}
     *
     * @return {@link PositionType}
     */
    PositionType positionType() default PositionType.RIGHT_TO_NAME;
}