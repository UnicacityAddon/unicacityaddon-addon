package com.rettichlp.unicacityaddon.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author RettichLP
 */
@Getter
@AllArgsConstructor
public class AutoNC {

    private final long id;
    private final List<String> words;
    private final String answer;
}