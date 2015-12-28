/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.handlers;

import au.com.bytecode.opencsv.CSVWriter;
import com.hp.inventory.audit.parser.Config;
import com.hp.inventory.audit.parser.model.AbstractProduct;
import com.hp.inventory.audit.parser.model.Product;
import com.hp.inventory.audit.parser.parsers.DetectionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Result handler that just outputs the detection phase results into a CSV file.
 *
 * @author TCDEVELOPER
 * @version 1.0.5
 */
public class DetectOnlyHandler implements ResultHandler {

    private Logger log = LoggerFactory.getLogger(DetectOnlyHandler.class);
    private PrintWriter output;
    private CSVWriter writer;
    private Config config;

    /**
     * @inheritDoc
     */
    @Override
    public void setConfig(Config config) {
        this.config = config;
    }

    /**
     * Do nothing
     */
    @Override
    public void extractionFailed(Product definition, Exception e) {

    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean shouldExtractAccessories() {
        return false;
    }

    /**
     * Do nothing
     */
    @Override
    public void extractionSucceeded(Product definition, AbstractProduct extracted) throws Exception {

    }

    /**
     * Setup the output CSV writer.
     */
    @Override
    public void beforeStart() {
        this.writer = new CSVWriter(this.output);
        String[] head = {"Parser", "Name", "Type", "Match", "URL"};
        this.writer.writeNext(head);
        try {
            this.writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the PrintWriter to write the results.
     */
    public void setOutput(PrintWriter output) {
        this.output = output;
    }

    @Override
    public void detectionSucceeded(DetectionResult detectionResult, Product definition, AbstractProduct extracted) {
        try {
            String parserName = null, patternType = null, patternMatch = null;

            if (detectionResult != null) {
                parserName = detectionResult.parser.getClass().getSimpleName();
                patternType = detectionResult.ruleHit;
                patternMatch = detectionResult.patternMatch;
            }

            String[] val = new String[] {parserName, extracted.getProductName(), patternType, patternMatch, definition.getProductUrl()};
            this.writer.writeNext(val);
            this.writer.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * @inheritDoc
     */
    @Override
    public void detectionFailed(Product definition, Exception e) {
        log.error("Error on parser detection", e);
    }
}
