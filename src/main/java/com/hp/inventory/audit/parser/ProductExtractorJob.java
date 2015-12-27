/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser;

import com.hp.inventory.audit.parser.handlers.ResultHandler;
import com.hp.inventory.audit.parser.model.IProduct;
import com.hp.inventory.audit.parser.model.Product;
import com.hp.inventory.audit.parser.parsers.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Runs a single product extract job. To be used with a ExecutorService.
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
public class ProductExtractorJob implements Runnable {
    private final Product definition;
    private final Config config;

    Logger log = LoggerFactory.getLogger(ProductExtractorJob.class);


    public ProductExtractorJob(Config config, Product definition) {
        this.config = config;
        this.definition = definition;
    }

    /**
     * Runs the extraction job by loading the content, identifying the correct parser, parsing the content
     * and passing the results to the result handler.
     */
    @Override
    public void run() {
        ResultHandler resultHandler = config.resultHandler;

        try {
            log.debug("Parsing html file: {}", definition.getSourceFile());
            File f = new File(config.dataDirectory, definition.getSourceFile());
            String content = FileUtils.readFileToString(f);
            DetectionResult detectionResult;
            try {
                detectionResult = DocumentParserDetector.detect(definition, content, config);
            } catch (Exception e) {
                resultHandler.detectionFailed(definition, e);
                return;
            }

            DocumentParser parser = detectionResult.parser;
            IProduct extracted = parser.parse(detectionResult.doc, definition, config);

            resultHandler.detectionSucceeded(detectionResult, definition, extracted);

            if (extracted != null) {
                resultHandler.extractionSucceeded(definition, extracted);
            }

            extracted = null; // Hint GC deallocation
            content = null; // Hint GC deallocation

        } catch (Exception e) {
            resultHandler.extractionFailed(definition, e);
        }
    }

}
