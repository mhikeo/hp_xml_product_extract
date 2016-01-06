/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.hp.inventory.audit.parser.Config;
import com.hp.inventory.audit.parser.Report;
import com.hp.inventory.audit.parser.model.AbstractProduct;
import com.hp.inventory.audit.parser.model.Product;
import com.hp.inventory.audit.parser.parsers.DetectionResult;
import com.hp.inventory.audit.parser.parsers.DocumentParser;
import com.hp.inventory.audit.parser.parsers.IgnoringParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.util.*;

/**
 * Writes the extracted products to the output file.
 *
 * @author TCDEVELOPER
 * @version 1.0.5
 */
public class JSONResultHandler implements ResultHandler {

    /**
     * If we should pretty-print the outputted JSON
     */
    private static final boolean PRETTY_PRINT = true;

    private Logger log = LoggerFactory.getLogger(DBResultHandler.class);

    @Expose
    private List<AbstractProduct> parsed = new ArrayList<>();

    private PrintWriter output;
    private Map<String, Set<String>> nonParsedAttrs = new HashMap<>();

    private Report report;
    private PrintWriter reportOutput;
    private Config config;

    /**
     * @inheritDoc
     */
    @Override
    public void setConfig(Config config) {
        this.config = config;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void beforeStart() {
        report = new Report();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void extractionFailed(Product definition, Exception e) {
        log.error("Exception occurred while trying to extract product #{}:", definition.getId(), e);
        report.addError(definition, e.getMessage());
    }

    /**
     * @inheritDoc
     */
    @Override
    public void extractionSucceeded(Product definition, AbstractProduct extracted) {
        parsed.add(extracted);
        report.addProductCount(extracted.getClass().getSimpleName(), extracted.getProductNumber());
    }

    /**
     * Print to output file the resulted extracted products as JSON objects.
     * Also logs statistics about non-parsed spec attributes.
     */
    @Override
    public void reportResults() {
        Gson gson;
        GsonBuilder gsonBuilder;
        if (PRETTY_PRINT) {
        	gsonBuilder = new GsonBuilder().setPrettyPrinting().serializeNulls();
        } else {
        	gsonBuilder = new GsonBuilder();
        }

        // @Expose implementations is incomplete. I'm commenting it.
        // gsonBuilder = gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        
        gson = gsonBuilder.create();
        
        String out = gson.toJson(parsed);
        output.print(out);
        output.flush();

        String rep = gson.toJson(report);
        log.info("Writing report results.");
        reportOutput.print(rep);
        reportOutput.flush();

        output.close();
        reportOutput.close();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void addNonParsedSpecItems(Product definition, DocumentParser documentParser, Set<String> attributes) {
        attributes.forEach(
                attr -> {
                    report.addNonParsedSpecAttribute(documentParser.getClass().getSimpleName(), attr, definition.getId());
                }
        );
    }

    /**
     * Sets the PrintWriter to write the results.
     */
    public void setOutput(PrintWriter output) {
        this.output = output;
    }

    /**
     * Gets the PrintWriter to write the results.
     */
    public PrintWriter getOutput() {
        return output;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setReportOutput(PrintWriter reportOutput) {
        this.reportOutput = reportOutput;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void detectionSucceeded(DetectionResult detectionResult, Product definition, AbstractProduct extracted) {
        if (extracted == null) {
            report.addParserNotFound(definition);

        } else if (detectionResult.parser instanceof IgnoringParser) {
            report.addIgnored(definition);
        } else {
            report.addHit(detectionResult.ruleHit);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void detectionFailed(Product definition, Exception e) {
        log.error("Error on parser detection", e);
        report.addDetectFailed(definition);
    }
}
