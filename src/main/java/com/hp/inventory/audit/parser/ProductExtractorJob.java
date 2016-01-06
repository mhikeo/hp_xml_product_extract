/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser;

import com.hp.inventory.audit.parser.handlers.ResultHandler;
import com.hp.inventory.audit.parser.model.AbstractProduct;
import com.hp.inventory.audit.parser.model.Product;
import com.hp.inventory.audit.parser.model.RelatedAccessory;
import com.hp.inventory.audit.parser.parsers.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Runs a single product extract job. To be used with a ExecutorService.
 *
 * @author TCDEVELOPER
 * @version 1.0.5
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
            AbstractProduct extracted = parser.parse(detectionResult.doc, definition, config);

            resultHandler.detectionSucceeded(detectionResult, definition, extracted);

            if (extracted != null) {
                associateRelatedAccessories();
                resultHandler.extractionSucceeded(definition, extracted);
            }

            extracted = null; // Hint GC deallocation
            content = null; // Hint GC deallocation

        } catch (Exception e) {
            resultHandler.extractionFailed(definition, e);
        }
    }

    /**
     * Associates records within the RelatedAccessory table with their products
     * in Product table.
     */
    private void associateRelatedAccessories() {
        Set<RelatedAccessory> toRemove = new HashSet<>();
        for (RelatedAccessory ra : definition.getAccessories()) {
            if (ra.getAccessoryProductNumber() == null) {
                String url = ra.getUrl();
                String number = config.urlProdNumberMap.get(url);
                if (number == null) {
                    // When we can't find the product number it means that the URL was never
                    // downloaded and that's most likely a mistake. We'll remove the related
                    // accessory since the schema requires the product number.

                    // An improvement would be to fetch the URL and extract the number but that could impact
                    // processing time - better to check with management first.

                    config.resultHandler.unknownAccessory(ra);
                    toRemove.add(ra);
                } else {
                    ra.setAccessoryProductNumber(number);
                }
            }
        }
        definition.getAccessories().removeAll(toRemove);
    }

}
