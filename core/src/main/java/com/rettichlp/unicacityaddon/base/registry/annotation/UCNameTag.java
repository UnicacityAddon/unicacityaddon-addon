package com.rettichlp.unicacityaddon.base.registry.annotation;

import net.labymod.api.client.entity.player.tag.PositionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author RettichLP
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface UCNameTag {

    /**
     * Name of the {@link net.labymod.api.client.entity.player.tag.tags.NameTag}
     *
     * @return Id
     */
    String name();

    /**
     * {@link PositionType} of the {@link net.labymod.api.client.entity.player.tag.tags.NameTag}
     *
     * @return {@link PositionType}
     */
    PositionType positionType() default PositionType.ABOVE_NAME;

    /**
     * Priority of the {@link net.labymod.api.client.entity.player.tag.tags.NameTag}, the lower the value, the closer the tag to the players name
     *
     * @return Priority
     */
    int priority() default 100;
}