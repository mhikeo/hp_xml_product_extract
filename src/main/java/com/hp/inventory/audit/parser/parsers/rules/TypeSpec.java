/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.parsers.rules;

import com.hp.inventory.audit.parser.RulesConfig;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Static value object holding generic product type assignment rules.
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */

public class TypeSpec {
    /**
     * Categories containing these patterns are never assigned. Applied on the full
     * category string (including the "~" separators).
     */
    public static List<Pattern> categoryExclude = new LinkedList<>();

    /**
     * Tries to match a category based on the product name. It has a lower priority than
     * the embedded category information. Thus entries here are only matched if no suitable
     * embedded category is found.
     */
    public static Map<Pattern, String> productNameMatch = new LinkedHashMap<>();

    /**
     * Replaces a category for the given string right before assignment.
     */
    public static Map<Pattern, String> typeReplace = new LinkedHashMap<>();


    /**
     * Initialize type assignment rules from a de-serialized rules configuration file.
     */
    @SuppressWarnings("unchecked")
    public static void init(RulesConfig rulesCfg) {
        Map<String, String> replace = (Map<String, String>) rulesCfg.productType.get("typeReplace");
        replace.forEach((k, v) ->
                        TypeSpec.typeReplace.put(Pattern.compile(k, Pattern.CASE_INSENSITIVE), v)
        );

        List<String> typeReplace = (List<String>) rulesCfg.productType.get("categoryExclude");
        TypeSpec.categoryExclude = typeReplace.stream()
                .map(s -> Pattern.compile(s, Pattern.CASE_INSENSITIVE))
                .collect(Collectors.toList());

        Map<String, String> productNameMatch = (Map<String, String>) rulesCfg.productType.get("productNameMatch");
        productNameMatch.forEach((k, v) ->
                        TypeSpec.productNameMatch.put(Pattern.compile(k, Pattern.CASE_INSENSITIVE), v)
        );
    }
}
