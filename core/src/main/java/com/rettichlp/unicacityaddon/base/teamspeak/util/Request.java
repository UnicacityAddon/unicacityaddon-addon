/*
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.rettichlp.unicacityaddon.base.teamspeak.util;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * This code was modified. The original code is available at: <a href="https://github.com/labymod-addons/teamspeak">https://github.com/labymod-addons/teamspeak</a>.
 * <p>
 * The following code is subject to the LGPL Version 2.1.
 *
 * @author jumpingpxl
 * @author RettichLP
 */
public class Request {

    private final String query;

    private final Predicate<String> responsePredicate;
    private final ResponseSchema responseSchema;
    private final String responseIdentifier;

    private int remainingAnswers;

    private Request(
            String query,
            int remainingAnswers,
            ResponseSchema responseSchema,
            String responseIdentifier,
            Predicate<String> responsePredicate
    ) {
        this.query = query;
        this.remainingAnswers = remainingAnswers;
        this.responseSchema = responseSchema;
        this.responseIdentifier = responseIdentifier;
        this.responsePredicate = responsePredicate;
    }

    public static Request unknown(
            String query,
            int remainingAnswers,
            Predicate<String> response
    ) {
        return new Request(query, remainingAnswers, ResponseSchema.UNKNOWN, null, response);
    }

    public static Request unknown(String query, Predicate<String> response) {
        return Request.unknown(query, 1, response);
    }

    public static Request firstParamEquals(
            String query,
            int remainingAnswers,
            String responseIdentifier,
            Consumer<String> response
    ) {
        return new Request(
                query,
                remainingAnswers,
                ResponseSchema.FIRST_PARAM_EQUALS,
                responseIdentifier,
                predicate -> {
                    response.accept(predicate);
                    return true;
                }
        );
    }

    public static Request firstParamEquals(
            String query,
            String responseIdentifier,
            Consumer<String> response
    ) {
        return Request.firstParamEquals(query, 1, responseIdentifier, response);
    }

    public static Request firstParamStartsWith(
            String query,
            int remainingAnswers,
            String responseIdentifier,
            Consumer<String> response
    ) {
        return new Request(
                query,
                remainingAnswers,
                ResponseSchema.FIRST_PARAM_STARTS_WITH,
                responseIdentifier,
                predicate -> {
                    response.accept(predicate);
                    return true;
                }
        );
    }

    public static Request firstParamStartsWith(
            String query,
            String responseIdentifier,
            Consumer<String> response
    ) {
        return Request.firstParamStartsWith(query, 1, responseIdentifier, response);
    }

    public boolean handle(String firstArgument, String response) {
        switch (this.responseSchema) {
            case UNKNOWN -> {
                if (this.responsePredicate.test(response)) {
                    this.remainingAnswers--;
                    return true;
                }
            }
            case FIRST_PARAM_STARTS_WITH -> {
                if (firstArgument.startsWith(this.responseIdentifier)) {
                    this.responsePredicate.test(response);
                    this.remainingAnswers--;
                    return true;
                }
            }
            case FIRST_PARAM_EQUALS -> {
                if (firstArgument.equals(this.responseIdentifier)) {
                    this.responsePredicate.test(response);
                    this.remainingAnswers--;
                    return true;
                }
            }
        }

        return false;
    }

    public String getQuery() {
        return this.query;
    }

    public boolean isFinished() {
        return this.remainingAnswers <= 0;
    }

    public enum ResponseSchema {
        FIRST_PARAM_EQUALS,
        FIRST_PARAM_STARTS_WITH,
        UNKNOWN
    }
}
