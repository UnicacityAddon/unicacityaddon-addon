package com.rettichlp.unicacityaddon.base.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author RettichLP
 */
public class ListUtils {

    public static List<String> getAllMatchesFromString(Pattern pattern, String content) {
        List<String> list = new ArrayList<>();
        Matcher matcher = pattern.matcher(content);
        while (matcher.find())
            list.add(matcher.group());
        return list;
    }
}
