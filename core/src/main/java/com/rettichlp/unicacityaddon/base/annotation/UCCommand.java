package com.rettichlp.unicacityaddon.base.annotation;

import com.rettichlp.unicacityaddon.commands.UnicacityCommand;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author RettichLP
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface UCCommand {

    /**
     * Prefix or shortname of the {@link com.rettichlp.unicacityaddon.commands.UnicacityCommand}
     *
     * @return Prefix or shortname
     */
    String prefix();

    /**
     * Aliases or empty array of the {@link com.rettichlp.unicacityaddon.commands.UnicacityCommand}
     *
     * @return Aliases or empty array
     */
    String[] aliases() default {};

    /**
     * Indicates if the command can be executed on other, not Unicacity, servers too
     *
     * @return true if the command can only be executed on unicacity, else false
     */
    boolean onlyOnUnicacity() default true;

    /**
     * Usage of the {@link com.rettichlp.unicacityaddon.commands.UnicacityCommand}. Empty if the command has no command parameters.<br>
     * The usage is without the prefix of the command. For example:<br>
     * <br>
     * Raw command syntax: <code>/tp RettichLP 1 2 3</code><br>
     * Usage: '<code>[Spieler] [x] [y] [z]</code>'<br>
     * <br>
     * The complete syntax with prefix is generated in {@link UnicacityCommand#sendUsage()}<br>
     * <br>
     *
     * @return Usage or empty string
     */
    String usage() default "";

    /**
     * Indicates if the command is deactivated.
     *
     * @return true if the command should not be registered, else false
     */
    boolean deactivated() default false;
}