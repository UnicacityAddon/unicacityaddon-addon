package com.rettichlp.unicacityaddon.base.registry.annotation;

import com.rettichlp.unicacityaddon.base.enums.faction.Faction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author RettichLP
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface UCGangzone {

    Faction owner() default Faction.NULL;

    boolean deactivated() default false;
}