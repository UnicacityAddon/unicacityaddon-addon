package com.rettichlp.unicacityaddon.base.abstraction;

import java.lang.reflect.InvocationTargetException;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/base/abstraction/AbstractionLayer.java">UCUtils by paulzhng</a>
 */
public class AbstractionLayer {

    private static final Class<? extends UPlayer> uplayerClass = UPlayerImpl.class;

    public static UPlayer getPlayer() {
        try {
            return uplayerClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new UPlayerClassCannotBeInstantiated("Class " + uplayerClass + " cannot be instantiated.", e);
        }
    }
}
