package com.hp.inventory.audit.parser.parsers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Functions to detect the correct document parser for each page.
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
public class DocumentParserDetector {


    /**
     * Matches the URL.
     */
    private static final Pattern urlPattern = Pattern.compile("/pdp/(.*)/");

    /**
     * Matches a provided embedded JS code HP inserted for tracking purposes.
     */
    private static final Pattern sectionValuePattern = Pattern.compile("var sectionValue='(.*)';");

    /**
     * For business solutions, matches a subgroup.
     */
    private static final Pattern subSectionPattern = Pattern.compile("~(.+)~By");

    /**
     * Pattern for finding page title
     */
    private static final Pattern titlePattern = Pattern.compile("<title>(.*)</title>");


    /**
     * Lowercase valid product type names
     */
    private static final Map<String, Class<? extends DocumentParser>> typeMap;

    /**
     * URL patterns to help detecting some corner cases.
     */
    private static final Map<String, Class<? extends DocumentParser>> urlMatches;
    private static Logger log = LoggerFactory.getLogger(DocumentParserDetector.class);

    static {
        typeMap = new HashMap<>();
        typeMap.put("laptops", LaptopParser.class);
        typeMap.put("desktops", DesktopParser.class);
        typeMap.put("printers", PrinterParser.class);
        typeMap.put("laserjet", PrinterParser.class);
        typeMap.put("designjet", PrinterParser.class);
        typeMap.put("tablets", TabletParser.class);

        urlMatches = new HashMap<>();
        urlMatches.put("-small-form-factor-pc", DesktopParser.class);
        urlMatches.put("-desktop-mini-pc", DesktopParser.class);
        urlMatches.put("-tower-pc", DesktopParser.class);
        urlMatches.put("-all-in-one-pc", DesktopParser.class);

        urlMatches.put("-mobile-workstation", LaptopParser.class);
        urlMatches.put("-notebook-pc", LaptopParser.class);
        urlMatches.put("hp-chromebook", LaptopParser.class);

        urlMatches.put("-tablet", TabletParser.class);

    }

    /**
     * Lowercase types that should be ignored
     */
    private static final List<String> ignoreTypes =
            Arrays.asList("care packs", "accessories", "ink, toner & paper", "monitors", "case");

    /**
     * Lowercase strings that mark a page to be ignored when present in page title. Has the lowest priority.
     */
    private static final List<String> ignoreTitleContains =
            Arrays.asList("post warranty", "wireless mouse", "dvdrw drive");


    /**
     * Uses a number of heuristics to detect the correct DocumentParser for the page.
     * @param url The original URL of the page.
     * @param content The page content.
     * @return A DocumentParser instance or null if none could be found.
     */
    public static DocumentParser detect(String url, String content) {
        Matcher matcher;
        String urlType, svType , subType, title;

        try {
            // Try the URL
            if ((matcher = urlPattern.matcher(url.toLowerCase())).find()) {
                urlType = matcher.group(1).toLowerCase();
                log.debug(urlType);
                if (typeMap.containsKey(urlType)) {
                    return typeMap.get(urlType).newInstance();
                } else if (ignoreTypes.contains(urlType)) {
                	//TODO: this else is probably redundant. its cases fall into the next "else". need to check carefully
                	
                    return null;
                }  else {
                    for(String ignoreType:ignoreTypes){
                    	if(urlType.contains(ignoreType)){
                            return null;
                    	}
                    }
                }
            }

            // Try the JS sectionValue string
            if ((matcher = sectionValuePattern.matcher(content)).find()) {
                svType = matcher.group(1).toLowerCase();
                log.debug("\t\t" + svType);
                if (typeMap.containsKey(svType)) {
                    return typeMap.get(svType).newInstance();
                } else if (ignoreTypes.contains(svType)) {
                    return null;
                }
            }

            // Try the JS subgroup string
            if ((matcher = subSectionPattern.matcher(content)).find()) {
                subType = matcher.group(1).toLowerCase();
                log.debug("\t\t\t\t" + subType);
                if (typeMap.containsKey(subType)) {
                    return typeMap.get(subType).newInstance();
                } else if (ignoreTypes.contains(subType)) {
                    return null;
                }
            }

            // Try URL corner cases
            for (String urlcase : urlMatches.keySet()) {
                if (url.contains(urlcase)) {
                    return urlMatches.get(urlcase).newInstance();
                }
            }

            // Try title ignore corner cases
            if ((matcher = titlePattern.matcher(content)).find()) {
                title = matcher.group(1).toLowerCase();
                log.debug("\t\t\t\t\t\t" + title);
                for (String titleCase : ignoreTitleContains) {
                    if (title.contains(titleCase)) {
                        return null;
                    }
                }
            }

            return null;


        } catch (Exception ex) {
            throw new RuntimeException("Unexpected Exception", ex);
        }
    }


}
