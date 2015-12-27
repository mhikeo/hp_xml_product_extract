package com.hp.inventory.audit.parser.parsers;

import org.jsoup.nodes.Document;

/**
 * Holder class for returning #detect results
 */
public class DetectionResult {
    public DocumentParser parser;
    public Document doc;
    public String ruleHit;
    public String patternMatch;

    public DetectionResult(DocumentParser parser, Document doc, String ruleHit, String patternMatch) {
        this.parser = parser;
        this.doc = doc;
        this.ruleHit = ruleHit;
        this.patternMatch = patternMatch;
    }
}
