/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.parsers.rules;

import com.hp.inventory.audit.parser.RulesConfig;

import java.util.regex.Pattern;

/**
 * Static value object holding product field CSS queries.
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
public class QueriesSpec {
    
    public static String productNumberQuery;
    public static String productNameQuery;

    public static String currentPriceQueryVisible;
    public static String currentPriceQuery;

    public static String currencyQuery;
    public static String strikedPriceQuery;

    public static Pattern reviewIdPattern; // = Pattern.compile("(\\d+)");

    // public final static String ratingQuery =
    // "span[itemprop=aggregateRating] span[itemprop=ratingValue]";
    public static String ratingQuery; // = "#BVSecondaryCustomerRatings div.BVRRRatingNormalOutOf span.BVRRRatingNumber";

    // public final static String reviewsQuery =
    // "span[itemprop=aggregateRating] meta[itemprop=reviewCount]";
    public static String reviewsQuery; // = "#BVSecondaryCustomerRatings div.BVRRRatingSummaryLinks span.BVRRCount span.BVRRNumber";

    public static String comingSoonQuery; // = "div[itemprop=offers] span:contains(Out of stock)";

    public static String specQueryPrefix;
    public static String specQuerySuffix;
    public static String specQueryPostQuery;

    public static String linkQueryPrefix;
    public static String linkQuerySuffix;
    public static String linkQueryPostQuery;

    public static String accessoriesNameQuery;
    public static String accessoriesUrlQuery;


    public static String imagesSelectQuery;
    public static String imagesSelectPostQuery;


    public static String reviewsExtractQuery;
    public static String reviewsExtractDateFormat;
    public static String reviewsExtractPostDateQuery;
    public static String reviewsExtractHpResponseDateQuery;
    public static String reviewsExtractRatingQuery;
    public static String reviewsExtractScaleQuery;
    public static String reviewsExtractTitleQuery;
    public static String reviewsExtractComentsQuery;
    public static String reviewsExtractUsernameQuery;
    public static String reviewsExtractLocationQuery;
    public static String reviewsExtractHpResponseQuery;
    public static String reviewsExtractHpResponseUserQuery;

    public static String reviewsExtractYesQuery;
    public static String reviewsExtractYesPostQuery;
    public static String reviewsExtractNoQuery;
    public static String reviewsExtractNoPostQuery;

    /**
     * CSS selector on where the full text extractor should begin.
     */
    public static String fullTextExtractStartQuery;

    /**
     * CSS selector specifying excluded elements. Use comma to specify multiple selectors.
     */
    public static String fullTextExtractExcludes;

    /**
     * Initialize queries from a de-serialized rules configuration file.
     */
    public static void init(RulesConfig rulesCfg) {
        QueriesSpec.productNumberQuery = rulesCfg.queriesSpec.get("productNumberQuery");
        QueriesSpec.productNameQuery = rulesCfg.queriesSpec.get("productNameQuery");
        QueriesSpec.currentPriceQueryVisible = rulesCfg.queriesSpec.get("currentPriceQueryVisible");
        QueriesSpec.currentPriceQuery = rulesCfg.queriesSpec.get("currentPriceQuery");
        QueriesSpec.currencyQuery = rulesCfg.queriesSpec.get("currencyQuery");
        QueriesSpec.strikedPriceQuery = rulesCfg.queriesSpec.get("strikedPriceQuery");
        QueriesSpec.reviewIdPattern = Pattern.compile(rulesCfg.queriesSpec.get("reviewIdPattern"));
        QueriesSpec.ratingQuery = rulesCfg.queriesSpec.get("ratingQuery");
        QueriesSpec.reviewsQuery = rulesCfg.queriesSpec.get("reviewsQuery");
        QueriesSpec.comingSoonQuery = rulesCfg.queriesSpec.get("comingSoonQuery");

        QueriesSpec.specQueryPrefix = rulesCfg.queriesSpec.get("specQueryPrefix");
        QueriesSpec.specQuerySuffix = rulesCfg.queriesSpec.get("specQuerySuffix");
        QueriesSpec.specQueryPostQuery = rulesCfg.queriesSpec.get("specQueryPostQuery");

        QueriesSpec.linkQueryPrefix = rulesCfg.queriesSpec.get("linkQueryPrefix");
        QueriesSpec.linkQuerySuffix = rulesCfg.queriesSpec.get("linkQuerySuffix");
        QueriesSpec.linkQueryPostQuery = rulesCfg.queriesSpec.get("linkQueryPostQuery");

        QueriesSpec.accessoriesNameQuery = rulesCfg.queriesSpec.get("accessoriesNameQuery");
        QueriesSpec.accessoriesUrlQuery = rulesCfg.queriesSpec.get("accessoriesUrlQuery");

        QueriesSpec.imagesSelectQuery = rulesCfg.queriesSpec.get("imagesSelectQuery");
        QueriesSpec.imagesSelectPostQuery = rulesCfg.queriesSpec.get("imagesSelectPostQuery");


        QueriesSpec.reviewsExtractQuery = rulesCfg.queriesSpec.get("reviewsExtractQuery");
        QueriesSpec.reviewsExtractDateFormat = rulesCfg.queriesSpec.get("reviewsExtractDateFormat");
        QueriesSpec.reviewsExtractPostDateQuery = rulesCfg.queriesSpec.get("reviewsExtractPostDateQuery");
        QueriesSpec.reviewsExtractHpResponseDateQuery = rulesCfg.queriesSpec.get("reviewsExtractHpResponseDateQuery");
        QueriesSpec.reviewsExtractRatingQuery = rulesCfg.queriesSpec.get("reviewsExtractRatingQuery");
        QueriesSpec.reviewsExtractScaleQuery = rulesCfg.queriesSpec.get("reviewsExtractScaleQuery");
        QueriesSpec.reviewsExtractTitleQuery = rulesCfg.queriesSpec.get("reviewsExtractTitleQuery");
        QueriesSpec.reviewsExtractComentsQuery = rulesCfg.queriesSpec.get("reviewsExtractComentsQuery");
        QueriesSpec.reviewsExtractUsernameQuery = rulesCfg.queriesSpec.get("reviewsExtractUsernameQuery");
        QueriesSpec.reviewsExtractLocationQuery = rulesCfg.queriesSpec.get("reviewsExtractLocationQuery");
        QueriesSpec.reviewsExtractHpResponseQuery = rulesCfg.queriesSpec.get("reviewsExtractHpResponseQuery");
        QueriesSpec.reviewsExtractHpResponseUserQuery = rulesCfg.queriesSpec.get("reviewsExtractHpResponseUserQuery");
        QueriesSpec.reviewsExtractYesQuery = rulesCfg.queriesSpec.get("reviewsExtractYesQuery");
        QueriesSpec.reviewsExtractYesPostQuery = rulesCfg.queriesSpec.get("reviewsExtractYesPostQuery");
        QueriesSpec.reviewsExtractNoQuery = rulesCfg.queriesSpec.get("reviewsExtractNoQuery");
        QueriesSpec.reviewsExtractNoPostQuery = rulesCfg.queriesSpec.get("reviewsExtractNoPostQuery");

        QueriesSpec.fullTextExtractStartQuery = rulesCfg.queriesSpec.get("fullTextExtractStartQuery");
        QueriesSpec.fullTextExtractExcludes = rulesCfg.queriesSpec.get("fullTextExtractExcludes");
    }
}
